package dev.home.library.service.mapper.impl;

import dev.home.library.model.Author;
import dev.home.library.model.dto.request.AuthorRequestDto;
import dev.home.library.model.dto.response.AuthorResponseDto;
import dev.home.library.service.mapper.RequestDtoMapper;
import dev.home.library.service.mapper.ResponseDtoMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper implements ResponseDtoMapper<AuthorResponseDto, Author>,
        RequestDtoMapper<AuthorRequestDto, Author> {
    @Override
    public AuthorResponseDto mapToDto(Author author) {
        AuthorResponseDto responseDto = new AuthorResponseDto();
        responseDto.setId(author.getId());
        responseDto.setAuthorName(author.getAuthorName());
        responseDto.setBirthDate(author.getBirthDate());
        responseDto.setPhone(author.getPhone());
        responseDto.setEmail(author.getEmail());
        return responseDto;
    }

    @Override
    public Author mapToModel(AuthorRequestDto dto) {
        Author author = new Author();
        author.setAuthorName(dto.getAuthorName());
        author.setBirthDate(dto.getBirthDate());
        author.setPhone(dto.getPhone());
        author.setEmail(dto.getEmail());
        return author;
    }
}
