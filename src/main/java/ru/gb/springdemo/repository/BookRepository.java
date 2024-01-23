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
      new Book("Война и мир"),
      new Book("Мертвые души"),
      new Book("Чистый код"),
      new Book("Декамерон"),
      new Book("Горе от ума"),
      new Book("Дракула"),
      new Book("Капитал"),
      new Book("Воскресенье")
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

  public List<Book> getAllBooks() {
    return List.copyOf(books);
  }

}
