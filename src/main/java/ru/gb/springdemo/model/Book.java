package ru.gb.springdemo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "books")
@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Schema(name = "Книга")
public class Book {

    public static long sequence = 1L;

    @Id
    @Schema(name = "Идентификатор")
    private final long id;

    @Column(name = "name", length = 300)
    @Schema(name = "Название", maxLength = 300)
    private final String name;

    public Book(String name) {
        this(sequence++, name);
    }
}
