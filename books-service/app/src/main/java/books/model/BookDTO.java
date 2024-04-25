package books.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDTO {


    @JsonProperty("id")
    private UUID id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("isbn")
    private String isbn;

    @JsonProperty("publisher")
    private String publisher;

    @JsonProperty("created")
    private LocalDateTime created;

    @JsonProperty("authorId")
    private UUID authorId;

}
