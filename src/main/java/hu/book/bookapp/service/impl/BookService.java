package hu.book.bookapp.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hu.book.bookapp.model.Book;
import hu.book.bookapp.model.exception.NotFoundException;
import hu.book.bookapp.service.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing book operations.
 */
@Service
public class BookService implements BookRepository {

  private final List<Book> dataBase = new ArrayList<>();
  private int numberOfBorrowedBooks = 0;

  @Autowired
  public BookService() {
    dataBase.add(new Book(1L, "author1", "book1", 200, "2012-10-06"));
    dataBase.add(new Book(2L, "author2", "book2", 500, "2015-08-02"));
    dataBase.add(new Book(3L, "author3", "book3", 100, "2020-02-20"));
  }

  public BookService(final List<Book> books) {
    dataBase.addAll(books);
  }

  @Override
  public List<Book> getAllAvailableBooks() {
    return Collections.unmodifiableList(dataBase);
  }

  @Override
  public Book getBookById(Long id) {
    return dataBase.stream().filter(book -> book.getId().equals(id))
          .findFirst()
          .orElseThrow(NotFoundException::new);
  }

  @Override
  public Book addBook(final Book book) {

    boolean isNewBook = true;
    for (Book b : dataBase) {
      if (b.getAuthor().equals(book.getAuthor())
          && b.getTitle().equals(book.getTitle())) {
        isNewBook = false;
      }
    }

    if (isNewBook) {
      book.setId(getNextId());
      dataBase.add(book);
    }
    return book;
  }

  public Book replaceBook(final Book book) {
    dataBase.add(getReplaceIndex(book.getId()), book);
    numberOfBorrowedBooks--;
    return book;
  }

  public void removeById(Long id) {
    for (int i = 0; i < dataBase.size(); i++) {
      if (dataBase.get(i).getId().equals(id)) {
        dataBase.remove(i);
        numberOfBorrowedBooks++;
        break;
      }
    }
  }

  private long getNextId() {
    return dataBase.size() + numberOfBorrowedBooks + 1L;
  }

  private int getReplaceIndex(Long id) {
    int result = dataBase.size();

    for (int i = 0; i < dataBase.size(); i++) {
      if (dataBase.get(i).getId() > id) {
        result = i;
        break;
      }
    }
    return result;
  }
}