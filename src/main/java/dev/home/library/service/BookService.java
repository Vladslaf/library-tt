package dev.home.library.service;

import dev.home.library.model.Book;
import dev.home.library.model.dto.response.BookSuccessRateResponseDto;
import java.util.List;

public interface BookService {
    Book add(Book book);

    Book update(Book book);

    void delete(Long id);

    List<Book> findAllByAuthorName(String authorName);

    Book findMostSoldBooksByAuthorName(String authorName);

    Book findMostPublishedBooksByAuthorName(String authorName);

    List<Book> findAllBySoldAmountAndAuthorNamePart(String partName);

    List<Book> findAllByPublishedAmountAndAuthorNamePart(String partName);

    List<BookSuccessRateResponseDto> findAllBySuccessRateAndAuthorNamePart(String partName);
}
