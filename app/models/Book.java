package models;

import play.data.validation.Constraints;
import support.Storage;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:alexander.hanschke@techdev.de">Alexander Hanschke</a>
 */
@Entity
public class Book extends BookstoreModel {

    @Constraints.Required
    public String title;

    @Lob
    @Column(columnDefinition = "text")
    @Constraints.Required
    public String description;

    @Column(scale = 2)
    @Constraints.Required
    public BigDecimal price;
    @OneToOne(cascade = CascadeType.PERSIST)
    public S3Image image;

    public static final Finder<Long, Book> find = new Finder<>(Long.class, Book.class);

    public static int count() {
        return find.findRowCount();
    }

    public static Set<Book> byTitle(String title) {
        return find.where().eq("title", title).findSet();
    }

    @Override
    public void delete() {
        Storage.remove(image);
        super.delete();
    }

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "book_id")
    public Set<Review> reviews = new HashSet<>();

    public Set<Review> getReviews() {
        return Collections.unmodifiableSet(reviews);
    }

    public boolean hasReviews() {
        return !reviews.isEmpty();
    }

    public boolean hasImage() {
        return image != null;
    }
}
