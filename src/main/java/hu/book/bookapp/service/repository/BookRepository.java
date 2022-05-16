package hu.book.bookapp.service.repository;

import java.util.List;

import hu.book.bookapp.model.Book;

/**
 * Interface which holds book manager methods.
 */
public interface BookRepository  {

  List<Book> getAllAvailableBooks();

  Book getBookById(Long id);

  Book addBook(Book book);

}
