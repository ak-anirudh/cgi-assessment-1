import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {RecipesService} from '../core/services/recipes.service';
import {FormControl} from '@angular/forms';

@Component({
  selector: 'app-recipes',
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.css']
})
export class RecipesComponent implements OnInit {
  ingredients: string[] = ['onions', 'butter', 'chicken', 'salmon', 'salt', 'ham', 'rice', 'water', 'garlic', 'dijon mustard',
    'italian bread', 'pita bread', 'vinegar', 'vegetable oil', 'olive oil', 'romano cheese', 'mushrooms', 'heavy cream', 'port wine',
    'black pepper', 'blue cheese', 'soy sauce', 'red onions', 'italian dressing', 'sandwich rolls', 'mayonaise', 'barbecue sauce',
    'lettuce', 'tomato', 'balsamic vinegar', 'rosemary', 'bacon', 'eggs', 'mustard', 'pastry', 'pancetta'];
  displayedColumns: string[] = ['id', 'title', 'ingredients', 'href', 'thumbnail'];
  recipesResults = new MatTableDataSource();
  ingredientsSelected: FormControl;

  constructor(private service: RecipesService) {
    this.ingredientsSelected = new FormControl();
  }

  ngOnInit(): void {
    this.ingredientsSelected.valueChanges.subscribe(data => {
    });
  }

  getRecipes(): void {
    if (this.ingredientsSelected.value == null) {
    this.service.findRecipes().subscribe((data: unknown[]) => {
      this.recipesResults.data = data;
    });
    }
    else{
      this.service.findRecipesByIngredients(this.ingredientsSelected.value).subscribe(data => {
        this.recipesResults.data = data;
      });
    }
    this.ingredientsSelected.setValue(null);
  }

}
