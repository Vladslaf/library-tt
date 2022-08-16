package dev.home.library.controller;

import dev.home.library.model.Author;
import dev.home.library.model.Book;
import dev.home.library.model.dto.request.BookRequestDto;
import dev.home.library.model.dto.response.BookResponseDto;
import dev.home.library.service.BookService;
import dev.home.library.service.mapper.RequestDtoMapper;
import dev.home.library.service.mapper.ResponseDtoMapper;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
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
import java.util.ArrayList;
import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {
    @MockBean
    private BookService bookService;

    @MockBean
    private RequestDtoMapper<BookRequestDto, Book> bookRequestDtoMapper;

    @MockBean
    private ResponseDtoMapper<BookResponseDto, Book> bookResponseDtoMapper;
    @Autowired
    private MockMvc mockMvc;

    private Book book;

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
    }

    @Test
    void add_ok() {
        Book bookToSave = book;
        bookToSave.setId(2L);
        Mockito.when(bookService.add(book)).thenReturn(bookToSave);
        BookRequestDto bookRequestDto = new BookRequestDto(book.getBookName(), Arrays.asList(1L, 2L),
                book.getPublishedAmount(), book.getSoldAmount());
        Mockito.when(bookRequestDtoMapper.mapToModel(bookRequestDto)).thenReturn(book);
        Mockito.when(bookResponseDtoMapper.mapToDto(bookToSave)).thenReturn(new BookResponseDto(2L,
                book.getBookName(), Arrays.asList(1L, 2L), book.getPublishedAmount(), book.getSoldAmount()));

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

//    @Test
//    void update_ok() {
//        Author authorToUpdate = new Author(1L, "Mark", LocalDate.of(1987, Month.AUGUST, 5),
//                "06315948457", "a@test.ua");
//        Mockito.when(bookService.update(authorToUpdate)).thenReturn(authorToUpdate);
//        RestAssuredMockMvc.given()
//                .contentType(ContentType.JSON)
//                .body(new AuthorRequestDto(authorToUpdate.getAuthorName(), authorToUpdate.getBirthDate(),
//                        authorToUpdate.getPhone(), authorToUpdate.getEmail()))
//                .when()
//                .put("/authors/" + 1L)
//                .then()
//                .statusCode(200)
//                .status(HttpStatus.OK)
//                .body("id", Matchers.equalTo(1))
//                .body("authorName", Matchers.equalTo(authorToUpdate.getAuthorName()))
//                .body("birthDate", Matchers.equalTo(authorToUpdate.getBirthDate().toString()))
//                .body("email", Matchers.equalTo(authorToUpdate.getEmail()))
//                .body("phone", Matchers.equalTo(authorToUpdate.getPhone()));
//    }
//
//    @Test
//    void delete_ok() {
//        Author authorToUpdate = new Author(1L, "Mark", LocalDate.of(1987, Month.AUGUST, 5),
//                "06315948457", "a@test.ua");
//        RestAssuredMockMvc.given()
//                .contentType(ContentType.JSON)
//                .when()
//                .delete("/authors/" + 1L)
//                .then()
//                .statusCode(200)
//                .status(HttpStatus.OK);
//    }
//
//    @Test
//    void findTopBySuccessAuthorRate_ok() {
//        AuthorSuccessRateResponseDto expectedDto = new AuthorSuccessRateResponseDto(1L, "Mark",
//                LocalDate.of(1987, Month.AUGUST, 5), "06315948457", "a@test.ua", 50);
//        Mockito.when(bookService.findTopBySuccessAuthorRate()).thenReturn(expectedDto);
//        RestAssuredMockMvc.given()
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/authors/top-author")
//                .then()
//                .statusCode(200)
//                .body("id", Matchers.equalTo(1))
//                .body("authorName", Matchers.equalTo(expectedDto.getAuthorName()))
//                .body("birthDate", Matchers.equalTo(expectedDto.getBirthDate().toString()))
//                .body("email", Matchers.equalTo(expectedDto.getEmail()))
//                .body("phone", Matchers.equalTo(expectedDto.getPhone()))
//                .body("successRate", Matchers.equalTo((int) expectedDto.getSuccessRate()));
//    }
}