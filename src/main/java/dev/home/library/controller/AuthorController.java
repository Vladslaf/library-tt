package library.controller;

import library.service.mapper.RequestDtoMapper;
import library.service.mapper.ResponseDtoMapper;
import library.model.Author;
import library.model.dto.request.AuthorRequestDto;
import library.model.dto.response.AuthorResponseDto;
import library.service.AuthorService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;
    private final RequestDtoMapper<AuthorRequestDto, Author> authorRequestDtoMapper;
    private final ResponseDtoMapper<AuthorResponseDto, Author> authorResponseDtoMapper;

    public AuthorController(AuthorService authorService, RequestDtoMapper<AuthorRequestDto, Author> authorRequestDtoMapper, ResponseDtoMapper<AuthorResponseDto, Author> authorResponseDtoMapper) {
        this.authorService = authorService;
        this.authorRequestDtoMapper = authorRequestDtoMapper;
        this.authorResponseDtoMapper = authorResponseDtoMapper;
    }

    @PostMapping
    public AuthorResponseDto add(@RequestBody AuthorRequestDto authorRequestDto) {
        Author author = authorService.add(authorRequestDtoMapper.mapToModel(authorRequestDto));
        authorService.add(author);
        return authorResponseDtoMapper.mapToDto(author);
    }

    @PutMapping("/{id}")
    public AuthorResponseDto update(@PathVariable Long id, @RequestBody AuthorRequestDto authorRequestDto) {
        Author author = authorService.update(authorRequestDtoMapper.mapToModel(authorRequestDto));
        author.setId(id);
        return authorResponseDtoMapper.mapToDto(author);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        authorService.delete(id);
    }
}
