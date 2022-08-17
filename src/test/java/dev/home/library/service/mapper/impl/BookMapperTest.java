package dev.home.library.service.mapper.impl;

import dev.home.library.model.Author;
import dev.home.library.model.Book;
import dev.home.library.model.dto.request.BookRequestDto;
import dev.home.library.model.dto.response.BookResponseDto;
import dev.home.library.service.AuthorService;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookMapperTest {
    private Book book;
    private BookMapper bookMapper;
    @Mock
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        bookMapper = new BookMapper(authorService);
        book = new Book();
        book.setId(1L);
        book.setBookName("Secret Garden");
        Author author1 = new Author();
        author1.setId(1L);
        author1.setAuthorName("Tom");
        Author author2 = new Author();
        author2.setId(2L);
        author2.setAuthorName("Mate");
        book.setAuthors(Arrays.asList(author1, author2));
        book.setSoldAmount(123);
        book.setPublishedAmount(321);
    }

    @Test
    void mapToDto_correctInput_ok() {
        BookResponseDto actual = bookMapper.mapToDto(book);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(book.getId(), actual.getId());
        Assertions.assertEquals(book.getAuthors().stream()
                        .map(Author::getId)
                        .collect(Collectors.toList()),
                actual.getAuthorIds());
        Assertions.assertEquals(book.getId(), actual.getId());
        Assertions.assertEquals(book.getPublishedAmount(), actual.getPublishedAmount());
        Assertions.assertEquals(book.getSoldAmount(), actual.getSoldAmount());
    }

    @Test
    void mapToModel_correctInput_ok() {
        BookRequestDto bookRequestDto = new BookRequestDto();
        bookRequestDto.setBookName(book.getBookName());
        bookRequestDto.setAuthorIds(book.getAuthors()
                .stream()
                .map(Author::getId)
                .collect(Collectors.toList()));
        bookRequestDto.setPublishedAmount(book.getPublishedAmount());
        bookRequestDto.setSoldAmount(book.getSoldAmount());
        Mockito.when(authorService.get(1L)).thenReturn(book.getAuthors().get(0));
        Mockito.when(authorService.get(2L)).thenReturn(book.getAuthors().get(1));
        Book actual = bookMapper.mapToModel(bookRequestDto);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(book.getBookName(), actual.getBookName());
        Assertions.assertEquals(book.getAuthors(), actual.getAuthors());
    }
}
