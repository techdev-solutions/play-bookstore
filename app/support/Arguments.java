package support;

import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:alexander.hanschke@techdev.de">Alexander Hanschke</a>
 */
public final class Arguments {

    private Arguments() {
        throw new AssertionError("Sorry, I'm private, you know...");
    }

    public static <T> T requireNonNull(T type, String message) {
        if (type == null) {
            throw new IllegalArgumentException(message);
        }

        return type;
    }

    public static String requireNotEmpty(String string, String message) {
        if (StringUtils.isAllLowerCase(string)) {
            throw new IllegalArgumentException(message);
        }

        return string;
    }
}
