package tutorial.lorence.template.other;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tutorial.lorence.template.data.storage.database.entities.Schedule;
import tutorial.lorence.template.view.activities.home.fragment.schedule.FragmentSchedule;

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

    public static List<Schedule> convertStringToObject(List<String> list, List<String> paths) {
        int temp = 0;
        for (int index = 0; index < list.size(); index++) {
            temp++;
            if (list.get(index).equals("Ngày Đội Giờ Đội T.Tiếp")) {
                break;
            }
        }
        List<Schedule> tempsSchedules = new ArrayList<>();
        List<String> flags = convertStringToFlag(paths);
        for (int index = temp; index < 52; index++) {
            String mydata = list.get(index);
            String _player = "", _player_path = "", _enemy = "", _enemy_path = "", _time = "", _date = "";

            Matcher player = Pattern.compile("\\d{1}\\s{1}.*\\s+\\d{1}").matcher(mydata);
            if (player.find()) {
                _player = player.group().substring(1, player.group().length() - 1);
                _player_path = getPathOfFlag(_player.trim(), flags);
            }

            Matcher enemy = Pattern.compile("\\d{2}\\:\\d{2}\\s{1}.*").matcher(mydata);
            if (enemy.find()) {
                _enemy = enemy.group().substring(5, enemy.group().length());
                _enemy_path = getPathOfFlag(_enemy.trim(), flags);
            }

            Matcher date = Pattern.compile("\\d{2}\\/\\d{2}\\s+").matcher(mydata);
            if (date.find()) {
                _date = date.group();
            }

            Matcher time = Pattern.compile("\\s+\\d{2}\\:\\d{2}\\s+").matcher(mydata);
            if (time.find()) {
                _time = time.group();
            }
            tempsSchedules.add(new Schedule(_player, _player_path, _enemy, _enemy_path, _time, _date));
        }
        return tempsSchedules;
    }

    private static String getPathOfFlag(String player, List<String> paths) {
        switch (player) {
            case "Nga":
                return paths.get(0);
            case "Ả Rập Xê-út":
                return paths.get(1);
            case "ẢRậpXê-út":
                return paths.get(1);
            case "Ai Cập":
                return paths.get(2);
            case "AiCập":
                return paths.get(2);
            case "Uruguay":
                return paths.get(3);
            case "Ma rốc":
                return paths.get(4);
            case "Marốc":
                return paths.get(4);
            case "Iran":
                return paths.get(5);
            case "Bồ Đào Nha":
                return paths.get(6);
            case "BồĐàoNha":
                return paths.get(6);
            case "Tây Ban Nha":
                return paths.get(7);
            case "TâyBanNha":
                return paths.get(7);
            case "Pháp":
                return paths.get(8);
            case "Australia":
                return paths.get(9);
            case "Argentina":
                return paths.get(10);
            case "Ai-xơ-len":
                return paths.get(11);
            case "Peru":
                return paths.get(12);
            case "Đan Mạch":
                return paths.get(13);
            case "ĐanMạch":
                return paths.get(13);
            case "Croatia":
                return paths.get(14);
            case "Costa Rica":
                return paths.get(15);
            case "CostaRica":
                return paths.get(15);
            case "Nigeria":
                return paths.get(16);
            case "Serbia":
                return paths.get(17);
            case "Đức":
                return paths.get(18);
            case "Mexico":
                return paths.get(19);
            case "Brazil":
                return paths.get(20);
            case "Thụy Sĩ":
                return paths.get(21);
            case "ThụySĩ":
                return paths.get(21);
            case "Thụy Điển":
                return paths.get(22);
            case "ThụyĐiển":
                return paths.get(22);
            case "Hàn Quốc":
                return paths.get(23);
            case "HànQuốc":
                return paths.get(23);
            case "Bỉ":
                return paths.get(24);
            case "Panama":
                return paths.get(25);
            case "Tunisia":
                return paths.get(26);
            case "Anh":
                return paths.get(27);
            case "Colombia":
                return paths.get(28);
            case "Nhật Bản":
                return paths.get(29);
            case "NhậtBản":
                return paths.get(29);
            case "Ba Lan":
                return paths.get(30);
            case "BaLan":
                return paths.get(30);
            case "Senegal":
                return paths.get(31);
        }
        return "";
    }

    private static List<String> convertStringToFlag(List<String> list) {
        List<String> tempFlags = new ArrayList<>();
        for (int index = 1; index < list.size(); index++) {
            String mydata = list.get(index);
            Pattern pattern = Pattern.compile("https://static.bongda24h.vn/Medias/icon/\\d{4}/\\d{1}/\\d{1}/.*");
            Matcher matcher = pattern.matcher(mydata);
            if (matcher.find()) {
                tempFlags.add(matcher.group());
            }
            if (tempFlags.size() == 32) return tempFlags;
        }
        return null;
    }

    public static boolean checkPermissionCamera(Activity activity) {
        return ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public static void settingPermissionCameraOnActivity(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, Constants.PERMISSION_CAMERA
        );
    }

    public static void settingPermissionCameraOnFragment(Fragment fragment) {
        fragment.requestPermissions(
                new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, Constants.PERMISSION_CAMERA
        );
    }

    public static File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = String.format(Locale.US, "Vg_%d", System.currentTimeMillis());
        File storageDir = getAlbumStorageDir(Constants.IMAGE_FOLDER);

        // Save a file: path for use with ACTION_VIEW intents
        return File.createTempFile(
                imageFileName,        /* prefix    */
                ".jpg",         /* suffix    */
                storageDir            /* directory  */
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

    public static Bitmap fixOrientationBugOfProcessedBitmap(Context context, Bitmap bitmap, String mImagePath) {
        try {
            if (getCameraPhotoOrientation(context, Uri.parse(mImagePath)) == 0) {
                return bitmap;
            } else {
                Matrix matrix = new Matrix();
                matrix.postRotate(getCameraPhotoOrientation(context, Uri.fromFile(new File(mImagePath))));
                // Recreate the new Bitmap and set it back
                return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static int getCameraPhotoOrientation(@NonNull Context context, Uri imageUri) {
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(imageUri, null);
            ExifInterface exif = new ExifInterface(
                    imageUri.getPath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Vogo_", null);
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        return Uri.parse(path);
    }

    public static String getRealPathFromURI(Activity activity, Uri contentUri) {
        String res = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                res = cursor.getString(column_index);
            }
            cursor.close();
        }
        return res;
    }

    public static boolean deleteImageFolder(Activity activity) {
        return !Utils.checkPermissionReadExternalStorage(activity) && deleteRecursive(getAlbumStorageDir(Constants.IMAGE_FOLDER));
    }

    public static boolean checkPermissionReadExternalStorage(Activity activity) {
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

    public static void settingPermissionReadExternalOnFragment(Fragment fragment) {
        fragment.requestPermissions(
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }, Constants.PERMISSION_GALLERY
        );
    }
}
