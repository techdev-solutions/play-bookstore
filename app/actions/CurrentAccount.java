package actions;

import models.Account;
import org.apache.commons.lang3.StringUtils;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

/**
 * @author <a href="mailto:alexander.hanschke@techdev.de">Alexander Hanschke</a>
 */
public class CurrentAccount extends Action.Simple {

    @Override
    public Result call(Http.Context context) throws Throwable {
        String email = context.session().get("email");
        if (StringUtils.isNotBlank(email)) {
            Account account = Account.byEmail(email);
            if (account != null) {
                context.args.put("account", account);
            } else {
                context.session().clean();
            }
        }

        return delegate.call(context);
    }

    public static Account get() {
        return (Account) Http.Context.current().args.get("account");
    }
}
