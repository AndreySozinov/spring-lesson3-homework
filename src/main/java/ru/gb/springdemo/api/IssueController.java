package ru.gb.springdemo.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.service.IssueService;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/issue")
@Tag(name = "Issue")
public class IssueController {

  @Autowired
  private IssueService service;

  @PutMapping(path = "/{issueId}")
  @Operation(summary = "return the book by issue id", description = "Возвращает взятую книгу по ID выдачи")
  public ResponseEntity<Issue> returnBook(@PathVariable long issueId) {
    log.info("Возврат книги по выдаче с id = {}", issueId);

    final Issue issue;
    try {
      issue = service.returnBook(issueId);
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.status(HttpStatus.OK).body(issue);

  }

  @PostMapping
  @Operation(summary = "create new issue", description = "Создает новый факт выдачи книги читателю по их ID")
  public ResponseEntity<Issue> issueBook(@RequestBody IssueRequest request) {
    log.info("Получен запрос на выдачу: readerId = {}, bookId = {}", request.getReaderId(), request.getBookId());

    final Issue issue;
    try {
      issue = service.issue(request);
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    return ResponseEntity.status(HttpStatus.OK).body(issue);
  }

  @GetMapping(path = "/{id}")
  @Operation(summary = "get issue by ID", description = "Загружает факт выдачи из базы данных по ID")
  public ResponseEntity<Issue> getIssueInfo(@PathVariable long id) {
    log.info("Получен запрос на чтение информации о факте выдачи книги: id = {}", id);

    final Issue issue;
    try {
      issue = service.readInfo(id);
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.status(HttpStatus.OK).body(issue);
  }

}
