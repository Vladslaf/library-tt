package dev.home.library.model.dto.request;

import java.time.LocalDate;
import lombok.Data;

@Data
public class AuthorRequestDto {
    private String authorName;
    private LocalDate birthDate;
    private String phone;
    private String email;
}
