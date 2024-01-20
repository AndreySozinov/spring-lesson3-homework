package ru.gb.springdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.api.BookRequest;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.repository.BookRepository;

import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Book create(BookRequest request) {
        if (bookRepository.getBookByName(request.getName()) != null) {
            throw new IllegalArgumentException("Книга с таким названием уже есть");
        }

        Book book = new Book(request.getName());
        bookRepository.save(book);
        return book;
    }

    public Book readInfo(long id) {
        Book book = bookRepository.getBookById(id);
        if (book == null) {
            throw new NoSuchElementException("Не найдена книга с идентификатором \"" + id + "\"");
        }

        return book;
    }

    public Book delete(long id) {
        Book book = bookRepository.getBookById(id);
        if (book == null) {
            throw new NoSuchElementException("Не найдена книга с идентификатором \"" + id + "\"");
        }
        bookRepository.delete(book);
        return book;
    }
}
