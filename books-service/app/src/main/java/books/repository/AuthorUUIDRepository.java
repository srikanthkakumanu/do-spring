package books.repository;

import books.domain.AuthorUUID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorUUIDRepository extends JpaRepository<AuthorUUID, UUID> {
}
