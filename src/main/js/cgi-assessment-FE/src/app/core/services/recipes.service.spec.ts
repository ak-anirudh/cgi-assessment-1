import {TestBed} from '@angular/core/testing';

import {RecipesService} from './recipes.service';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {asyncData, asyncError} from '../../../testing/async-observable-helpers';
import {recipes} from '../model/recipes';

describe('RecipesService with spies', () => {
  let httpClientSpy: { get: jasmine.Spy };
  let service: RecipesService;

  beforeEach(() => {
    httpClientSpy = jasmine.createSpyObj('HttpClient', ['get']);
    service = new RecipesService(httpClientSpy as any);

  });

  it('should return expected recipes (HttpClient called once)', () => {
    const expectedRecipes: recipes[] = [{
      id: 1,
      title: 'Creamy Scrambled Eggs Recipe Recipe',
      href: 'http://www.grouprecipes.com/43522/creamy-scrambled-eggs-recipe.html',
      ingredients: [
        'onions'
      ],
      thumbnail: 'http://img.recipepuppy.com/373064.jpg'
    }];
    httpClientSpy.get.and.returnValue(asyncData(expectedRecipes));
    expect(service).toBeTruthy();
    service.findRecipes().subscribe(i => expect(i).toEqual(expectedRecipes, 'expected recipes'),
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
      i => fail('expected an error, not recipes'),
      error  => expect(error.message).toContain('test 404 error')
    );
  });
});

describe('HeroesService (with mocks)', () => {
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;
  let service: RecipesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      // Import the HttpClient mocking services
      imports: [HttpClientTestingModule],
      // Provide the service-under-test
      providers: [RecipesService]
    });

    // Inject the http, test controller, and service-under-test
    // as they will be referenced by each test.
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
    service = TestBed.inject(RecipesService);
  });

  afterEach(() => {
    // After every test, assert that there are no more pending requests.
    httpTestingController.verify();
  });

  /// RecipeServices method tests begin ///
  describe('#findRecipes', () => {
    let expectedRecipes: recipes[];

    beforeEach(() => {
      service = TestBed.inject(RecipesService);
      expectedRecipes = [{
        id: 1,
        title: 'Creamy Scrambled Eggs Recipe Recipe',
        href: 'http://www.grouprecipes.com/43522/creamy-scrambled-eggs-recipe.html',
        ingredients: [
          'onions'
        ],
        thumbnail: 'http://img.recipepuppy.com/373064.jpg'
      }
      ] as recipes[];
    });

    it('should return expected recipes (called once)', () => {
      service.findRecipes().subscribe(
        l => expect(l).toEqual(expectedRecipes, 'should return expected recipes'),
        fail
      );

      // HeroService should have made one request to GET recipes from expected URL
      const req = httpTestingController.expectOne(service.url);
      expect(req.request.method).toEqual('GET');

      // Respond with the mock recipes
      req.flush(expectedRecipes);
    });

    it('should be OK returning no recipes', () => {
      service.findRecipes().subscribe(
        l => expect(l.length).toEqual(0, 'should have empty recipes array'),
        fail
      );

      const req = httpTestingController.expectOne(service.url);
      req.flush([]); // Respond with no recipes
    });

    it('should turn 404 into a user-friendly error', () => {
      const msg = 'Deliberate 404';
      service.findRecipes().subscribe(
        l => fail('expected to fail'),
        error => expect(error.message).toContain(msg)
      );

      const req = httpTestingController.expectOne(service.url);

      // respond with a 404 and the error message in the body
      req.flush(msg, {status: 404, statusText: 'Not Found'});
    });

    it('should return expected recipes (called multiple times)', () => {
      service.findRecipes().subscribe();
      service.findRecipes().subscribe();
      service.findRecipes().subscribe(
        l => expect(l).toEqual(expectedRecipes, 'should return expected recipes'),
        fail
      );

      const requests = httpTestingController.match(service.url);
      expect(requests.length).toEqual(3, 'calls to g()');

      // Respond to each request with different mock recipes results
      requests[0].flush([]);
      requests[1].flush([{
        errorDescription: 'LoggingAspect: Enter: com.fluentgrid.repapp.web.rest.AccountResource.getAccount() with argument[s] = []',
        occurrences: 48}]);
      requests[2].flush(expectedRecipes);
    });
  });

  describe('#findRecipesByIngredients', () => {
    let expectedRecipes: recipes[];

    beforeEach(() => {
      service = TestBed.inject(RecipesService);
      expectedRecipes = [{
        id: 1,
        title: 'Creamy Scrambled Eggs Recipe Recipe',
        href: 'http://www.grouprecipes.com/43522/creamy-scrambled-eggs-recipe.html',
        ingredients: [
          'onions', 'mushrooms'
        ],
        thumbnail: 'http://img.recipepuppy.com/373064.jpg'
      }
      ] as recipes[];
    });

    it('should return expected recipes (called once)', () => {
      service.findRecipesByIngredients(['onions', 'mushrooms']).subscribe(
        l => expect(l).toEqual(expectedRecipes, 'should return expected recipes'),
        fail
      );

      // HeroService should have made one request to GET recipes from expected URL
      const req = httpTestingController.expectOne(service.url);
      expect(req.request.method).toEqual('GET');

      // Respond with the mock recipes
      req.flush(expectedRecipes);
    });

    it('should be OK returning no recipes', () => {
      service.findRecipesByIngredients(['onions', 'mushrooms']).subscribe(
        l => expect(l.length).toEqual(0, 'should have empty recipes array'),
        fail
      );

      const req = httpTestingController.expectOne(service.url);
      req.flush([]); // Respond with no recipes
    });

    it('should turn 404 into a user-friendly error', () => {
      const msg = 'Deliberate 404';
      service.findRecipesByIngredients(['onions', 'mushrooms']).subscribe(
        l => fail('expected to fail'),
        error => expect(error.message).toContain(msg)
      );

      const req = httpTestingController.expectOne(service.url);

      // respond with a 404 and the error message in the body
      req.flush(msg, {status: 404, statusText: 'Not Found'});
    });

    it('should return expected recipes (called multiple times)', () => {
      service.findRecipesByIngredients(['onions', 'mushrooms']).subscribe();
      service.findRecipesByIngredients(['onions', 'mushrooms']).subscribe();
      service.findRecipesByIngredients(['onions', 'mushrooms']).subscribe(
        l => expect(l).toEqual(expectedRecipes, 'should return expected recipes'),
        fail
      );

      const requests = httpTestingController.match(service.url);
      expect(requests.length).toEqual(3, 'calls to g()');

      // Respond to each request with different mock recipes results
      requests[0].flush([]);
      requests[1].flush([{
        errorDescription: 'LoggingAspect: Enter: com.fluentgrid.repapp.web.rest.AccountResource.getAccount() with argument[s] = []',
        occurrences: 48}]);
      requests[2].flush(expectedRecipes);
    });
  });
});
