package org.cgi.assesment.controller;

import lombok.RequiredArgsConstructor;
import org.cgi.assesment.exceptions.ResourceNotFoundException;
import org.cgi.assesment.model.Recipes;
import org.cgi.assesment.service.RecipesService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/api/v1/recipes")
@Validated
@RequiredArgsConstructor
public class RecipesController {

    private final RecipesService recipesService;

    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Iterable<Recipes> recipeList() throws ResourceNotFoundException {
        return recipesService.recipeList();
    }

    @GetMapping(value = "/byIngredients", params = "ingredients", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Recipes> recipesByIngredients(@RequestParam("ingredients") List<@Valid @Pattern(regexp = "([a-z\\s]+)") String> ingredients) throws ResourceNotFoundException{
        return recipesService.recipesByIngredients(ingredients);
    }

}
