package library.model.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class BookResponseDto {
    private Long id;
    private String bookName;
    private List<Long> authorIds;
    private int publishedAmount;
    private int soldAmount;
}
