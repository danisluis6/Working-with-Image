package tutorial.lorence.template.other;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public class Utils {

    private static long sLastClickTime = 0;

    private Utils() {
    }

    public static boolean equalsIgnoreCase(String a, String b) {
        return a != null && b != null && a.length() == b.length() && a.equalsIgnoreCase(b);
    }

    public static boolean isDoubleClick() {
        long clickTime = System.currentTimeMillis();
        if (clickTime - sLastClickTime < Constants.DOUBLE_CLICK_TIME_DELTA) {
            sLastClickTime = clickTime;
            return true;
        }
        sLastClickTime = clickTime;
        return false;
    }

    public static boolean isInternetOn(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static String getPronouceByBre(List<String> spellingcontent) {
        Pattern pattern = Pattern.compile("//.*;+\\s{1}");
        Matcher matcher = pattern.matcher(spellingcontent.get(0));
        if (matcher.find()) {
            return matcher.group().substring(2, matcher.group().length() - 5);
        }
        return Constants.EMPTY_STRING;
    }

    public static String getPronouceByNAmE(List<String> spellingcontent) {
        Pattern pattern = Pattern.compile(";+\\s+.*");
        Matcher matcher = pattern.matcher(spellingcontent.get(0));
        if (matcher.find()) {
            return matcher.group().split("//")[1];
        }
        return Constants.EMPTY_STRING;
    }

    public static boolean checkPermissionCamera(Activity activity) {
        return ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }


    public static void settingPermissionCameraOnFragment(Fragment fragment) {
        fragment.requestPermissions(
                new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, Constants.PERMISSION_CAMERA
        );
    }

    private static File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("Camera", "Directory not created");
        }
        return file;
    }

    public static boolean deleteImageFolder(Activity activity) {
        return !Utils.checkPermissionReadExternalStorage(activity) && deleteRecursive(getAlbumStorageDir(Constants.IMAGE_FOLDER));
    }

    static boolean checkPermissionReadExternalStorage(Activity activity) {
        return ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
    }

    private static boolean deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                deleteRecursive(child);
            }
        }
        return fileOrDirectory.delete();
    }

}
