import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {recipes} from '../model/recipes';

@Injectable({
  providedIn: 'root'
})
export class RecipesService {

  url: string;

  constructor(private http: HttpClient) {
    this.url = environment.baseUrl + 'recipes/';
  }

  public findRecipes(): Observable<recipes[]>{
    return this.http.get<recipes[]>(`${this.url}`);
  }

  public findRecipesByIngredients(ingredients: string[]): Observable<recipes[]>{
    return this.http.get<recipes[]>(`${this.url}byIngredients?ingredients=${ingredients.join()}`);
  }

}
