package ru.gb.springdemo.api;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.service.BookService;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/book")
public class BookController {


    @Autowired
    private BookService service;

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody BookRequest request) {
        log.info("Получен запрос на добавление новой книги: name = {}", request.getName());

        final Book book;
        try {
            book = service.create(request);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Book> getBookInfo(@PathVariable long id) {
        log.info("Получен запрос на чтение информации о книге: id = {}", id);

        final Book book;
        try {
            book = service.readInfo(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable long id) {
        log.info("Получен запрос на удаление книги: id = {}", id);

        final Book book;
        try {
            book = service.delete(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    // @PostConstruct
//      bookRepository.save(new Book("Мертвые души"));
//      bookRepository.save(new Book("Чистый код"));
//      bookRepository.save(new Book("Декамерон"));
//      bookRepository.save(new Book("Горе от ума"));
//      bookRepository.save(new Book("Дракула"));
//      bookRepository.save(new Book("Капитал"));
//      bookRepository.save(new Book("Воскресенье"));

}
