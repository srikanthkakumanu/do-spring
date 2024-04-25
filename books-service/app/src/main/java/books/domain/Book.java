package books.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
//@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JdbcTypeCode(value = Types.VARBINARY)
    @Column(columnDefinition = "VARBINARY(16)", updatable = false, nullable = false)
    private UUID id;

    private String title;
    private String isbn;
    private String publisher;
    private final LocalDateTime created = LocalDateTime.now();

    @JdbcTypeCode(value = Types.VARBINARY)
    @Column(columnDefinition = "VARBINARY(16)", updatable = true, nullable = true)
    private UUID authorId;

    public Book(String title, String isbn, String publisher, UUID authorId) {
        this.title = title;
        this.isbn = isbn;
        this.publisher = publisher;
        this.authorId = authorId;
        //this.created = LocalDateTime.now();
    }

    public Book(UUID id, String title, String isbn, String publisher, UUID authorId) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.publisher = publisher;
        this.authorId = authorId;
        //this.created = LocalDateTime.now();
    }
}
