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
@Table(name = "readers")
@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Schema(name = "Читатель")
public class Reader {

    public static long sequence = 1L;

    @Id
    @Schema(name = "Идентификатор")
    private final long id;

    @Column(name = "name", length = 100)
    @Schema(name = "ФИО", maxLength = 100)
    private final String name;

    public Reader(String name) {
        this(sequence++, name);
    }
}
