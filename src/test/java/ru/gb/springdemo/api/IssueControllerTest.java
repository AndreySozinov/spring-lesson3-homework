package ru.gb.springdemo.api;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gb.springdemo.JUnitSpringBootBase;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.repository.BookRepository;
import ru.gb.springdemo.repository.IssueRepository;
import ru.gb.springdemo.repository.ReaderRepository;

import java.time.LocalDateTime;

public class IssueControllerTest extends JUnitSpringBootBase{


    @Autowired
    WebTestClient webTestClient;

    @Autowired
    IssueRepository issueRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    ReaderRepository readerRepository;


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Data
    static class JUnitIssueResponse {
        private Long id;
        private Long bookId;
        private Long readerId;
        private LocalDateTime issued_at;
        private LocalDateTime returned_at;
    }

    @BeforeEach
    void clearingDataBase() {
        issueRepository.deleteAll();
        bookRepository.deleteAll();
        readerRepository.deleteAll();
        bookRepository.save(new Book(1L, "Bible"));
        readerRepository.save(new Reader(1L, "John Smith"));
    }

    @Test
    void testCreate() {
        IssueControllerTest.JUnitIssueResponse request = new IssueControllerTest.JUnitIssueResponse();
        request.setId(1L);
        request.setBookId(1L);
        request.setReaderId(1L);
        request.setIssued_at(LocalDateTime.now());


        IssueControllerTest.JUnitIssueResponse responseBody = webTestClient.post()
                .uri("/issue")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(IssueControllerTest.JUnitIssueResponse.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertNotNull(responseBody.getId());

        Assertions.assertTrue(issueRepository.findById(request.getId()).isPresent());
    }

    @Test
    void testFindByIdSuccess() {
        Issue expected = issueRepository.save(new Issue(1L, 1L, 1L, LocalDateTime.now()));

        IssueControllerTest.JUnitIssueResponse responseBody = webTestClient.get()
                .uri("/issue/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(IssueControllerTest.JUnitIssueResponse.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.getId(), responseBody.getId());
        Assertions.assertEquals(expected.getBookId(), responseBody.getBookId());
        Assertions.assertEquals(expected.getReaderId(), responseBody.getReaderId());
        Assertions.assertEquals(expected.getIssued_at().toLocalDate(), responseBody.getIssued_at().toLocalDate());
    }


    @Test
    void testFindByIdNotFound() {
        issueRepository.save(new Issue(1L, 1L, 1L, LocalDateTime.now()));
        Long maxId = jdbcTemplate.queryForObject("select max(id) from readers", Long.class);

        webTestClient.get()
                .uri("/issue/" + maxId + 1)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testReturnBook() {
        Issue expected = issueRepository.save(new Issue(1L, 1L, 1L, LocalDateTime.now()));

        IssueControllerTest.JUnitIssueResponse responseBody = webTestClient.put()
                .uri("/issue/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(IssueControllerTest.JUnitIssueResponse.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.getId(), responseBody.getId());
        Assertions.assertEquals(expected.getBookId(), responseBody.getBookId());
        Assertions.assertEquals(expected.getReaderId(), responseBody.getReaderId());
        Assertions.assertEquals(expected.getIssued_at().toLocalDate(), responseBody.getIssued_at().toLocalDate());
        Assertions.assertNotNull(responseBody.getReturned_at());
    }

}
