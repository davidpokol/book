package hu.book.bookapp.service.repository;

import java.util.List;
import hu.book.bookapp.model.Borrowing;

/**
 * Interface which holds book borrowing manager methods.
 */
public interface BorrowingRepository {

  List<Borrowing> getAllBorrowedBooks();

  Borrowing borrow(Borrowing borrowing);

  void deleteBorrow(Long id);
}
