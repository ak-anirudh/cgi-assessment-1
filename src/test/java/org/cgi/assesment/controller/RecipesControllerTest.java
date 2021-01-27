package org.cgi.assesment.controller;

import org.cgi.assesment.service.RecipesService;
import org.cgi.assesment.util.RecipeUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class RecipesControllerTest {

    @MockBean
    private RecipesService recipesService;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setup() {

    }
    @Test
    public void recipeList() throws Exception {
        when(recipesService.recipeList()).thenReturn(RecipeUtil.createRecipes());
        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/recipes/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(RecipeUtil.COLLECTION_COUNT)));
    }

    @Test
    public void recipesByIngredients() throws Exception {
        when(recipesService.recipesByIngredients(Arrays.asList("onions", "mushrooms"))).thenReturn(RecipeUtil.createRecipes());
        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/recipes/byIngredients?ingredients=onions,mushrooms")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(RecipeUtil.COLLECTION_COUNT)));
    }
}
