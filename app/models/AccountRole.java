package models;

import be.objectify.deadbolt.core.models.Role;

import javax.persistence.Entity;

/**
 * @author <a href="mailto:alexander.hanschke@techdev.de">Alexander Hanschke</a>
 */
@Entity
public class AccountRole extends BookstoreModel implements Role {

    public static final AccountRole ADMIN    = new AccountRole("admin");
    public static final AccountRole CUSTOMER = new AccountRole("customer");

    public final String name;

    public AccountRole(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public static Finder<Long, AccountRole> find = new Finder<>(Long.class, AccountRole.class);

    public static int count() {
        return find.findRowCount();
    }

}
