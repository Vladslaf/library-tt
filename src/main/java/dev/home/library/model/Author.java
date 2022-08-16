package dev.home.library.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.StringJoiner;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "author_name", nullable = false)
    private String authorName;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    private String phone;
    private String email;

    public Author(String authorName, LocalDate birthDate, String phone, String email) {
        this.authorName = authorName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Author author = (Author) o;
        return Objects.equals(id, author.id)
                && Objects.equals(authorName, author.authorName)
                && Objects.equals(birthDate, author.birthDate)
                && Objects.equals(phone, author.phone)
                && Objects.equals(email, author.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authorName, birthDate, phone, email);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Author.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("authorName='" + authorName + "'")
                .add("birthDate=" + birthDate)
                .add("phone='" + phone + "'")
                .add("email='" + email + "'")
                .toString();
    }
}
