package dev.home.library.controller;

import dev.home.library.model.Book;
import dev.home.library.model.dto.request.BookRequestDto;
import dev.home.library.model.dto.response.BookResponseDto;
import dev.home.library.service.BookService;
import dev.home.library.service.mapper.RequestDtoMapper;
import dev.home.library.service.mapper.ResponseDtoMapper;
import java.util.List;
import java.util.stream.Collectors;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final RequestDtoMapper<BookRequestDto, Book> bookRequestDtoMapper;
    private final ResponseDtoMapper<BookResponseDto, Book> bookResponseDtoMapper;

    public BookController(BookService bookService,
                          RequestDtoMapper<BookRequestDto, Book> bookRequestDtoMapper,
                          ResponseDtoMapper<BookResponseDto, Book> bookResponseDtoMapper) {
        this.bookService = bookService;
        this.bookRequestDtoMapper = bookRequestDtoMapper;
        this.bookResponseDtoMapper = bookResponseDtoMapper;
    }

    @PostMapping
    @ApiOperation(value = "create new book")
    public BookResponseDto add(@RequestBody BookRequestDto requestDto) {
        Book book = bookRequestDtoMapper.mapToModel(requestDto);
        bookService.add(book);
        return bookResponseDtoMapper.mapToDto(book);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update book by id")
    public BookResponseDto update(@PathVariable Long id,
                                  @RequestBody BookRequestDto requestDto) {
        Book book = bookRequestDtoMapper.mapToModel(requestDto);
        book.setId(id);
        return bookResponseDtoMapper.mapToDto(bookService.update(book));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete book by id")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @GetMapping("/by-author")
    public List<BookResponseDto> findAllByAuthorName(@RequestParam String name) {
        return bookService.findAllByAuthorName(name)
                .stream()
                .map(bookResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/most-sold/by-author")
    public BookResponseDto findTopBySoldAmountAndAuthorName(@RequestParam String name) {
        Book book = bookService.findMostSoldBooksByAuthorName(name);
        return bookResponseDtoMapper.mapToDto(book);
    }

    @GetMapping("/most-published/by-author")
    public BookResponseDto findMostPublishedBooksByAuthorName(@RequestParam String name) {
        Book book = bookService.findMostPublishedBooksByAuthorName(name);
        return bookResponseDtoMapper.mapToDto(book);
    }

    @GetMapping("/all-by-most-sold/by-author")
    public List<BookResponseDto> findAllBySoldAmountAndAuthorName(
            @RequestParam @ApiParam(value = "any part of the author name") String wildcardAuthorName) {
        return bookService.findAllBySoldAmountAndAuthorNamePart(wildcardAuthorName)
                .stream()
                .map(bookResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/all-by-most-published/by-author")
    public List<BookResponseDto> findAllByPublishedAmountAndAuthorName(
            @RequestParam @ApiParam(value = "any part of the author name") String wildcardAuthorName) {
        return bookService.findAllByPublishedAmountAndAuthorNamePart(wildcardAuthorName)
                .stream()
                .map(bookResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/all-by-success/by-author")
    public List<BookResponseDto> findAllBySuccessRateAndAuthorName(
            @RequestParam @ApiParam(value = "any part of the author name") String wildcardAuthorName) {
        return bookService.findAllBySuccessRateAndAuthorNamePart(wildcardAuthorName)
                .stream()
                .map(bookResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
