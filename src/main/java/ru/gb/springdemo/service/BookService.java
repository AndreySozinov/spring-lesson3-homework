package ru.gb.springdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.api.BookRequest;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.repository.BookRepository;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Book create(BookRequest request) {
        if (bookRepository.findByName(request.getName()) != null) {
            throw new IllegalArgumentException("Книга с таким названием уже есть");
        }

        Book book = new Book(request.getName());
        return bookRepository.save(book);
    }

    public Book readInfo(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Не найдена книга с идентификатором \"" + id + "\""));
    }

    public Book delete(long id) {
        Book book = readInfo(id);
        bookRepository.deleteById(id);
        return book;
    }

    public List<Book> allBooks() {
        return bookRepository.findAll();
    }

}
