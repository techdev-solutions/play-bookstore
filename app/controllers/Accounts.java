package controllers;

import actions.CurrentAccount;
import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.Account;
import models.AccountRole;
import models.Credentials;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;

import static play.data.Form.form;

/**
 * @author <a href="mailto:alexander.hanschke@techdev.de">Alexander Hanschke</a>
 */
public class Accounts extends Controller {

    private static final Form<Account> ACCOUNT_FORM = form(Account.class);
    private static final Form<Credentials> CREDENTIALS_FORM = form(Credentials.class);

	public static Result login() {
		return ok(views.html.accounts.login.render(CREDENTIALS_FORM));
	}

	public static Result authenticate() {
		Form<Credentials> form = CREDENTIALS_FORM.bindFromRequest();
		if (form.hasErrors()) {
            flash("error", "Something went wrong!");
			return ok(views.html.accounts.login.render(form));
		}

		Account account = Account.authenticate(form.get());
        if (account == null) {
            flash("error", "Invalid credentials!");
            return ok(views.html.accounts.login.render(form));
        }

        session().put("email", account.email);

        String path = form().bindFromRequest().get("next");
        if (StringUtils.isBlank(path)) {
            path = routes.Books.all().url();
        }

        return redirect(path);
	}

	public static Result signup() {
		return ok(views.html.accounts.add.render(ACCOUNT_FORM));
	}

	public static Result create() {
		Form<Account> form = ACCOUNT_FORM.bindFromRequest();
		if (form.hasErrors()) {
            flash("error", "Something went wrong!");
			return ok(views.html.accounts.add.render(form));
		}

		Account account = form.get();
		String confirmation = form().bindFromRequest().get("confirmation");
		if (account.password.equals(confirmation)) {
            account.password = BCrypt.hashpw(account.password, BCrypt.gensalt());
            account.save();
            account.addRole(AccountRole.CUSTOMER);
			return redirect(routes.Accounts.login());
		}

        flash("error", "Passwords do not match!");
		return ok(views.html.accounts.add.render(form));
	}

    @With(CurrentAccount.class)
    @SubjectPresent
    public static Result logout() {
        session().clear();
        flash("info", "Come back soon!");
        return redirect(routes.Accounts.login());
    }

}