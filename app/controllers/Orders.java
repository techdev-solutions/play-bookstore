package controllers;

import actions.CurrentAccount;
import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.Book;
import models.Order;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;

import static play.data.Form.form;

/**
 * @author <a href="mailto:alexander.hanschke@techdev.de">Alexander Hanschke</a>
 */
@With(CurrentAccount.class)
@DeferredDeadbolt
public class Orders extends Controller {

    private static final Form<Order> ORDER_FORM = form(Order.class);

    @SubjectPresent(deferred = true)
    public static Result add(Long id) {
        Book book = Book.find.byId(id);
        if (book == null) {
            flash("info", "We couldn't find the book you were looking for.");
            return redirect(routes.Books.all());
        }

        if (CurrentAccount.get().owns(book)) {
            flash("info", "You already own that book - just download it!");
            return redirect(routes.Books.show(id));
        }

        return ok(views.html.orders.add.render(ORDER_FORM, book));
    }

    @SubjectPresent(deferred = true)
    public static Result create(Long book) {
        if (CurrentAccount.get().owns(Book.find.ref(book))) {
            flash("info", "You already own that book - just download it!");
            return redirect(routes.Books.show(book));
        }
        
        Form<Order> form = ORDER_FORM.bindFromRequest();
        if (form.hasErrors()) {
            flash("error", "Something went wrong!");
            return ok(views.html.orders.add.render(form, Book.find.ref(book)));
        }

        Order order = form.get();
        order.book = Book.find.ref(book);
        order.buyer = CurrentAccount.get();
        order.total = order.book.price;
        order.save();

        flash("success", "Order successfully submitted!");
        return redirect(routes.Orders.all());
    }

    @SubjectPresent(deferred = true)
    public static Result all() {
        return ok(views.html.orders.all.render(Order.byBuyer(CurrentAccount.get())));
    }
}
