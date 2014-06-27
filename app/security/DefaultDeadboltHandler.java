package security;

import actions.CurrentAccount;
import be.objectify.deadbolt.core.models.Subject;
import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import controllers.routes;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

/**
 * @author <a href="mailto:alexander.hanschke@techdev.de">Alexander Hanschke</a>
 */
public class DefaultDeadboltHandler extends Results implements DeadboltHandler {

    @Override
    public Result beforeAuthCheck(Http.Context context) {
        return null;
    }

    @Override
    public Subject getSubject(Http.Context context) {
        return CurrentAccount.get();
    }

    @Override
    public Result onAuthFailure(Http.Context context, String s) {
        // insufficient privileges
        if (CurrentAccount.get() != null) {
            context.flash().put("error", "Sorry, you are not allowed to do this!");
            return redirect(routes.Books.all());
        }

        // no subject present
        context.flash().put("error", "Please login first!");
        return redirect(withDestination(context.request()));
    }

    @Override
    public DynamicResourceHandler getDynamicResourceHandler(Http.Context context) {
        return null;
    }

    private String withDestination(Http.Request request) {
        return routes.Accounts.login().absoluteURL(request) + "?next=" + request.path();
    }

}
