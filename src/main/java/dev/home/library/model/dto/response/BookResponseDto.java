package dev.home.library.model.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDto {
    private Long id;
    private String bookName;
    private List<Long> authorIds;
    private int publishedAmount;
    private int soldAmount;
}
