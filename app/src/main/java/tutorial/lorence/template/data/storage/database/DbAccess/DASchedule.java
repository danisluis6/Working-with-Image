package tutorial.lorence.template.data.storage.database.DbAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import tutorial.lorence.template.data.storage.database.DbContract;
import tutorial.lorence.template.data.storage.database.DbHelper;
import tutorial.lorence.template.data.storage.database.entities.Schedule;
import tutorial.lorence.template.other.Constants;
import tutorial.lorence.template.other.Utils;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public class DASchedule {

    private ContentValues getContentValues(final Schedule schedule, Context context) {
        ContentValues values = new ContentValues();
        values.put(DbContract.TableSchedule.COLUMN_NAME_PLAYER, schedule.getPlayer());
        values.put(DbContract.TableSchedule.COLUMN_NAME_PLAYER_PATH, schedule.getPlayerPath());
        values.put(DbContract.TableSchedule.COLUMN_NAME_ENEMY, schedule.getEnemy());
        values.put(DbContract.TableSchedule.COLUMN_NAME_ENEMY_PATH, schedule.getEnemyPath());
        values.put(DbContract.TableSchedule.COLUMN_NAME_TIME, schedule.getTime());
        values.put(DbContract.TableSchedule.COLUMN_NAME_DATE, schedule.getDate());
        return values;
    }

    private Schedule getFromCursor(final Cursor cursor) {
        int scheduleId = cursor.getInt(cursor.getColumnIndex(DbContract.TableSchedule.COLUMN_NAME_INDEX));
        String player = cursor.getString(cursor.getColumnIndex(DbContract.TableSchedule.COLUMN_NAME_PLAYER));
        String player_path = cursor.getString(cursor.getColumnIndex(DbContract.TableSchedule.COLUMN_NAME_PLAYER_PATH));
        String enemy = cursor.getString(cursor.getColumnIndex(DbContract.TableSchedule.COLUMN_NAME_ENEMY));
        String enemy_path = cursor.getString(cursor.getColumnIndex(DbContract.TableSchedule.COLUMN_NAME_ENEMY_PATH));
        String time = cursor.getString(cursor.getColumnIndex(DbContract.TableSchedule.COLUMN_NAME_TIME));
        String date = cursor.getString(cursor.getColumnIndex(DbContract.TableSchedule.COLUMN_NAME_DATE));
        return new Schedule(scheduleId, player, player_path, enemy, enemy_path, time, date);
    }

    public long add(Schedule schedule, Context context) {
        long id = 0;
        DbHelper dbHelper = DbHelper.getInstance(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = getContentValues(schedule, context);
        if (db != null && db.isOpen()) {
            id = db.insert(DbContract.TableSchedule.TABLE_NAME, null, values);
        }
        return id;
    }

    public boolean edit(Schedule schedule, Context context) {
        DbHelper dbHelper = DbHelper.getInstance(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DbContract.TableSchedule.COLUMN_NAME_PLAYER, schedule.getPlayer());
        cv.put(DbContract.TableSchedule.COLUMN_NAME_PLAYER_PATH, schedule.getPlayerPath());
        cv.put(DbContract.TableSchedule.COLUMN_NAME_ENEMY, schedule.getEnemy());
        cv.put(DbContract.TableSchedule.COLUMN_NAME_ENEMY_PATH, schedule.getEnemyPath());
        cv.put(DbContract.TableSchedule.COLUMN_NAME_TIME, schedule.getTime());
        cv.put(DbContract.TableSchedule.COLUMN_NAME_DATE, schedule.getDate());
        try {
            db.update(DbContract.TableSchedule.TABLE_NAME, cv, DbContract.TableSchedule.COLUMN_NAME_INDEX + "=" + schedule.getIndex(), null);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void addAll(List<Schedule> schedules, Context context) {
        if (schedules.size() > 0) {
            DbHelper dbHelper = DbHelper.getInstance(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            if (db != null && db.isOpen()) {
                for (Schedule schedule : schedules) {
                    ContentValues values = getContentValues(schedule, context);
                    db.insert(DbContract.TableSchedule.TABLE_NAME, null, values);
                }
            }
        }
    }

    private String[] getProjection() {
        return new String[]{
                DbContract.TableSchedule.COLUMN_NAME_INDEX,
                DbContract.TableSchedule.COLUMN_NAME_PLAYER,
                DbContract.TableSchedule.COLUMN_NAME_PLAYER_PATH,
                DbContract.TableSchedule.COLUMN_NAME_ENEMY,
                DbContract.TableSchedule.COLUMN_NAME_ENEMY_PATH,
                DbContract.TableSchedule.COLUMN_NAME_TIME,
                DbContract.TableSchedule.COLUMN_NAME_DATE
        };
    }

    public ArrayList<Schedule> getAll(Context context) {
        ArrayList<Schedule> listContact = new ArrayList<>();
        DbHelper dbHelper = DbHelper.getInstance(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db != null && db.isOpen()) {
            Cursor cursor = db.query(DbContract.TableSchedule.TABLE_NAME, getProjection(),
                    null, null, null, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                Schedule schedule;
                do {
                    schedule = getFromCursor(cursor);
                    listContact.add(schedule);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return listContact;
    }

    public boolean deleteAll(Context context) {
        DbHelper dbHelper = DbHelper.getInstance(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db != null && db.isOpen()
                && db.delete(DbContract.TableSchedule.TABLE_NAME, null, null) > 0;
    }

    public boolean deleteById(int index, Context context) {
        DbHelper dbHelper = DbHelper.getInstance(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = new StringBuilder(Constants.EMPTY_STRING)
                .append(DbContract.TableSchedule.COLUMN_NAME_INDEX).append(Constants.EQUAL)
                .toString();
        String[] selectionArgs = {String.valueOf(index)};
        return db != null && db.isOpen()
                && db.delete(DbContract.TableSchedule.TABLE_NAME, selection, selectionArgs) > 0;
    }

    public boolean deleteByIds(List<Integer> scheduleIds, Context context) {
        DbHelper dbHelper = DbHelper.getInstance(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String scheduleIDs = TextUtils.join(",", scheduleIds.toArray());
        String selection = new StringBuilder(Constants.EMPTY_STRING)
                .append(DbContract.TableSchedule.COLUMN_NAME_INDEX).append(Constants.IN)
                .append(Constants.LEFT_BRACKET_SEP).append(scheduleIDs).append(Constants.RIGHT_BRACKET_SEP).toString();
        return db != null && db.isOpen()
                && db.delete(DbContract.TableSchedule.TABLE_NAME, selection, null) > 0;
    }

    public List<Schedule> searchForSchedule(Context context, String data, String type) {
        List<Schedule> schedules = new ArrayList<>();
        DbHelper dbHelper = DbHelper.getInstance(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = new StringBuilder(Constants.EMPTY_STRING)
                .append(Utils.equalsIgnoreCase(type, "name") ? DbContract.TableSchedule.COLUMN_NAME_PLAYER : DbContract.TableSchedule.COLUMN_NAME_ENEMY)
                .append(Constants.LIKE)
                .toString();
        String[] selectionArgs = {data + Constants.LIKE_SEP};
        if (db != null && db.isOpen()) {
            Cursor cursor = db.query(DbContract.TableSchedule.TABLE_NAME, getProjection(),
                    selection, selectionArgs, null, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                Schedule schedule;
                do {
                    schedule = getFromCursor(cursor);
                    schedules.add(schedule);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return schedules;
    }
}
