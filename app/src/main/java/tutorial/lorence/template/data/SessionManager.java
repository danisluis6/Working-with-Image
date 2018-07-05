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

    private static final String L1 = "l1";
    private static final String MINIMUM_VERSION = "minimum version";
    private static final String LATEST_VERSION = "latest version";
    private static final String DATE_UPGRATE_APP = "date upgrade app";
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

    public String getL1() {
        return pref.getString(L1, Constants.EMPTY_STRING);
    }

    public void setL1(String l1) {
        editor.putString(L1, l1);
        editor.apply();
    }

    public String getLatestVersion() {
        return pref.getString(LATEST_VERSION, Constants.EMPTY_STRING);
    }

    public void setLatestVersion(String latestVersion) {
        editor.putString(LATEST_VERSION, latestVersion);
        editor.apply();
    }

    public String getMinimumVersion() {
        return pref.getString(MINIMUM_VERSION, Constants.EMPTY_STRING);
    }

    public void setMinimumVersion(String minimumVersion) {
        editor.putString(MINIMUM_VERSION, minimumVersion);
        editor.apply();
    }

    public long getDateUpgradeApp() {
        return pref.getLong(DATE_UPGRATE_APP, 0);
    }

    public void setDateUpgradeApp(long time) {
        editor.putLong(DATE_UPGRATE_APP, time);
        editor.apply();
    }

    public String getBaseUrl() {
        return pref.getString(BASE_URL, Constants.EMPTY_STRING);
    }

    public void setBaseUrl(String baseUrl) {
        editor.putString(BASE_URL, baseUrl);
        editor.apply();
    }

    public void clear() {
        editor.remove(L1);
        editor.remove(LATEST_VERSION);
        editor.remove(MINIMUM_VERSION);
        editor.apply();
    }
}
