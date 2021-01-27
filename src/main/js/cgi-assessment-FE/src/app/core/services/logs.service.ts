import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {logs} from '../model/logs';

@Injectable({
  providedIn: 'root'
})
export class LogsService {

  url: string;

  constructor(private http: HttpClient) {
    this.url = environment.baseUrl + 'logs';
  }

  public findByLevelAndLimit(level: string | undefined, limit: number | undefined): Observable<logs[]>{
    return this.http.get<logs[]>(`${this.url}/${level}/${limit}`);
}

}
