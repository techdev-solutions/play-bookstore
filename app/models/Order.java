package models;

import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:alexander.hanschke@techdev.de">Alexander Hanschke</a>
 */
@Entity
@Table(name = "orders")
public class Order extends BookstoreModel {

    @Constraints.Required
    public Long cardnumber;
    @Constraints.Required
    public String owner;
    @Constraints.Required
    public Date validity;
    @Constraints.Required
    public Long checkdigit;
    @Column(scale = 2)
    public BigDecimal total;
    @ManyToOne
    public Book book;
    @ManyToOne
    public Account buyer;

    public List<ValidationError> validate() {
        final List<ValidationError> errors = new ArrayList<>();
        if (validity.before(new Date())) {
            errors.add(new ValidationError("validity", "Your card has expired!"));
        }
        if (cardnumber.toString().length() != 16) {
            errors.add(new ValidationError("cardnumber", "Invalid card number!"));
        }

        return errors.isEmpty() ? null : errors;
    }

    public static final Finder<Long, Order> find = new Finder<>(Long.class, Order.class);

    public static List<Order> byBuyer(Account buyer) {
        return find.where().eq("buyer", buyer).findList();
    }

    public static Order byBuyerAndBook(Account buyer, Book book) {
        return find.where().eq("buyer", buyer).eq("book", book).findUnique();
    }

}

