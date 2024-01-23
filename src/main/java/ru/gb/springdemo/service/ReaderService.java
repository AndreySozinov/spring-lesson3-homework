package ru.gb.springdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.api.ReaderRequest;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.repository.ReaderRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReaderService {
    private final ReaderRepository readerRepository;

    public Reader create(ReaderRequest request) {
        if (readerRepository.getReaderByName(request.getName()) != null) {
            throw new IllegalArgumentException("Читатель с таким названием уже есть");
        }

        Reader reader = new Reader(request.getName());
        readerRepository.save(reader);
        return reader;
    }

    public Reader readInfo(long id) {
        Reader reader = readerRepository.getReaderById(id);
        if (reader == null) {
            throw new NoSuchElementException("Не найден читатель с идентификатором \"" + id + "\"");
        }

        return reader;
    }

    public Reader delete(long id) {
        Reader reader = readerRepository.getReaderById(id);
        if (reader == null) {
            throw new NoSuchElementException("Не найден читатель с идентификатором \"" + id + "\"");
        }
        readerRepository.delete(reader);
        return reader;
    }

    public List<Reader> allReaders() {
        return readerRepository.getAllReaders();
    }
}
