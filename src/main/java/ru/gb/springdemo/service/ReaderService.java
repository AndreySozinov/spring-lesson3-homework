package ru.gb.springdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.api.ReaderRequest;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.repository.ReaderRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReaderService {
    private final ReaderRepository readerRepository;

    public Reader create(ReaderRequest request) {
        if (readerRepository.findByName(request.getName()) != null) {
            throw new IllegalArgumentException("Читатель с таким названием уже есть");
        }

        Reader reader = new Reader(request.getName());
        return readerRepository.save(reader);
    }

    public Reader readInfo(long id) {
        return readerRepository.findById(id)
                .orElseThrow(() -> new  NoSuchElementException("Не найден читатель с идентификатором \"" + id + "\""));
    }

    public Reader delete(long id) {
        Reader reader = readInfo(id);
        readerRepository.deleteById(id);
        return reader;
    }

    public List<Reader> allReaders() {
        return readerRepository.findAll();
    }

}
