package hu.book.bookapp.service.impl;

import java.util.List;

import hu.book.bookapp.model.Book;
import hu.book.bookapp.model.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class BookServiceTest {

  public static final Book BOOK_ONE = new Book(
    1L,
    "author1",
    "book1",
    200,
    "2012-10-06");

  public static final Book BOOK_TWO = new Book(
    2L,
    "author2",
    "book2",
    500,
    "2015-08-02");

  public static final Book BOOK_THREE_WITH_NO_ID = new Book(
    null,
    "author3",
    "book3",
    100,
    "2020-02-20");

  public static final Book BOOK_THREE_WITH_ID = new Book(
    3L,
    "author3",
    "book3",
    100,
    "2020-02-20");

  public static final String BOOK_THREE_AUTHOR = BOOK_THREE_WITH_ID.getAuthor();
  public static final String BOOK_THREE_TITLE = BOOK_THREE_WITH_ID.getTitle();

  public static final Book BOOK_FOUR = new Book(
    4L,
    BOOK_THREE_AUTHOR,
    "book4",
    400,
    "2017-03-24");

  public static final Book BOOK_FIVE = new Book(
    5L,
    "author5",
    BOOK_THREE_TITLE,
    550,
    "2022-05-02");

  public static final List<Book> BOOKS = List.of(BOOK_ONE, BOOK_TWO);
  public static final List<Book> BOOKS_WITH_ONLY_BOOK_TWO = List.of(BOOK_TWO);

  public static final Long BOOK_ONE_ID = BOOK_ONE.getId();
  public static final Book FIRST_ELEMENT_OF_BOOKS = BOOKS.get(0);

  public static final Long NON_EXISTENT_ID = -1L;

  private BookService underTest;

  @BeforeEach
  void setUp() {
    underTest = new BookService(BOOKS);
  }

  @Test
  public void getAllAvailableBooksShouldReturnAllBooks() {

    //Given - When
    List<Book> actual = underTest.getAllAvailableBooks();

    //Then
    Assertions.assertEquals(BOOKS, actual);
  }

  @Test
  public void getBookByIdShouldReturnWithTheProperBook() {

    //Given - When
    Book actual = underTest.getBookById(BOOK_ONE_ID);

    //Then
    Assertions.assertEquals(BOOK_ONE, actual);
  }

  @Test
  public void getBookByIdShouldThrowNotFoundExceptionWhenGivenIdNotAssignedToAnyBook() {

    //When - Then
    Assertions.assertThrows(NotFoundException.class, () -> underTest.getBookById(NON_EXISTENT_ID));
  }

  @Test
  public void addBookShouldReturnWithTheTheAddedBook() {

    //Given
    Book expected = BOOK_THREE_WITH_ID;

    //When
    Book actual = underTest.addBook(BOOK_THREE_WITH_NO_ID);

    //Then
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void addBookShouldAddBookEvenThereIsABookWithTheSameAuthorInTheList() {

    //Given
    List<Book> expected = List.of(BOOK_ONE, BOOK_TWO, BOOK_FOUR);

    //When
    underTest.addBook(BOOK_FOUR);
    List<Book> actual = underTest.getAllAvailableBooks();

    //Then
    Assertions.assertEquals(expected,actual);
  }

  @Test
  public void addBookShouldAddBookEvenThereIsABookWithTheSameTitleInTheList() {

    //Given
    List<Book> expected = List.of(BOOK_ONE, BOOK_TWO, BOOK_FIVE);

    //When
    underTest.addBook(BOOK_FIVE);
    List<Book> actual = underTest.getAllAvailableBooks();

    //Then
    Assertions.assertEquals(expected,actual);
  }

  @Test
  public void addBookShouldNotAddBookIfItIsAlreadyExistsInTheList() {

    //Given
    List<Book> expected = BOOKS;

    //When
    underTest.addBook(BOOK_ONE);
    List<Book> actual = underTest.getAllAvailableBooks();

    //Then
    Assertions.assertEquals(expected,actual);
  }

  @Test
  public void replaceBookShouldReturnWithTheGivenBook() {

    //Given - When
    Book actual = underTest.replaceBook(BOOK_THREE_WITH_ID);

    //Then
    Assertions.assertEquals(BOOK_THREE_WITH_ID, actual);
  }

  @Test
  public void replaceBookShouldAddBookToTheBookListAtTheProperIndex() {

    //Given
    underTest = new BookService(BOOKS_WITH_ONLY_BOOK_TWO);

    //When
    Book actual = underTest.replaceBook(BOOK_ONE);

    //Then
    Assertions.assertEquals(BOOK_ONE, FIRST_ELEMENT_OF_BOOKS);
  }

  @Test
  public void removeByIdShouldRemoveTheGivenBookFromTheList() {

    //Given
    List<Book> expected = BOOKS_WITH_ONLY_BOOK_TWO;

    //When
    underTest.removeById(BOOK_ONE_ID);
    List<Book> actual = underTest.getAllAvailableBooks();

    //Then
    Assertions.assertEquals(expected,actual);
  }
}
