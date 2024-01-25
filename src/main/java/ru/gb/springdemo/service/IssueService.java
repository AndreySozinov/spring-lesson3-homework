package ru.gb.springdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.api.IssueRequest;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.repository.BookRepository;
import ru.gb.springdemo.repository.IssueRepository;
import ru.gb.springdemo.repository.ReaderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class IssueService {

  private final BookRepository bookRepository;
  private final ReaderRepository readerRepository;
  private final IssueRepository issueRepository;

  @Value("${application.max-allowed-books:1}")
  private long booksLimit;


  public Issue issue(IssueRequest request) {
    long bookId = request.getBookId();
    long readerId = request.getReaderId();
    bookRepository.findById(bookId).orElseThrow(() ->
                    new NoSuchElementException("Не найдена книга с идентификатором \"" + bookId + "\""));
    readerRepository.findById(readerId).orElseThrow(() ->
            new NoSuchElementException("Не найден читатель с идентификатором \"" + readerId + "\""));

    long booksAmountOnHand = issueRepository.findAll()
            .stream()
            .filter(it -> it.getReaderId() == readerId)
            .filter(it -> it.getReturned_at() == null)
            .count();
    if (booksAmountOnHand > booksLimit) {
      throw new RuntimeException("Превышен лимит выдач для читателя с идентификатором \"" + readerId + "\"");
    }

    Issue issue = new Issue(request.getBookId(), request.getReaderId());
    return issueRepository.save(issue);
  }

  public Issue readInfo(long id) {
    return issueRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Не найдена выдача с идентификатором \"" + id + "\""));
  }

  public List<Issue> getAllIssuesForReader(long id) {
    readerRepository.findById(id).orElseThrow(() ->
            new NoSuchElementException("Не найден читатель с идентификатором \"" + id + "\""));

    List<Issue> foundIssues = issueRepository.findAll()
            .stream()
            .filter(it -> it.getReaderId() == id).toList();
    if (foundIssues.isEmpty()) {
      throw new NoSuchElementException("Не найдено ни одной выдачи читателю с идентификатором \"" + id + "\"");
    }
    return foundIssues;
  }

  public Issue returnBook(long id) {
    Issue issue = readInfo(id);
    issue.setReturned_at(LocalDateTime.now());
    return issueRepository.save(issue);
  }

  public List<Issue> AllIssues() {
    return issueRepository.findAll();
  }

}
