package RESTServices.RecipesRESTService.controller;

import RESTServices.RecipesRESTService.model.Recipe;
import RESTServices.RecipesRESTService.model.User;
import RESTServices.RecipesRESTService.repository.RecipeRepository;
import RESTServices.RecipesRESTService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeRepository recipeRepo;
    private final UserRepository userRepo;

    @GetMapping("/recipe/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable String id, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        long idLong = Long.parseLong(id);
        Optional<Recipe> optionalRecipe = recipeRepo.findById(idLong);
        if (optionalRecipe.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalRecipe.get(), HttpStatus.OK);
    }

    @PostMapping("/recipe/new")
    public ResponseEntity<String> setRecipe(@RequestBody @Valid Recipe postedRecipe, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        postedRecipe.setUser(userRepo.getByEmail(userDetails.getUsername()));
        recipeRepo.save(postedRecipe);
        String responseId = "{\n" + "\"id\": " + postedRecipe.getId() + "\n}";
        return new ResponseEntity<>(responseId, HttpStatus.OK);
    }

    @DeleteMapping("/recipe/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable String id, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        long idLong = Long.parseLong(id);
        if (recipeRepo.existsById(idLong)) {
            if (userRepo.getByEmail(userDetails.getUsername()).getId() == (recipeRepo.getById(idLong).getUser().getId())) {
                recipeRepo.deleteById(idLong);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/recipe/{id}")
    public ResponseEntity<Void> putRecipe(@PathVariable String id, @RequestBody @Valid Recipe postedRecipe, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        long idLong = Long.parseLong(id);
        Optional<Recipe> optionalRecipe = recipeRepo.findById(idLong);
        if (optionalRecipe.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Recipe oldRecipe = optionalRecipe.get();
        if (userRepo.getByEmail(userDetails.getUsername()).getId() == (oldRecipe.getUser().getId())) {
            oldRecipe.setName(postedRecipe.getName());
            oldRecipe.setCategory(postedRecipe.getCategory());
            oldRecipe.setDate(LocalDateTime.now());
            oldRecipe.setDescription(postedRecipe.getDescription());
            oldRecipe.setIngredients(postedRecipe.getIngredients());
            oldRecipe.setDirections(postedRecipe.getDirections());
            recipeRepo.save(oldRecipe);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/recipe/search/")
    public ResponseEntity<List<Recipe>> getRecipesByCategory(@RequestParam(required = false) String category, @RequestParam(required = false) String name, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if ((category == null) == (name == null)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (category != null) {
            return new ResponseEntity<>(recipeRepo.getByCategoryIgnoreCaseOrderByDateDesc(category.toUpperCase()), HttpStatus.OK);
        }
        return new ResponseEntity<>(recipeRepo.getByNameContainingIgnoreCaseOrderByDateDesc(name.toUpperCase()), HttpStatus.OK);
    }

    @GetMapping("/ids")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userRepo.findAll(), HttpStatus.OK);
    }
}
