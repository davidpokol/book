package hu.book.bookapp.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hu.book.bookapp.model.Book;
import hu.book.bookapp.model.Borrowing;
import hu.book.bookapp.service.repository.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing book borrowing operations.
 */
@Service
public class BorrowingService implements BorrowingRepository {

  private final BookService bookService;
  private List<Borrowing> borrowingList = new ArrayList<>();

  @Autowired
  public BorrowingService(BookService bookService) {
    this.bookService = bookService;
  }

  @Override
  public List<Borrowing> getAllBorrowedBooks() {
    return Collections.unmodifiableList(borrowingList);
  }

  @Override
  public Borrowing borrow(Borrowing borrowing) {
    for (Book b : bookService.getAllAvailableBooks()) {

      if (b.getAuthor().equals(borrowing.getAuthor())
              && b.getTitle().equals(borrowing.getTitle())) {

        borrowing.setBorrowingDate(dateNow());
        borrowing.setId(b.getId());
        borrowing.setTotalPages(b.getTotalPages());
        borrowing.setPublishedDate(b.getPublishedDate());
        borrowingList.add(getIndex(b.getId()), borrowing);

        bookService.removeById(b.getId());
        break;
      }
    }

    return borrowing;
  }

  @Override
  public void deleteBorrow(Long id) {

    for (int i = 0; i < borrowingList.size(); i++) {
      if (borrowingList.get(i).getId().equals(id)) {
        Borrowing returnBook = borrowingList.get(i);
        bookService.replaceBook(new Book(
            id,
            returnBook.getAuthor(),
            returnBook.getTitle(),
            returnBook.getTotalPages(),
            returnBook.getPublishedDate()));
        borrowingList.remove(i);
        break;
      }
    }
  }

  private String dateNow() {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    return formatter.format(new Date(System.currentTimeMillis()));
  }

  private int getIndex(Long id) {

    int result = borrowingList.size();
    for (int i = 0; i < borrowingList.size(); i++) {

      if (borrowingList.get(i).getId() > id) {
        result = i;
        break;
      }
    }
    return result;
  }
}
