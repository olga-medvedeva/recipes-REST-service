package RESTServices.RecipesRESTService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@Entity
@Table(name = "[user]")
public class User {
    @Id
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @JsonIgnore
    private long id;

    @NotBlank
    @Pattern(regexp = "^.*@.*\\..*")
    private String email;

    @NotBlank
    @Length(min = 8)
    private String password;

    private String role;
}
