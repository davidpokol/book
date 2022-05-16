package hu.book.bookapp.controller;

import hu.book.bookapp.model.Borrowing;
import hu.book.bookapp.model.exception.NotFoundException;
import hu.book.bookapp.service.impl.BorrowingService;
import hu.book.bookapp.service.repository.BorrowingRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class for managing book borrowing features.
 */
@Controller
@RequestMapping("/books")
public class BorrowingController {

  private final BorrowingRepository borrowingRepository;

  public BorrowingController(BorrowingService borrowingService) {
    this.borrowingRepository = borrowingService;
  }

  @GetMapping("/borrowing")
  public String borrowingBookForm(final Model model) {
    model.addAttribute("borrowing", new Borrowing());

    return "books/borrowing";
  }

  @PostMapping("/borrowing")
  public String borrowingBook(final Model model, @ModelAttribute Borrowing borrowing) {

    if (borrowing.getId() == null) {
      model.addAttribute("borrowing", borrowingRepository.borrow(borrowing));
    }

    return "books/borrowing";
  }

  @GetMapping("/borrowed")
  public String getAllBorrowedBooks(Model model) {
    model.addAttribute("borrowedBooks", borrowingRepository.getAllBorrowedBooks());

    return "books/borrowed";
  }

  @GetMapping("/borrowed/remove/{id}")
  public String deleteBorrowing(final Model model, final @PathVariable("id") Long id) {

    try {
      borrowingRepository.deleteBorrow(id);
    } catch (NotFoundException e) {
      //ignored
    }
    model.addAttribute("borrowedBooks", borrowingRepository.getAllBorrowedBooks());

    return "redirect:/books/borrowed";
  }
}
