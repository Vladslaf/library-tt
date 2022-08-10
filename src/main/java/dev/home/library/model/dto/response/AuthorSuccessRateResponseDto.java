package dev.home.library.model.dto.response;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class AuthorSuccessRateResponseDto {
    private Long id;
    private String authorName;
    private LocalDate birthDate;
    private String phone;
    private String email;
    private long successRate;

    public AuthorSuccessRateResponseDto() {
    }

    public AuthorSuccessRateResponseDto(Long id, String authorName, LocalDate birthDate,
                                        String phone, String email, long successRate) {
        this.id = id;
        this.authorName = authorName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
        this.successRate = successRate;
    }
}
