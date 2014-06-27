package models;

import support.Arguments;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

/**
 * @author <a href="mailto:alexander.hanschke@techdev.de">Alexander Hanschke</a>
 */
@Entity
public class Review extends BookstoreModel {

    @ManyToOne
    public Account author;

    @ManyToOne
    public Book book;

    @Lob
    @Column(columnDefinition = "text")
    public String text;

    public Review(Account author, Book book, String text) {
        this.author = Arguments.requireNonNull(author, "Author must not be null!");
        this.book   = Arguments.requireNonNull(book,   "Book must not be null!");
        this.text = text;
    }

    public static final Finder<Long, Review> find = new Finder<>(Long.class, Review.class);

    public static Review byAuthorAndBook(Account author, Book book) {
        return find.where().eq("author", author).eq("book", book).findUnique();
    }

    public static boolean hasReviewed(Account author, Book book) {
        return byAuthorAndBook(author, book) != null;
    }
}
