package library.controller;

import library.service.mapper.RequestDtoMapper;
import library.service.mapper.ResponseDtoMapper;
import library.model.Book;
import library.model.dto.request.BookRequestDto;
import library.model.dto.response.BookResponseDto;
import library.service.BookService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public BookResponseDto add(@RequestBody BookRequestDto requestDto) {
        Book book = bookRequestDtoMapper.mapToModel(requestDto);
        bookService.add(book);
        return bookResponseDtoMapper.mapToDto(book);
    }

    @PutMapping("/{id}")
    public BookResponseDto update(@PathVariable Long id,
                                  @RequestBody BookRequestDto requestDto) {
        Book book = bookRequestDtoMapper.mapToModel(requestDto);
        book.setId(id);
        bookService.update(book);
        return bookResponseDtoMapper.mapToDto(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }
}
