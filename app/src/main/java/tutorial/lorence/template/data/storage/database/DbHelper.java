package tutorial.lorence.template.data.storage.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public class DbHelper extends SQLiteOpenHelper {
    /**
     * Database name.
     */
    private static final String DATABASE_NAME = "dbdemo.db";
    /**
     * Database version. If you change the database schema, you must increase the database version.
     */
    private static final int DATABASE_VERSION = 1;
    /**
     * DbHelper instance.
     */
    private static DbHelper sHelper;

    /**
     * Constructor.
     *
     * @param contextApp The application context.
     */
    private DbHelper(final Context contextApp) {
        super(contextApp, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Get instance.
     *
     * @param context Application context
     * @return instance of DbHelper
     */
    public static synchronized DbHelper getInstance(final Context context) {
        if (sHelper == null) {
            sHelper = new DbHelper(context);
        }
        return sHelper;
    }

    /**
     * This function is used to reset the helper instance.
     */
    public static void resetHelper() {
        if (sHelper != null) {
            sHelper = null;
        }
    }

    /**
     * onCreate method. This method is used to create tables
     *
     * @param db The SQLiteDatabase.
     */
    public final void onCreate(final SQLiteDatabase db) {
        db.execSQL(DbContract.SQL_CREATE_SCHEDULE);
    }

    /**
     * onUpgrade method. This method is used to upgrade the database scheme
     *
     * @param db         The SQLiteDatabase.
     * @param oldVersion The old version.
     * @param newVersion The new version.
     */
    public final void onUpgrade(final SQLiteDatabase db, final int oldVersion,
                                final int newVersion) {
        if (oldVersion > newVersion) {
            deleteDatabase(db);
        } else {
            int oVersion = oldVersion;
            while (oVersion < newVersion) {
                switch (oVersion) {
                    case 1:
                        db.execSQL("ALTER TABLE "
                                + DbContract.TableSchedule.TABLE_NAME
                                + " ADD "
                                + DbContract.TableSchedule.COLUMN_NAME_DATE
                                + " INTEGER");
                        break;
                    default:
                        deleteDatabase(db);
                        break;
                }
                oVersion++;
            }
        }
    }

    /**
     * onDowngrade method. This method is used to downgrade the database scheme
     *
     * @param db         The SQLiteDatabase.
     * @param oldVersion The old version.
     * @param newVersion The new version.
     */
    public final void onDowngrade(final SQLiteDatabase db, final int oldVersion,
                                  final int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * Delete all data.
     *
     * @param db Database
     */
    private void deleteDatabase(final SQLiteDatabase db) {
        db.execSQL(DbContract.SQL_DELETE_SCHEDULE);
        // Create again
        onCreate(db);
    }
}
