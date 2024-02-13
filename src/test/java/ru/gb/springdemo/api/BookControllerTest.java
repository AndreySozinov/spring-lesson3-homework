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
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.repository.BookRepository;

import java.util.List;
import java.util.Objects;

public class BookControllerTest extends JUnitSpringBootBase {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Data
    static class JUnitBookResponse {
        private Long id;
        private String name;
    }

    @BeforeEach
    void clearingDataBase() {
        bookRepository.deleteAll();
    }

    @Test
    void testCreate() {
        JUnitBookResponse request = new JUnitBookResponse();
        request.setId(1L);
        request.setName("Bible");

        JUnitBookResponse responseBody = webTestClient.post()
                .uri("/book")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(JUnitBookResponse.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertNotNull(responseBody.getId());

        Assertions.assertTrue(bookRepository.findById(request.getId()).isPresent());
    }

    @Test
    void testFindByIdSuccess() {
        Book expected = bookRepository.save(new Book(1L, "Bible"));

        JUnitBookResponse responseBody = webTestClient.get()
                .uri("/book/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitBookResponse.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.getId(), responseBody.getId());
        Assertions.assertEquals(expected.getName(), responseBody.getName());
    }


    @Test
    void testFindByIdNotFound() {
        bookRepository.save(new Book(1L, "Bible"));
        Long maxId = jdbcTemplate.queryForObject("select max(id) from books", Long.class);

        webTestClient.get()
                .uri("/book/" + maxId + 1)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testGetAll() {
        bookRepository.saveAll(List.of(
                new Book(1L, "Bible"),
                new Book(2L, "Das Kapital")
        ));

        List<Book> expected = bookRepository.findAll();

        List<JUnitBookResponse> responseBody = webTestClient.get()
                .uri("/book/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<JUnitBookResponse>>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.size(), responseBody.size());
        for (JUnitBookResponse bookResponse : responseBody) {
            boolean found = expected.stream()
                    .filter(it -> Objects.equals(it.getName(), bookResponse.getName()))
                    .anyMatch(it -> Objects.equals(it.getName(), bookResponse.getName()));
            Assertions.assertTrue(found);
        }
    }

    @Test
    void testDeleteById() {
        bookRepository.saveAll(List.of(
                new Book(1L, "Bible"),
                new Book(2L, "Das Kapital")));
        Long maxId = jdbcTemplate.queryForObject("select max(id) from books", Long.class);

        Integer rowsBeforeDeleting = jdbcTemplate.queryForObject("select count(1) from books", Integer.class);
        webTestClient.delete()
                .uri("/book/" + maxId)
                .exchange()
                .expectStatus().isOk();
        Integer rowsAfterDeleting = jdbcTemplate.queryForObject("select count(1) from books", Integer.class);

        Assertions.assertEquals(1, rowsBeforeDeleting - rowsAfterDeleting);
    }

    @Test
    @Disabled
    void testUpdate() {
        // TODO: Не реализовано в приложении
    }


}
