package dev.home.library.model.dto.request;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorRequestDto {
    private String authorName;
    private LocalDate birthDate;
    private String phone;
    private String email;
}
