package hu.book.bookapp.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Value object for Borrowers.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Borrowing extends Book {

  String borrower;
  String borrowingDate;

  public Borrowing(String borrower, String borrowingDate, Long id,
                   String author, String title, int totalPages, String publishedDate) {
    super(id,
        author,
        title,
        totalPages,
        publishedDate);

    this.borrower = borrower;
    this.borrowingDate = borrowingDate;
  }
}

