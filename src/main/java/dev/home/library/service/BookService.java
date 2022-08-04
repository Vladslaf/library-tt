package library.service;

import library.model.Book;

public interface BookService {
    Book add(Book book);
    Book update(Book book);
    void delete(Long id);
}
