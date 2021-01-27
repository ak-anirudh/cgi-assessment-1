package org.cgi.assesment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cgi.assesment.model.Recipes;
import org.cgi.assesment.repository.RecipesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecipesService {

    private final RecipesRepository recipesRepository;

    public Iterable<Recipes> save(List<Recipes> recipes) {
       return recipesRepository.saveAll(recipes);
    }

    public List<Recipes> recipeList() {
        return recipesRepository.findAll();
    }

    public List<Recipes> recipesByIngredients(List<String> ingredients) {
        return recipesRepository.findByIngredientsInOrderByTitleAsc(ingredients).stream()
                .filter(i -> i.getIngredients().containsAll(ingredients))
                .distinct()
                .collect(Collectors.toList());
    }

}
