package dev.home.library.model.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookRequestDto {
    private String bookName;
    private List<Long> authorIds;
    private int publishedAmount;
    private int soldAmount;
}
