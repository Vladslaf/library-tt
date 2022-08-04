package library.service;

import library.model.Author;

public interface AuthorService {
    Author add(Author author);
    Author update(Author author);
    void delete(Long id);
    Author get(Long id);
}
