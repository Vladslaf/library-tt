package dev.home.library.repository;

import dev.home.library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query(value = "SELECT a.id, a.author_name, a.birth_date, a.phone, a.email, "
            + "SUM(b.sold_amount / b.published_amount) / COUNT(b.id) AS success_author_rate "
            + "FROM authors a "
            + "JOIN books_authors ba on a.id = ba.author_id "
            + "JOIN books b ON b.id = ba.book_id "
            + "GROUP BY a.author_name "
            + "ORDER BY success_author_rate DESC "
            + "LIMIT 1", nativeQuery = true)
    Author findTopBySuccessAuthorRate();
}
