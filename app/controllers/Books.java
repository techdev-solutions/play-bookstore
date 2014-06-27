package controllers;

import actions.CurrentAccount;
import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.Book;
import models.Review;
import models.S3Image;
import org.apache.commons.lang3.StringUtils;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import support.Storage;

import java.io.IOException;

import static play.data.Form.form;

/**
 * @author <a href="mailto:alexander.hanschke@techdev.de">Alexander Hanschke</a>
 */
@With(CurrentAccount.class)
@DeferredDeadbolt
public class Books extends Controller {

    private static final Form<Book> BOOK_FORM = form(Book.class);

    public static Result all() {
        return ok(views.html.books.all.render(Book.find.all()));
    }

    public static Result show(Long id) {
        Book book = Book.find.byId(id);
        if (book == null) {
            flash("info", "We couldn't find the book you were looking for.");
            return redirect(routes.Books.all());
        }

        return ok(views.html.books.show.render(book));
    }

    @Restrict(value = @Group("admin"), deferred = true)
    public static Result edit(Long id) {
        Book book = Book.find.byId(id);
        if (book == null) {
            flash("info", "We couldn't find the book you were looking for.");
            return redirect(routes.Books.all());
        }

        return ok(views.html.books.edit.render(id, BOOK_FORM.fill(book)));
    }

    @Restrict(value = @Group("admin"), deferred = true)
    public static Result update(Long id) {
        Form<Book> form = BOOK_FORM.bindFromRequest();
        if (form.hasErrors()) {
            flash("error", "Something went wrong.");
            return ok(views.html.books.edit.render(id, form));
        }

        Book book = form.get();
        book.update(id);

        flash("success", "Book successfully edited!");
        return redirect(routes.Books.show(book.id));
    }

    @Restrict(value = @Group("admin"), deferred = true)
    public static Result create() {
        Form<Book> form = BOOK_FORM.bindFromRequest();
        if (form.hasErrors()) {
            flash("error", "Something went wrong!");
            return ok(views.html.books.add.render(form));
        }

        Book book = form.get();

        Http.MultipartFormData.FilePart image = request().body().asMultipartFormData().getFile("image");
        if (image.getFile().length() > 0) {
            try {
                book.image = new S3Image(image.getFile(), Storage.bucket);
                Storage.store(book.image);
            } catch (IOException cause) {
                return internalServerError("Could not store image!");
            }
        }

        book.save();
        flash("success", "'" + book.title + "' successfully added!");
        return redirect(routes.Books.show(book.id));
    }

    @Restrict(value = @Group("admin"), deferred = true)
    public static Result add() {
        return ok(views.html.books.add.render(BOOK_FORM));
    }

    @SubjectPresent(deferred = true)
    public static Result review(Long id) {
        String text = Form.form().bindFromRequest().get("review");
        new Review(CurrentAccount.get(), Book.find.ref(id), text).save();
        flash("success", "Thank you :)");
        return redirect(routes.Books.show(id));
    }

}
