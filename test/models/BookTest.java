package models;

import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;
import play.test.WithApplication;

import java.util.Date;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;

/**
 * @author <a href="mailto:alexander.hanschke@techdev.de>Alexander Hanschke</a>
 */
public class BookTest extends WithApplication {

    @Before
    public void setup() {
        start(fakeApplication(inMemoryDatabase()));
    }

    @Test
    public void shouldSaveBook() {
        Assertions.assertThat(Book.count()).isZero();

        Book book = new Book();
        book.title = "The Book";
        book.description = "a book about everything";
        book.published = new Date();
        book.save();

        Assertions.assertThat(Book.count()).isEqualTo(1);
    }

    @Test
    public void shouldFindBooksByTitle() {
        Book book = new Book();
        book.title = "The Book";
        book.description = "a book about everything";
        book.published = new Date();
        book.save();

        Assertions.assertThat(Book.byTitle("The Book")).hasSize(1);
    }
}
