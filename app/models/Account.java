package models;

import be.objectify.deadbolt.core.models.Permission;
import be.objectify.deadbolt.core.models.Role;
import be.objectify.deadbolt.core.models.Subject;
import com.avaje.ebean.Ebean;
import org.mindrot.jbcrypt.BCrypt;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:alexander.hanschke@techdev.de">Alexander Hanschke</a>
 */
@Entity
public class Account extends BookstoreModel implements Subject {

    @Constraints.Required
    public String firstname;
    @Constraints.Required
    public String lastname;
	@Constraints.Required
    @Constraints.Email
	public String email;
    @Constraints.MinLength(8)
	@Constraints.Required
	public String password;

    @Override
    public String getIdentifier() {
        return email;
    }

    @Override
    public List<? extends Permission> getPermissions() {
        return Collections.emptyList();
    }

    @ManyToMany
    public List<AccountRole> roles = new ArrayList<>();

    @Override
    public List<? extends Role> getRoles() {
        return Collections.unmodifiableList(roles);
    }

    public void addRole(AccountRole role) {
        roles.add(role);
        Ebean.saveManyToManyAssociations(this, "roles");
    }

	public static final Finder<Long, Account> find = new Finder<>(Long.class, Account.class);
	
	public static boolean isSignedUp(String email) {
		return byEmail(email) != null;
	}

	public static Account authenticate(Credentials credentials) {
        Account account = byEmail(credentials.email);
		if (account != null) {
            if (BCrypt.checkpw(credentials.password, account.password)) {
                return account;
            }
		}

		return null;
	}

    public static Account byEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }

    @SuppressWarnings("unused")
	public String validate() {
		if (isSignedUp(email)) {
			return "Email already taken!";
		}

		return null;
	}

    public boolean canReview(Book book) {
        return owns(book) && !Review.hasReviewed(this, book);
    }

    public String getFullname() {
        return firstname + " " + lastname;
    }

    public boolean owns(Book book) {
        return Order.byBuyerAndBook(this, book) != null;
    }
}