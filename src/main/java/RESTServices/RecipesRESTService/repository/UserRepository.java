package RESTServices.RecipesRESTService.repository;

import RESTServices.RecipesRESTService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User getByEmail(String email);
}
