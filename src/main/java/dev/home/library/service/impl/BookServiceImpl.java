package dev.home.library.service.impl;

import dev.home.library.model.Book;
import dev.home.library.model.dto.response.BookSuccessRateResponseDto;
import dev.home.library.repository.BookRepository;
import dev.home.library.service.BookService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book add(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book update(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> findAllByAuthorName(String authorName) {
        return bookRepository.findAllByAuthorName(authorName);
    }

    @Override
    public Book findMostSoldBooksByAuthorName(String authorName) {
        return bookRepository.findMostSoldBooksByAuthorName(authorName);
    }

    @Override
    public Book findMostPublishedBooksByAuthorName(String authorName) {
        return bookRepository.findMostPublishedBooksByAuthorName(authorName);
    }

    @Override
    public List<Book> findAllBySoldAmountAndAuthorNamePart(String partName) {
        return bookRepository.findAllByTopSoldAmountAndAuthorNamePart(partName);
    }

    @Override
    public List<Book> findAllByPublishedAmountAndAuthorNamePart(String partName) {
        return bookRepository.findAllByTopPublishedAmountAndAuthorNamePart(partName);
    }

    @Override
    public List<BookSuccessRateResponseDto> findAllBySuccessRateAndAuthorNamePart(String partName) {
        return bookRepository.findAllBySuccessRateAndAuthorNamePart(partName).orElseThrow(
                () -> new RuntimeException("No such author by part of the name : " + partName));
    }
}
