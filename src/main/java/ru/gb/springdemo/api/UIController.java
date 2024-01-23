package ru.gb.springdemo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.service.BookService;
import ru.gb.springdemo.service.IssueService;
import ru.gb.springdemo.service.ReaderService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller()
public class UIController {

    @Autowired
    private BookService bookService;
    @Autowired
    private ReaderService readerService;
    @Autowired
    private IssueService issueService;

    @GetMapping("/ui")
    public String uiHome() {
        return "home";
    }

    @GetMapping("/ui/books")
    public String books(Model model) {
        List<Book> bookList = bookService.allBooks();
        model.addAttribute("books", bookList);
        return "books";
    }

    @GetMapping("/ui/readers")
    public String readers(Model model) {
        List<Reader> readerList = readerService.allReaders();
        model.addAttribute("readers", readerList);
        return "readers";
    }

    @GetMapping("/ui/issues")
    public String issues(Model model) {
        List<Issue> issueList = issueService.AllIssues();
        List<Reader> readerList = readerService.allReaders();
        List<Book> bookList = bookService.allBooks();
        model.addAttribute("issues", issueList);
        model.addAttribute("readers", readerList);
        model.addAttribute("books", bookList);
        return "issues";
    }

    @GetMapping("/ui/reader/{id}")
    public String readerBooks(@PathVariable long id, Model model) {
        List<Issue> issues;
        try {
            issues = issueService.getAllIssuesForReader(id);
        } catch (NoSuchElementException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }

        Reader reader;
        try {
            reader = readerService.readInfo(id);
        } catch (NoSuchElementException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }

        List<Book> books = new ArrayList<>();
        try {
            for (Issue issue : issues) {
                books.add(bookService.readInfo(issue.getBookId()));
            }
        } catch (NoSuchElementException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }

        model.addAttribute("books", books);
        model.addAttribute("reader", reader);
        return "reader_books";
    }

}
