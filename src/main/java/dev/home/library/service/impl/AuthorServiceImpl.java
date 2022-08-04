package library.service.impl;

import library.model.Author;
import library.repository.AuthorRepository;
import library.service.AuthorService;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    @Override
    public Author add(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author update(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public Author get(Long id) {
        return authorRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Author not found by id: " + id));
    }
}
