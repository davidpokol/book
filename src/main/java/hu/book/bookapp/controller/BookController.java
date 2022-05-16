package hu.book.bookapp.controller;

import hu.book.bookapp.model.Book;
import hu.book.bookapp.service.repository.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class for managing book adding and queries.
 */
@Controller
@RequestMapping("/books")
public class BookController {

  private final BookRepository bookRepository;

  public BookController(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @GetMapping("/available")
  public String getAllAvailableBooks(final Model model) {
    model.addAttribute("books", bookRepository.getAllAvailableBooks());
    return "books/available";
  }

  @GetMapping("/add")
  public String addBookForm(final Model model) {
    model.addAttribute("book", new Book());
    return "books/add";
  }

  @PostMapping("/add")
  public String addBook(@ModelAttribute final Book book, final Model model) {
    model.addAttribute("book", bookRepository.addBook(book));
    return "books/add";
  }
}
