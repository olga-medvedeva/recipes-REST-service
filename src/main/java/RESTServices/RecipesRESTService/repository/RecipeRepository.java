package RESTServices.RecipesRESTService.repository;

import RESTServices.RecipesRESTService.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> getByCategoryIgnoreCaseOrderByDateDesc(String category);

    List<Recipe> getByNameContainingIgnoreCaseOrderByDateDesc(String name);
}
