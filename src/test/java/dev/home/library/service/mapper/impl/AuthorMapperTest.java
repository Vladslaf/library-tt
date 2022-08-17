package dev.home.library.service.mapper.impl;

import dev.home.library.model.Author;
import dev.home.library.model.dto.request.AuthorRequestDto;
import dev.home.library.model.dto.response.AuthorResponseDto;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthorMapperTest {
    private AuthorMapper authorMapper;
    private Author author;

    @BeforeEach
    void setUp() {
        authorMapper = new AuthorMapper();
        author = new Author();
        author.setId(1L);
        author.setAuthorName("Mark");
        author.setBirthDate(LocalDate.of(1987, Month.AUGUST, 5));
        author.setEmail("a@test.ua");
        author.setPhone("06315948457");
    }

    @Test
    void mapToDto_correctInput_ok() {
        AuthorResponseDto actual = authorMapper.mapToDto(author);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(author.getId(), actual.getId());
        Assertions.assertEquals(author.getAuthorName(), actual.getAuthorName());
        Assertions.assertEquals(author.getPhone(), actual.getPhone());
        Assertions.assertEquals(author.getBirthDate(), actual.getBirthDate());
        Assertions.assertEquals(author.getEmail(), actual.getEmail());
    }

    @Test
    void mapToModel_correctInput_ok() {
        AuthorRequestDto authorRequestDto = new AuthorRequestDto();
        authorRequestDto.setAuthorName(author.getAuthorName());
        authorRequestDto.setEmail(author.getEmail());
        authorRequestDto.setPhone(author.getPhone());
        authorRequestDto.setBirthDate(author.getBirthDate());
        Author actual = authorMapper.mapToModel(authorRequestDto);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(author.getAuthorName(), actual.getAuthorName());
        Assertions.assertEquals(author.getPhone(), actual.getPhone());
        Assertions.assertEquals(author.getBirthDate(), actual.getBirthDate());
        Assertions.assertEquals(author.getEmail(), actual.getEmail());
    }
}
