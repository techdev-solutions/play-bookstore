import models.Account;
import models.AccountRole;
import play.Application;
import play.GlobalSettings;
import support.Storage;

/**
 * @author <a href="mailto:alexander.hanschke@techdev.de">Alexander Hanschke</a>
 */
public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {
        Storage.initialize(app.configuration());
        if (firstRun()) {
            seed();
        }
        super.onStart(app);
    }

    private boolean firstRun() {
        return AccountRole.count() == 0;
    }

    private void seed() {
        createRoles();
        createAccounts();
    }

    private void createRoles() {
        AccountRole.ADMIN.save();
        AccountRole.CUSTOMER.save();
    }

    private void createAccounts() {
        Account admin = new Account();
        admin.password = "$2a$10$tylfSdJOR3Xi0/ox.M1aJe4NyEbxqlLfXnk3wvtblgWa13ahDcRGK";
        admin.firstname = "John";
        admin.lastname = "Doe";
        admin.email = "admin@bookstore.play";
        admin.save();

        admin.addRole(AccountRole.ADMIN);
        admin.addRole(AccountRole.CUSTOMER);

        Account customer = new Account();
        customer.password = "$2a$10$tylfSdJOR3Xi0/ox.M1aJe4NyEbxqlLfXnk3wvtblgWa13ahDcRGK";
        customer.firstname = "Jane";
        customer.lastname = "Doe";
        customer.email = "customer@bookstore.play";
        customer.save();

        customer.addRole(AccountRole.CUSTOMER);
    }

}
