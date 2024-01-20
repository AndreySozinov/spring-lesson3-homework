package ru.gb.springdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.api.IssueRequest;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.repository.BookRepository;
import ru.gb.springdemo.repository.IssueRepository;
import ru.gb.springdemo.repository.ReaderRepository;

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
    if (bookRepository.getBookById(request.getBookId()) == null) {
      throw new NoSuchElementException("Не найдена книга с идентификатором \"" + request.getBookId() + "\"");
    }
    if (readerRepository.getReaderById(request.getReaderId()) == null) {
      throw new NoSuchElementException("Не найден читатель с идентификатором \"" + request.getReaderId() + "\"");
    }

    long booksAmountOnHand = issueRepository.getListOfIssues()
            .stream()
            .filter(it -> it.getReaderId() == request.getReaderId())
            .count();
    if (booksAmountOnHand > booksLimit) {
      throw new RuntimeException("Превышен лимит выдач для читателя с идентификатором \"" + request.getReaderId() + "\"");
    }

    Issue issue = new Issue(request.getBookId(), request.getReaderId());
    issueRepository.save(issue);
    return issue;
  }

  public Issue readInfo(long id) {
    Issue issue = issueRepository.getIssueById(id);
    if (issue == null) {
      throw new NoSuchElementException("Не найдена выдача с идентификатором \"" + id + "\"");
    }

    return issue;
  }

  public List<Issue> getAllIssues(long id) {
    if (readerRepository.getReaderById(id) == null) {
      throw new NoSuchElementException("Не найден читатель с идентификатором \"" + id + "\"");
    }

    List<Issue> foundIssues = issueRepository.getListOfIssues()
            .stream()
            .filter(it -> it.getReaderId() == id).toList();
    if (foundIssues.isEmpty()) {
      throw new NoSuchElementException("Не найдено ни одной выдачи читателю с идентификатором \"" + id + "\"");
    }
    return foundIssues;
  }

}
