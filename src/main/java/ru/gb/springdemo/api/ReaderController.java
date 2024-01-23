package ru.gb.springdemo.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.service.IssueService;
import ru.gb.springdemo.service.ReaderService;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/reader")
public class ReaderController {
    @Autowired
    private ReaderService service;
    private IssueService issueService;

    @PostMapping
    public ResponseEntity<Reader> addReader(@RequestBody ReaderRequest request) {
        log.info("Получен запрос на добавление нового читателя: name = {}", request.getName());

        final Reader reader;
        try {
            reader = service.create(request);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(reader);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Reader> getReaderInfo(@PathVariable long id) {
        log.info("Получен запрос на чтение информации о читателе: id = {}", id);

        final Reader reader;
        try {
            reader = service.readInfo(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(reader);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Reader> deleteReader(@PathVariable long id) {
        log.info("Получен запрос на удаление читателя: id = {}", id);

        final Reader reader;
        try {
            reader = service.delete(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(reader);
    }

    @GetMapping(path = "/{id}/issue")
    public ResponseEntity<List<Issue>> getAllReaderIssues(@PathVariable long id) {
        log.info("Получен запрос на чтение информации о всех выдачах читателя: id = {}", id);

        final List<Issue> issues;
        try {
            issues = issueService.getAllIssuesForReader(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(issues);
    }
}
