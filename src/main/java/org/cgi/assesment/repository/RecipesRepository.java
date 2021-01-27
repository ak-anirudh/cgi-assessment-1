package org.cgi.assesment.repository;

import org.cgi.assesment.model.Recipes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipesRepository extends JpaRepository<Recipes, Integer> {
    List<Recipes> findByIngredientsInOrderByTitleAsc(List<String> ingredients);

    @Query(value = "from Recipes r left join fetch r.ingredients a where a in (:ingredientsList) order by r.title asc")
    List<Recipes> findByAllIngredients(@Param("ingredientsList") List<String> ingredientsList);

}
