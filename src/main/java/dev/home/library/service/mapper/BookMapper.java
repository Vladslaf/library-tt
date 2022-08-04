package library.service.mapper;

import library.model.Author;
import library.model.Book;
import library.model.dto.request.BookRequestDto;
import library.model.dto.response.BookResponseDto;
import library.service.AuthorService;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class BookMapper implements ResponseDtoMapper<BookResponseDto, Book>,
        RequestDtoMapper<BookRequestDto, Book> {
    private final AuthorService authorService;

    public BookMapper(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public BookResponseDto mapToDto(Book book) {
        BookResponseDto responseDto = new BookResponseDto();
        responseDto.setId(book.getId());
        responseDto.setBookName(book.getBookName());
        responseDto.setAuthorIds(book.getAuthors()
                .stream()
                .map(Author::getId)
                .collect(Collectors.toList()));
        responseDto.setPublishedAmount(book.getPublishedAmount());
        responseDto.setSoldAmount(book.getSoldAmount());
        return responseDto;
    }

    @Override
    public Book mapToModel(BookRequestDto dto) {
        Book book = new Book();
        book.setBookName(dto.getBookName());
        book.setAuthors(dto.getAuthorIds()
                .stream()
                .map(authorService::get)
                .collect(Collectors.toList()));
        book.setPublishedAmount(dto.getPublishedAmount());
        book.setSoldAmount(dto.getSoldAmount());
        return book;
    }
}
