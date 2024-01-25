package ru.gb.springdemo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "readers")
@Data
@RequiredArgsConstructor
public class Reader {

    public static long sequence = 1L;
    @Id
    private final long id;

    @Column(name = "name", length = 100)
    private final String name;

    public Reader(String name) {
        this(sequence++, name);
    }
}
