package dev.home.library.service;

import dev.home.library.model.Author;
import dev.home.library.model.dto.response.AuthorSuccessRateResponseDto;

public interface AuthorService {
    Author add(Author author);

    Author update(Author author);

    void delete(Long id);

    Author get(Long id);

    AuthorSuccessRateResponseDto findTopBySuccessAuthorRate();
}
