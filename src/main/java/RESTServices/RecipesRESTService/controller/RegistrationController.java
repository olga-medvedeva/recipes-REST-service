package RESTServices.RecipesRESTService.controller;

import RESTServices.RecipesRESTService.model.User;
import RESTServices.RecipesRESTService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RegistrationController {
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder;

    @PostMapping("/api/register")
    public ResponseEntity<Void> register(@RequestBody @Valid User user) {
        User foundUser = userRepo.getByEmail(user.getEmail());
        if (foundUser != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepo.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/actuator/shutdown")
    public void shutdown() {

    }
}
