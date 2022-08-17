package dev.home.library.controller;

import dev.home.library.model.Author;
import dev.home.library.model.Book;
import dev.home.library.model.dto.request.BookRequestDto;
import dev.home.library.model.dto.response.BookResponseDto;
import dev.home.library.model.dto.response.BookSuccessRateResponseDto;
import dev.home.library.service.BookService;
import dev.home.library.service.mapper.RequestDtoMapper;
import dev.home.library.service.mapper.ResponseDtoMapper;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.Arrays;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {
    private static final String WILDCARD_NAME = "oM";
    private static final String AUTHOR_NAME = "Tom";

    @MockBean
    private BookService bookService;

    @MockBean
    private RequestDtoMapper<BookRequestDto, Book> bookRequestDtoMapper;

    @MockBean
    private ResponseDtoMapper<BookResponseDto, Book> bookResponseDtoMapper;
    @Autowired
    private MockMvc mockMvc;

    private Book book;
    private BookRequestDto bookRequestDto;
    private BookResponseDto bookResponseDto;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
        book = new Book();
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
        bookRequestDto = new BookRequestDto(book.getBookName(), Arrays.asList(1L, 2L),
                book.getPublishedAmount(), book.getSoldAmount());
        bookResponseDto = new BookResponseDto(1L, book.getBookName(), Arrays.asList(1L, 2L),
                book.getPublishedAmount(), book.getSoldAmount());
    }

    @Test
    void add_ok() {
        Book bookToSave = book;
        bookToSave.setId(2L);
        Mockito.when(bookService.add(book)).thenReturn(bookToSave);
        Mockito.when(bookRequestDtoMapper.mapToModel(bookRequestDto)).thenReturn(book);
        Mockito.when(bookResponseDtoMapper.mapToDto(bookToSave)).thenReturn(new BookResponseDto(2L,
                book.getBookName(), Arrays.asList(1L, 2L), book.getPublishedAmount(),
                book.getSoldAmount()));
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(bookRequestDto)
                .when()
                .post("/books")
                .then()
                .statusCode(200)
                .status(HttpStatus.OK)
                .body("id", Matchers.equalTo(2))
                .body("bookName", Matchers.equalTo(book.getBookName()))
                .body("authorIds", Matchers.equalTo(Arrays.asList(1, 2)))
                .body("publishedAmount", Matchers.equalTo(book.getPublishedAmount()))
                .body("soldAmount", Matchers.equalTo(book.getSoldAmount()));
    }

    @Test
    void update_ok() {
        Book bookToSave = book;
        bookToSave.setId(2L);
        Mockito.when(bookService.update(book)).thenReturn(bookToSave);
        Mockito.when(bookRequestDtoMapper.mapToModel(bookRequestDto)).thenReturn(book);
        Mockito.when(bookResponseDtoMapper.mapToDto(bookToSave)).thenReturn(new BookResponseDto(2L,
                book.getBookName(), Arrays.asList(1L, 2L), book.getPublishedAmount(),
                book.getSoldAmount()));
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(bookRequestDto)
                .when()
                .put("/books/" + 2L)
                .then()
                .statusCode(200)
                .status(HttpStatus.OK)
                .body("id", Matchers.equalTo(2))
                .body("bookName", Matchers.equalTo(book.getBookName()))
                .body("authorIds", Matchers.equalTo(Arrays.asList(1, 2)))
                .body("publishedAmount", Matchers.equalTo(book.getPublishedAmount()))
                .body("soldAmount", Matchers.equalTo(book.getSoldAmount()));
    }

    @Test
    void delete_ok() {
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/books/" + 1L)
                .then()
                .statusCode(200)
                .status(HttpStatus.OK);
    }

    @Test
    void findAllByAuthorName_ok() {
        Mockito.when(bookService.findAllByAuthorName(AUTHOR_NAME))
                        .thenReturn(List.of(book));
        Mockito.when(bookResponseDtoMapper.mapToDto(book))
                        .thenReturn(bookResponseDto);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .param("name", AUTHOR_NAME)
                .when()
                .get("/books/by-author")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(1))
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].bookName", Matchers.equalTo(book.getBookName()))
                .body("[0].authorIds", Matchers.equalTo(Arrays.asList(1, 2)))
                .body("[0].publishedAmount", Matchers.equalTo(book.getPublishedAmount()))
                .body("[0].soldAmount", Matchers.equalTo(book.getSoldAmount()));
    }

    @Test
    void findTopBySoldAmountAndAuthorName_ok() {
        Mockito.when(bookService.findMostSoldBooksByAuthorName(AUTHOR_NAME))
                .thenReturn(book);
        Mockito.when(bookResponseDtoMapper.mapToDto(book))
                .thenReturn(bookResponseDto);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .param("name", AUTHOR_NAME)
                .when()
                .get("/books/most-sold/by-author")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("bookName", Matchers.equalTo(book.getBookName()))
                .body("authorIds", Matchers.equalTo(Arrays.asList(1, 2)))
                .body("publishedAmount", Matchers.equalTo(book.getPublishedAmount()))
                .body("soldAmount", Matchers.equalTo(book.getSoldAmount()));
    }

    @Test
    void findMostPublishedBooksByAuthorName_ok() {
        Mockito.when(bookService.findMostPublishedBooksByAuthorName(AUTHOR_NAME))
                .thenReturn(book);
        Mockito.when(bookResponseDtoMapper.mapToDto(book))
                .thenReturn(bookResponseDto);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .param("name", AUTHOR_NAME)
                .when()
                .get("/books/most-published/by-author")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("bookName", Matchers.equalTo(book.getBookName()))
                .body("authorIds", Matchers.equalTo(Arrays.asList(1, 2)))
                .body("publishedAmount", Matchers.equalTo(book.getPublishedAmount()))
                .body("soldAmount", Matchers.equalTo(book.getSoldAmount()));
    }

    @Test
    void findAllBySoldAmountAndAuthorName_ok() {
        Mockito.when(bookService.findAllBySoldAmountAndAuthorNamePart(WILDCARD_NAME))
                .thenReturn(List.of(book));
        Mockito.when(bookResponseDtoMapper.mapToDto(book))
                .thenReturn(bookResponseDto);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .param("wildcardAuthorName", WILDCARD_NAME)
                .when()
                .get("/books/all-by-most-sold/by-author")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(1))
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].bookName", Matchers.equalTo(book.getBookName()))
                .body("[0].authorIds", Matchers.equalTo(Arrays.asList(1, 2)))
                .body("[0].publishedAmount", Matchers.equalTo(book.getPublishedAmount()))
                .body("[0].soldAmount", Matchers.equalTo(book.getSoldAmount()));
    }

    @Test
    void findAllByPublishedAmountAndAuthorName_ok() {
        Mockito.when(bookService.findAllByPublishedAmountAndAuthorNamePart(WILDCARD_NAME))
                .thenReturn(List.of(book));
        Mockito.when(bookResponseDtoMapper.mapToDto(book))
                .thenReturn(bookResponseDto);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .param("wildcardAuthorName", WILDCARD_NAME)
                .when()
                .get("/books/all-by-most-published/by-author")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(1))
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].bookName", Matchers.equalTo(book.getBookName()))
                .body("[0].authorIds", Matchers.equalTo(Arrays.asList(1, 2)))
                .body("[0].publishedAmount", Matchers.equalTo(book.getPublishedAmount()))
                .body("[0].soldAmount", Matchers.equalTo(book.getSoldAmount()));
    }

    @Test
    void findAllByAuthorsEndsWithIgnoreCaseBySuccessRate_ok() {
        BookSuccessRateResponseDto bookRateResponseDto = new BookSuccessRateResponseDto(1L,
                book.getBookName(), book.getPublishedAmount(), book.getSoldAmount(), 50);
        Mockito.when(bookService.findAllBySuccessRateAndAuthorNamePart(WILDCARD_NAME))
                .thenReturn(List.of(bookRateResponseDto));
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .param("wildcardAuthorName", WILDCARD_NAME)
                .when()
                .get("/books/all-by-success-rate/by-author")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(1))
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].bookName", Matchers.equalTo(book.getBookName()))
                .body("[0].publishedAmount", Matchers.equalTo(book.getPublishedAmount()))
                .body("[0].soldAmount", Matchers.equalTo(book.getSoldAmount()))
                .body("[0].successRate", Matchers.equalTo(50));
    }
}
