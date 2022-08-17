package dev.home.library.service.impl;

import dev.home.library.repository.AuthorRepository;
import dev.home.library.service.AuthorService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {
    @Mock
    private AuthorRepository authorRepository;
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        authorService = new AuthorServiceImpl(authorRepository);
    }

    @Test
    void get_nonExistingAuthor_notOk() {
        Long id = 2L;
        Mockito.when(authorRepository.findById(id))
                .thenReturn(Optional.empty());
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class,
                () -> authorService.get(id));
        String expectedMessage = "Author not found by id: " + id;
        Assertions.assertEquals(expectedMessage, runtimeException.getMessage());
    }

    @Test
    void findTopBySuccessAuthorRate_throws_notOk() {
        Mockito.when(authorRepository.findFirstBySuccessAuthorRate())
                        .thenReturn(List.of(Optional.empty()));
        RuntimeException actualException = Assertions.assertThrows(RuntimeException.class,
                () -> authorService.findTopBySuccessAuthorRate());
        String expectedMessage = "No author found.";
        Assertions.assertEquals(expectedMessage, actualException.getMessage());
    }
}
