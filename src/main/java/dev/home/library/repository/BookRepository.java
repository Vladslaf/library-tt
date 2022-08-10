package dev.home.library.repository;

import dev.home.library.model.Book;
import dev.home.library.model.dto.response.BookSuccessRateResponseDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value =
            "SELECT b "
            + "FROM Book AS b "
            + "JOIN b.authors AS a "
            + "WHERE a.authorName LIKE %:name%")
    List<Book> findAllByAuthorName(@Param("name") String authorName);

    @Query(value =
            "SELECT b.book_name, b.id, b.sold_amount, b.published_amount "
            + "FROM books b "
            + "JOIN books_authors ba on b.id = ba.book_id "
            + "JOIN authors a ON a.id = ba.author_id "
            + "WHERE a.author_name = ?1 "
            + "GROUP BY book_id "
            + "ORDER BY SUM(b.sold_amount) DESC "
            + "LIMIT 1", nativeQuery = true)
    Book findMostSoldBooksByAuthorName(String authorName);

    @Query(value =
            "SELECT b.book_name, b.id, b.sold_amount, b.published_amount "
            + "FROM books b "
            + "JOIN books_authors ba on b.id = ba.book_id "
            + "JOIN authors a ON a.id = ba.author_id "
            + "WHERE a.author_name = ?1 "
            + "GROUP BY book_id "
            + "ORDER BY SUM(b.published_amount) DESC "
            + "LIMIT 1", nativeQuery = true)
    Book findMostPublishedBooksByAuthorName(String authorName);

    @Query(value =
            "SELECT DISTINCT(b) "
            + "FROM Book AS b "
            + "JOIN FETCH b.authors AS a "
            + "WHERE a.authorName LIKE %:name% "
            + "GROUP BY b.id "
            + "ORDER BY SUM(b.soldAmount) DESC")
    List<Book> findAllByTopSoldAmountAndAuthorNamePart(
            @Param("name") String wildcardAuthorName);

    @Query(value =
            "SELECT DISTINCT(b) "
            + "FROM Book AS b "
            + "JOIN FETCH b.authors AS a "
            + "WHERE a.authorName LIKE %:name% "
            + "GROUP BY b.id "
            + "ORDER BY SUM(b.publishedAmount) DESC")
    List<Book> findAllByTopPublishedAmountAndAuthorNamePart(
            @Param("name") String wildcardAuthorName);

    @Query(value =
            "SELECT new dev.home.library.model.dto.response.BookSuccessRateResponseDto"
                + "(b.id, b.bookName, b.publishedAmount, b.soldAmount, "
                + "(b.soldAmount / b.publishedAmount) * 100) "
            + "FROM Book AS b "
            + "JOIN b.authors AS a "
            + "WHERE a.authorName LIKE %:name% "
            + "ORDER BY b.soldAmount / b.publishedAmount DESC")
    Optional<List<BookSuccessRateResponseDto>> findAllBySuccessRateAndAuthorNamePart(
            @Param("name") String wildcardAuthorName);
}
