package ru.gb.springdemo.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.model.Issue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class BookRepository {

  private final List<Book> books;

  public BookRepository() {
    this.books = new ArrayList<>();
  }

  @PostConstruct
  public void generateData() {
    books.addAll(List.of(
      new Book("война и мир"),
      new Book("метрвые души"),
      new Book("чистый код")
    ));
  }

  public Book getBookById(long id) {
    return books.stream().filter(it -> Objects.equals(it.getId(), id))
      .findFirst()
      .orElse(null);
  }

  public Book getBookByName(String name) {
    return books.stream().filter(it -> Objects.equals(it.getName(), name))
            .findFirst()
            .orElse(null);
  }

  public void save(Book book) {
    // insert into ....
    books.add(book);
  }

  public void delete(Book book) {

    books.remove(book);
  }

}
