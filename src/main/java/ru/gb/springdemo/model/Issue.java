package ru.gb.springdemo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Table(name = "issues")
@Data
@RequiredArgsConstructor
public class Issue {

    public static long sequence = 1L;
    @Id
    private final long id;

    @Column(name = "book_id")
    private final long bookId;

    @Column(name = "reader_id")
    private final long readerId;

    /**
     * Дата выдачи
     */
    @Column(name = "issued_at")
    private final LocalDateTime issued_at;
    /**
     * Дата возврата
     */
    @Column(name = "returned_at")
    private LocalDateTime returned_at;

    public Issue(long bookId, long readerId) {
        this.id = sequence++;
        this.bookId = bookId;
        this.readerId = readerId;
        this.issued_at = LocalDateTime.now();
    }
}
