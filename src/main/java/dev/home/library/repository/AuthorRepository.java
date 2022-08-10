package dev.home.library.repository;

import dev.home.library.model.Author;
import dev.home.library.model.dto.response.AuthorSuccessRateResponseDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query(value =
            "SELECT new dev.home.library.model.dto.response.AuthorSuccessRateResponseDto"
                + "(a.id, a.authorName, a.birthDate, a.phone, a.email, "
            + "(SUM(b.soldAmount / b.publishedAmount) / COUNT(b.id) * 100))"
            + "FROM Book AS b "
            + "JOIN b.authors AS a "
            + "GROUP BY a.id "
            + "ORDER BY (SUM(b.soldAmount / b.publishedAmount) / COUNT(b.id) * 100) DESC ")
    List<Optional<AuthorSuccessRateResponseDto>> findFirstBySuccessAuthorRate();
}
