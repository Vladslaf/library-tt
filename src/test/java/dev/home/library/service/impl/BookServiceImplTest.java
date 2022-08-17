package dev.home.library.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import dev.home.library.repository.BookRepository;
import dev.home.library.service.BookService;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;
    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookRepository);
    }

    @Test
    void findAllBySuccessRateAndAuthorNamePart_throws_notOk() {
        String partName = "name";
        Mockito.when(bookRepository.findAllBySuccessRateAndAuthorNamePart(partName))
                .thenReturn(Optional.empty());
        RuntimeException actualException = assertThrows(RuntimeException.class,
                () -> bookService.findAllBySuccessRateAndAuthorNamePart(partName));
        String expectedMessage = "No such author by part of the name : " + partName;
        Assertions.assertEquals(expectedMessage, actualException.getMessage());
    }
}
