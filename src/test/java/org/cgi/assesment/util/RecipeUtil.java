package org.cgi.assesment.util;

import org.cgi.assesment.model.Recipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeUtil {
    public static int COLLECTION_COUNT = 5;

    public static List<Recipes> createRecipes() {
        List<Recipes> recipes = new ArrayList();
        for (int i = 1; i <= COLLECTION_COUNT; i++) {
            recipes.add(createRecipe(i));
        }
        return recipes;
    }

    public static Recipes createRecipe(final int id) {
        Recipes recipes = new Recipes();
        recipes.setId(id);
        recipes.setTitle("New Recipe" + id);
        recipes.setHref("http://newlink.com"+id);
        recipes.setIngredients(Arrays.asList("onions", "garlic", "mushrooms"));
        recipes.setThumbnail("http://newimage"+id);
        return recipes;
    }
}
