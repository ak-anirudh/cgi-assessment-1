import {TestBed} from '@angular/core/testing';
import { asyncData, asyncError } from '../../testing/async-observable-helpers';
import {LogsService} from './logs.service';
import {logs} from '../model/logs';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';

describe('LogsService with spies', () => {
  let httpClientSpy: { get: jasmine.Spy };
  let service: LogsService;

  beforeEach(() => {
    httpClientSpy = jasmine.createSpyObj('HttpClient', ['get']);
    service = new LogsService(httpClientSpy as any);
    /*TestBed.configureTestingModule({});
    service = TestBed.inject(LogsService);*/
  });

  it('should return expected logs (HttpClient called once)', () => {
    const expectedLogs: logs[] = [{
      errorDescription: 'LoggingAspect: Enter: com.fluentgrid.repapp.web.rest.AccountResource.getAccount() with argument[s] = []',
      occurrences: 48}];
    httpClientSpy.get.and.returnValue(asyncData(expectedLogs));
    expect(service).toBeTruthy();
    service.findByLevelAndLimit('DEBUG', 1).subscribe(i => expect(i).toEqual(expectedLogs, 'expected logs'),
      fail);
    expect(httpClientSpy.get.calls.count()).toBe(1, 'one call');
  });

  it('should return an error when the server returns a 404', () => {
    const errorResponse = new HttpErrorResponse({
      error: 'test 404 error',
      status: 404, statusText: 'Not Found'
    });

    httpClientSpy.get.and.returnValue(asyncError(errorResponse));

    service.findByLevelAndLimit('DEBUG', 1).subscribe(
      i => fail('expected an error, not logs'),
      error  => expect(error.message).toContain('test 404 error')
    );
  });
});

describe('HeroesService (with mocks)', () => {
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;
  let service: LogsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      // Import the HttpClient mocking services
      imports: [HttpClientTestingModule],
      // Provide the service-under-test
      providers: [LogsService]
    });

    // Inject the http, test controller, and service-under-test
    // as they will be referenced by each test.
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
    service = TestBed.inject(LogsService);
  });

  afterEach(() => {
    // After every test, assert that there are no more pending requests.
    httpTestingController.verify();
  });

  /// HeroService method tests begin ///
  describe('#findByLevelAndLimit', () => {
    let expectedLogs: logs[];

    beforeEach(() => {
      service = TestBed.inject(LogsService);
      expectedLogs = [{
        errorDescription: 'LoggingAspect: Enter: com.fluentgrid.repapp.web.rest.AccountResource.getAccount() with argument[s] = []',
        occurrences: 48}
      ] as logs[];
    });

    it('should return expected logs (called once)', () => {
      service.findByLevelAndLimit('DEBUG', 1).subscribe(
        l => expect(l).toEqual(expectedLogs, 'should return expected logs'),
        fail
      );

      // HeroService should have made one request to GET logs from expected URL
      const req = httpTestingController.expectOne(service.url);
      expect(req.request.method).toEqual('GET');

      // Respond with the mock logs
      req.flush(expectedLogs);
    });

    it('should be OK returning no logs', () => {
      service.findByLevelAndLimit('DEBUG', 1).subscribe(
        l => expect(l.length).toEqual(0, 'should have empty logs array'),
        fail
      );

      const req = httpTestingController.expectOne(service.url);
      req.flush([]); // Respond with no logs
    });

    it('should turn 404 into a user-friendly error', () => {
      const msg = 'Deliberate 404';
      service.findByLevelAndLimit('DEBUG', 1).subscribe(
        l => fail('expected to fail'),
        error => expect(error.message).toContain(msg)
      );

      const req = httpTestingController.expectOne(service.url);

      // respond with a 404 and the error message in the body
      req.flush(msg, {status: 404, statusText: 'Not Found'});
    });

    it('should return expected logs (called multiple times)', () => {
      service.findByLevelAndLimit('DEBUG', 1).subscribe();
      service.findByLevelAndLimit('DEBUG', 1).subscribe();
      service.findByLevelAndLimit('DEBUG', 1).subscribe(
        l => expect(l).toEqual(expectedLogs, 'should return expected logs'),
        fail
      );

      const requests = httpTestingController.match(service.url);
      expect(requests.length).toEqual(3, 'calls to g()');

      // Respond to each request with different mock logs results
      requests[0].flush([]);
      requests[1].flush([{
        errorDescription: 'LoggingAspect: Enter: com.fluentgrid.repapp.web.rest.AccountResource.getAccount() with argument[s] = []',
        occurrences: 48}]);
      requests[2].flush(expectedLogs);
    });
  });

});

