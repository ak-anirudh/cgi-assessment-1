package org.cgi.assesment.repository;

import org.cgi.assesment.model.Recipes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RecipesRepositoryTest {

    @Autowired
    private RecipesRepository repository;

    @Test
    public void testfindByIngredientsInOrderByTitleAsc(){
        List<Recipes> recipes = repository.findByIngredientsInOrderByTitleAsc(Arrays.asList("onions", "mushrooms"));
        assertThat(recipes.size()).isEqualTo(22);
    }

}
