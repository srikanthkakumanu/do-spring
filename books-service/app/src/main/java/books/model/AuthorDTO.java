package books.model;

import books.domain.Name;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDTO {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("firstName")
    @NotEmpty(message = "firstName must not be empty.")
    private String firstName;

    @JsonProperty("lastName")
    @NotEmpty(message = "lastName must not be empty.")
    private String lastName;

    @JsonProperty("genre")
    @NotEmpty(message = "genre must not be empty.")
    private String genre;

}
