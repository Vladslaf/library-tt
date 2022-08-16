package dev.home.library.controller;

import dev.home.library.model.Author;
import dev.home.library.model.dto.request.AuthorRequestDto;
import dev.home.library.model.dto.response.AuthorSuccessRateResponseDto;
import dev.home.library.service.AuthorService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.time.LocalDate;
import java.time.Month;
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
class AuthorControllerTest {
    @MockBean
    private AuthorService authorService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void add_ok() {
        Author authorToSave = new Author("Mark", LocalDate.of(1987, Month.AUGUST, 5),
                "06315948457", "a@test.ua");
        Mockito.when(authorService.add(authorToSave)).thenReturn(new Author(45L, "Mark",
                LocalDate.of(1987, Month.AUGUST, 5), "06315948457", "a@test.ua"));

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new AuthorRequestDto(authorToSave.getAuthorName(), authorToSave.getBirthDate(),
                        authorToSave.getPhone(), authorToSave.getEmail()))
                .when()
                .post("/authors")
                .then()
                .statusCode(201)
                .status(HttpStatus.CREATED)
                .body("id", Matchers.equalTo(45))
                .body("authorName", Matchers.equalTo(authorToSave.getAuthorName()))
                .body("birthDate", Matchers.equalTo(authorToSave.getBirthDate().toString()))
                .body("email", Matchers.equalTo(authorToSave.getEmail()))
                .body("phone", Matchers.equalTo(authorToSave.getPhone()));
    }

    @Test
    void update_ok() {
        Author authorToUpdate = new Author(1L, "Mark", LocalDate.of(1987, Month.AUGUST, 5),
                "06315948457", "a@test.ua");
        Mockito.when(authorService.update(authorToUpdate)).thenReturn(authorToUpdate);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new AuthorRequestDto(authorToUpdate.getAuthorName(), authorToUpdate.getBirthDate(),
                        authorToUpdate.getPhone(), authorToUpdate.getEmail()))
                .when()
                .put("/authors/" + 1L)
                .then()
                .statusCode(200)
                .status(HttpStatus.OK)
                .body("id", Matchers.equalTo(1))
                .body("authorName", Matchers.equalTo(authorToUpdate.getAuthorName()))
                .body("birthDate", Matchers.equalTo(authorToUpdate.getBirthDate().toString()))
                .body("email", Matchers.equalTo(authorToUpdate.getEmail()))
                .body("phone", Matchers.equalTo(authorToUpdate.getPhone()));
    }

    @Test
    void delete_ok() {
        Author authorToUpdate = new Author(1L, "Mark", LocalDate.of(1987, Month.AUGUST, 5),
                "06315948457", "a@test.ua");
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/authors/" + 1L)
                .then()
                .statusCode(200)
                .status(HttpStatus.OK);
    }

    @Test
    void findTopBySuccessAuthorRate_ok() {
        AuthorSuccessRateResponseDto expectedDto = new AuthorSuccessRateResponseDto(1L, "Mark",
                LocalDate.of(1987, Month.AUGUST, 5), "06315948457", "a@test.ua", 50);
        Mockito.when(authorService.findTopBySuccessAuthorRate()).thenReturn(expectedDto);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/authors/top-author")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("authorName", Matchers.equalTo(expectedDto.getAuthorName()))
                .body("birthDate", Matchers.equalTo(expectedDto.getBirthDate().toString()))
                .body("email", Matchers.equalTo(expectedDto.getEmail()))
                .body("phone", Matchers.equalTo(expectedDto.getPhone()))
                .body("successRate", Matchers.equalTo((int) expectedDto.getSuccessRate()));
    }
}