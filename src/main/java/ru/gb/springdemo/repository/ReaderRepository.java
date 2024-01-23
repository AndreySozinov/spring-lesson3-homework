package ru.gb.springdemo.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.model.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ReaderRepository {

  private final List<Reader> readers;

  public ReaderRepository() {
    this.readers = new ArrayList<>();
  }

  @PostConstruct
  public void generateData() {
    readers.addAll(List.of(
      new ru.gb.springdemo.model.Reader("Игорь Смирнов"),
      new ru.gb.springdemo.model.Reader("Андрей Иванов"),
      new ru.gb.springdemo.model.Reader("Вася Петров"),
      new ru.gb.springdemo.model.Reader("Петя Кузнецов"),
      new ru.gb.springdemo.model.Reader("Оксана Семенова"),
      new ru.gb.springdemo.model.Reader("Ирина Новикова")
    ));
  }

  public Reader getReaderById(long id) {
    return readers.stream().filter(it -> Objects.equals(it.getId(), id))
      .findFirst()
      .orElse(null);
  }

  public Reader getReaderByName(String name) {
    return readers.stream().filter(it -> Objects.equals(it.getName(), name))
            .findFirst()
            .orElse(null);
  }

  public void save(Reader reader) {
    // insert into ....
    readers.add(reader);
  }

  public void delete(Reader reader) {

    readers.remove(reader);
  }

  public List<Reader> getAllReaders() {
    return List.copyOf(readers);
  }

}
