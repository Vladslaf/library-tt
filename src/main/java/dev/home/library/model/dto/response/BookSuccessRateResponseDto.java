package dev.home.library.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSuccessRateResponseDto {
    private Long id;
    private String bookName;
    private int publishedAmount;
    private int soldAmount;
    private int successRate;
}
