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
    public final static int PERMISSION_GALLERY = 1;
    public final static int REQUEST_CAMERA = 3;
    public final static int REQUEST_GALLERY = 4;

    private Constants() {
    }

    public enum MVP {
        _JSOUP, _JSON, _SQL
    }

    static final String IMAGE_FOLDER = "Vogo";
}
