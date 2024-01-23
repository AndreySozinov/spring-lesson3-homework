package ru.gb.springdemo.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.model.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class IssueRepository {

  private final List<Issue> issues;

  public IssueRepository() {
    this.issues = new ArrayList<>();
  }

  @PostConstruct
  public void generateData() {
    issues.addAll(List.of(
            new Issue(5, 2),
            new Issue(2, 1),
            new Issue(4, 6),
            new Issue(8, 1),
            new Issue(1, 3),
            new Issue(7, 4),
            new Issue(3, 5),
            new Issue(6, 1)
    ));
  }

  public void save(Issue issue) {
    // insert into ....
    issues.add(issue);
  }

  public Issue getIssueById(long id) {
    return issues.stream().filter(it -> Objects.equals(it.getId(), id))
            .findFirst()
            .orElse(null);
  }

  public List<Issue> getListOfIssues() {
    return List.copyOf(issues);
  }

  public void update(Issue newIssue) {
    Issue oldIssue = issues.stream().filter(it -> Objects.equals(it.getId(), newIssue.getId()))
            .findFirst().orElse(null);
    if (oldIssue != null){
      int index = issues.indexOf(oldIssue);
      issues.set(index, newIssue);
    }
  }
}
