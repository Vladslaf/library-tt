package dev.home.library.controller;

import dev.home.library.model.Author;
import dev.home.library.model.dto.request.AuthorRequestDto;
import dev.home.library.model.dto.response.AuthorResponseDto;
import dev.home.library.service.AuthorService;
import dev.home.library.service.mapper.RequestDtoMapper;
import dev.home.library.service.mapper.ResponseDtoMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    public AuthorController(AuthorService authorService,
                            RequestDtoMapper<AuthorRequestDto, Author> authorRequestDtoMapper,
                            ResponseDtoMapper<AuthorResponseDto, Author> authorResponseDtoMapper) {
        this.authorService = authorService;
        this.authorRequestDtoMapper = authorRequestDtoMapper;
        this.authorResponseDtoMapper = authorResponseDtoMapper;
    }

    @PostMapping
    @ApiOperation(value = "add new author")
    public AuthorResponseDto add(@RequestBody AuthorRequestDto authorRequestDto) {
        Author author = authorService.add(authorRequestDtoMapper.mapToModel(authorRequestDto));
        authorService.add(author);
        return authorResponseDtoMapper.mapToDto(author);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update author by id")
    public AuthorResponseDto update(@PathVariable Long id,
                                    @RequestBody AuthorRequestDto authorRequestDto) {
        Author author = authorRequestDtoMapper.mapToModel(authorRequestDto);
        author.setId(id);
        return authorResponseDtoMapper.mapToDto(authorService.update(author));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete author by id")
    public void delete(@PathVariable Long id) {
        authorService.delete(id);
    }

    @GetMapping("/top-author")
    @ApiOperation(value = "show most successful author")
    public AuthorResponseDto findTopBySuccessAuthorRate() {
        Author author = authorService.findTopBySuccessAuthorRate();
        return authorResponseDtoMapper.mapToDto(author);
    }
}
