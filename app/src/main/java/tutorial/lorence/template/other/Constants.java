package tutorial.lorence.template.other;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public class Constants {
    public static final String EMPTY_STRING = "";
    public static final String EQUAL = " = ? ";
    public static final String LIKE = " like ? ";
    public static final String LIKE_SEP = "%";
    public static final String LEFT_BRACKET_SEP = " (";
    public static final String RIGHT_BRACKET_SEP = " )";
    public static final String IN = " in ";
    static int DOUBLE_CLICK_TIME_DELTA = 500;

    public final static int PERMISSION_CAMERA = 0;

    public final static String URL_SITE = "https://www.oxfordlearnersdictionaries.com/definition/english/";

    public final static String REGEX = "_1?q=";

    private Constants() {
    }

    public enum MVP {
        _JSOUP
    }

    static final String IMAGE_FOLDER = "Vogo";
}
