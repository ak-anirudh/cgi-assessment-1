package org.cgi.assesment.service;

import org.cgi.assesment.model.Recipes;
import org.cgi.assesment.repository.RecipesRepository;
import org.cgi.assesment.util.RecipeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@SpringBootConfiguration
@ActiveProfiles("test")
public class RecipesServiceTest {

    @Mock
    private RecipesRepository recipesRepository;

    @InjectMocks
    private RecipesService recipesService;


    @Test
    public void testRecipeList() {
        when(recipesRepository.findAll()).thenReturn(RecipeUtil.createRecipes());
        List<Recipes> recipes = recipesService.recipeList();
        assertThat(recipes.size()).isEqualTo(RecipeUtil.COLLECTION_COUNT);
    }

    @Test
    public void testrecipesByIngredients() {
        when(recipesRepository.findByIngredientsInOrderByTitleAsc(Arrays.asList("onions", "mushrooms"))).thenReturn(RecipeUtil.createRecipes());
        List<Recipes> recipes = recipesService.recipesByIngredients(Arrays.asList("onions", "mushrooms"));
        assertThat(recipes.size()).isEqualTo(RecipeUtil.COLLECTION_COUNT);
    }
}
