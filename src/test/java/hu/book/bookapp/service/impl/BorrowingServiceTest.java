package hu.book.bookapp.service.impl;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hu.book.bookapp.model.Borrowing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BorrowingServiceTest {

	BorrowingService underTest;
	BookService bookService;
	public static final String DATE_NOW = new SimpleDateFormat("yyyy-MM-dd")
		.format(new Date(System.currentTimeMillis()));

	public static final Borrowing RAW_BORROWING_ONE = new Borrowing(
		"borrower",
		null,
		null,
		"author1",
		"book1",
		0,
		null);

	public static final Borrowing BORROWED_BOOK_ONE = new Borrowing(
		"borrower",
		DATE_NOW,
		1L,
		"author1",
		"book1",
		200,
		"2012-10-06");

	public static final Borrowing RAW_BORROWING_TWO = new Borrowing(
		"borrower",
		null,
		null,
		"author2",
		"book2",
		0,
		null);

	public static final Borrowing BORROWED_BOOK_TWO = new Borrowing(
		"borrower",
		DATE_NOW,
		2L,
		"author2",
		"book2",
		500,
		"2015-08-02");

	public static final Borrowing INVALID_RAW_BORROWING_BOOK = new Borrowing(
		"borrower",
		null,
		null,
		"author2",
		"book1",
		0,
		null);

	public static final Long NON_EXISTENT_ID = -1L;
	public static final Long BORROWED_BOOK_ID = BORROWED_BOOK_ONE.getId();

	@BeforeEach
	void setUp() {
		bookService = new BookService();
		underTest = new BorrowingService(bookService);
	}

	@Test
	void getAllBorrowedBooksShouldReturnEmptyList() {

		//Given
		List<Borrowing> expected = Collections.emptyList();

		//When
		List<Borrowing> actual = underTest.getAllBorrowedBooks();

		//Then
		Assertions.assertEquals(expected, actual);
	}

	@Test
	void borrowShouldReturnWithTheCompleteBorrowingObject() {

		//Given
		Borrowing expected = BORROWED_BOOK_ONE;

		//When
		Borrowing actual = underTest.borrow(RAW_BORROWING_ONE);

		//Then
		Assertions.assertEquals(expected, actual);
	}
	@Test
	void borrowShouldReturnWithTheGivenBorrowingObjectToTheBorrowingListInCaseOfNotMatchingTheTitles() {

		//Given
		Borrowing expected = INVALID_RAW_BORROWING_BOOK;

		//When
		Borrowing actual = underTest.borrow(INVALID_RAW_BORROWING_BOOK);

		//Then
		Assertions.assertEquals(expected, actual);
	}

	@Test
	void borrowShouldAddTheGivenBorrowingObjectToTheBorrowingListAtTheProperIndex() {

		//Given
		List<Borrowing> expected = List.of(BORROWED_BOOK_ONE, BORROWED_BOOK_TWO);

		//When
		underTest.borrow(BORROWED_BOOK_TWO);
		underTest.borrow(BORROWED_BOOK_ONE);

		//Then
		Assertions.assertEquals(expected, underTest.getAllBorrowedBooks());
	}

	@Test
	void deleteBorrowShouldRemoveTheProperBorrowingObjectDeterminedById() {

		//Given
		underTest.borrow(RAW_BORROWING_ONE);
		List<Borrowing> expected = Collections.emptyList();

		//When
		underTest.deleteBorrow(BORROWED_BOOK_ID);

		//Then
		Assertions.assertEquals(expected, underTest.getAllBorrowedBooks());
	}

	@Test
	void deleteBorrowShouldNotChangeBorrowingListInCaseOfBadId() {

		//Given
		underTest.borrow(RAW_BORROWING_ONE);
		List<Borrowing> expected = List.of(BORROWED_BOOK_ONE);

		//When
		underTest.deleteBorrow(NON_EXISTENT_ID);

		//Then
		Assertions.assertEquals(expected, underTest.getAllBorrowedBooks());
	}
}
