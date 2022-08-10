package dev.home.library.model.dto.response;

import lombok.Data;

@Data
public class BookSuccessRateResponseDto {
    private Long id;
    private String bookName;
    private int publishedAmount;
    private int soldAmount;
    private int successRate;

    public BookSuccessRateResponseDto() {
    }

    public BookSuccessRateResponseDto(Long id, String bookName,
                                      int publishedAmount, int soldAmount, int successRate) {
        this.id = id;
        this.bookName = bookName;
        this.publishedAmount = publishedAmount;
        this.soldAmount = soldAmount;
        this.successRate = successRate;
    }
}
