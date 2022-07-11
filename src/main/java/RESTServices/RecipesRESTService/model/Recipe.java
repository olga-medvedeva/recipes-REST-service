package RESTServices.RecipesRESTService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "recipe")
public class Recipe {
    @Id
    @SequenceGenerator(name = "recipe_seq", sequenceName = "recipe_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_seq")
    @JsonIgnore
    private long id;

    @NotBlank
    private String category;

    private LocalDateTime date = LocalDateTime.now();

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotEmpty
    private String[] ingredients;

    @NotEmpty
    private String[] directions;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
