package ru.gb.springdemo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "books")
@Data
@RequiredArgsConstructor
public class Book {

    public static long sequence = 1L;

    @Id
    private final long id;

    @Column(name = "name", length = 300)
    private final String name;

    public Book(String name) {
        this(sequence++, name);
    }
}
