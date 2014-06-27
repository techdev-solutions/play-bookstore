package models;

import play.data.validation.Constraints;

/**
 * @author <a href="mailto:alexander.hanschke@techdev.de">Alexander Hanschke</a>
 */
public class Credentials {

    @Constraints.Email
    @Constraints.Required
	public String email;
    @Constraints.Required
	public String password;

}