package tutorial.lorence.template.data;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

import tutorial.lorence.template.other.Constants;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public class SessionManager {

    private static final String BASE_URL = "baseUrl";

    private static final String PREF_NAME = "ezFaxing_sharedPref";
    private static SessionManager instance;
    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;

    @Inject
    public SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.apply();
    }

    public static SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context);
        }
        return instance;
    }

    public String getBaseUrl() {
        return pref.getString(BASE_URL, Constants.EMPTY_STRING);
    }

    public void setBaseUrl(String baseUrl) {
        editor.putString(BASE_URL, baseUrl);
        editor.apply();
    }

    public void clear() {
        editor.remove(BASE_URL);
        editor.apply();
    }
}
