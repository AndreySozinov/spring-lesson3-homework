package ru.gb.springdemo.api;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gb.springdemo.JUnitSpringBootBase;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.repository.ReaderRepository;

import java.util.List;
import java.util.Objects;

public class ReaderControllerTest extends JUnitSpringBootBase {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ReaderRepository readerRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Data
    static class JUnitReaderResponse {
        private Long id;
        private String name;
    }

    @BeforeEach
    void clearingDataBase() {
        readerRepository.deleteAll();
    }

    @Test
    void testCreate() {
        JUnitReaderResponse request = new JUnitReaderResponse();
        request.setId(1L);
        request.setName("John Smith");

        JUnitReaderResponse responseBody = webTestClient.post()
                .uri("/reader")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(JUnitReaderResponse.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertNotNull(responseBody.getId());

        Assertions.assertTrue(readerRepository.findById(request.getId()).isPresent());
    }

    @Test
    void testFindByIdSuccess() {
        Reader expected = readerRepository.save(new Reader(1L, "John Smith"));

        JUnitReaderResponse responseBody = webTestClient.get()
                .uri("/reader/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitReaderResponse.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.getId(), responseBody.getId());
        Assertions.assertEquals(expected.getName(), responseBody.getName());
    }


    @Test
    void testFindByIdNotFound() {
        readerRepository.save(new Reader(1L, "John Smith"));
        Long maxId = jdbcTemplate.queryForObject("select max(id) from readers", Long.class);

        webTestClient.get()
                .uri("/reader/" + maxId + 1)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testGetAll() {
        readerRepository.saveAll(List.of(
                new Reader(1L, "John Smith"),
                new Reader(2L, "Vladimir Lenin")
        ));

        List<Reader> expected = readerRepository.findAll();

        List<JUnitReaderResponse> responseBody = webTestClient.get()
                .uri("/reader/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<JUnitReaderResponse>>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.size(), responseBody.size());
        for (JUnitReaderResponse readerResponse : responseBody) {
            boolean found = expected.stream()
                    .filter(it -> Objects.equals(it.getName(), readerResponse.getName()))
                    .anyMatch(it -> Objects.equals(it.getName(), readerResponse.getName()));
            Assertions.assertTrue(found);
        }
    }

    @Test
    void testDeleteById() {
        readerRepository.saveAll(List.of(
                new Reader(1L, "John Smith"),
                new Reader(2L, "Vlad Dracula")));
        Long maxId = jdbcTemplate.queryForObject("select max(id) from readers", Long.class);

        Integer rowsBeforeDeleting = jdbcTemplate.queryForObject("select count(1) from readers", Integer.class);
        webTestClient.delete()
                .uri("/reader/" + maxId)
                .exchange()
                .expectStatus().isOk();
        Integer rowsAfterDeleting = jdbcTemplate.queryForObject("select count(1) from readers", Integer.class);

        Assertions.assertEquals(1, rowsBeforeDeleting - rowsAfterDeleting);
    }

    @Test
    @Disabled
    void testUpdate() {
        // TODO: Не реализовано в приложении
    }


}
