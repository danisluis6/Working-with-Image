package tutorial.lorence.template.data.storage.database;

import android.provider.BaseColumns;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public final class DbContract {
    /**
     * Text data type.
     */
    private static final String TEXT_TYPE = " TEXT";
    /**
     * Integer data type.
     */
    private static final String INTEGER_TYPE = " INTEGER";
    /**
     * Comma symbol.
     */
    private static final String COMMA_SEP = ",";
    /**
     * Left bracket symbol.
     */
    private static final String LEFT_BRACKET_SEP = " (";
    /**
     * Right bracket symbol.
     */
    private static final String RIGHT_BRACKET_SEP = " );";
    /**
     * Primary key.
     */
    private static final String PRIMARY_AUTOINCREMENT = " PRIMARY KEY AUTOINCREMENT";
    /**
     * Create table statement.
     */
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
    /**
     * Create query for USER table.
     */
    static final String SQL_CREATE_SCHEDULE = new StringBuilder(CREATE_TABLE)
            .append(TableSchedule.TABLE_NAME).append(LEFT_BRACKET_SEP)
            .append(TableSchedule.COLUMN_NAME_INDEX).append(INTEGER_TYPE)
            .append(PRIMARY_AUTOINCREMENT)
            .append(COMMA_SEP)
            .append(TableSchedule.COLUMN_NAME_PLAYER).append(TEXT_TYPE)
            .append(COMMA_SEP)
            .append(TableSchedule.COLUMN_NAME_PLAYER_PATH).append(TEXT_TYPE)
            .append(COMMA_SEP)
            .append(TableSchedule.COLUMN_NAME_ENEMY).append(TEXT_TYPE)
            .append(COMMA_SEP)
            .append(TableSchedule.COLUMN_NAME_ENEMY_PATH).append(TEXT_TYPE)
            .append(COMMA_SEP)
            .append(TableSchedule.COLUMN_NAME_TIME).append(TEXT_TYPE)
            .append(COMMA_SEP)
            .append(TableSchedule.COLUMN_NAME_DATE).append(TEXT_TYPE)
            .append(RIGHT_BRACKET_SEP)
            .toString();
    /**
     * Drop table statement.
     */
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";
    /**
     * Drop query for User table.
     */
    static final String SQL_DELETE_SCHEDULE = new StringBuilder(DROP_TABLE)
            .append(TableSchedule.TABLE_NAME).toString();

    /**
     * Constructor. Prevents the DbUser class from being instantiated.
     */
    private DbContract() {
    }

    public abstract static class TableSchedule implements BaseColumns {
        public static final String TABLE_NAME = "schedule";
        public static final String COLUMN_NAME_INDEX = "id";
        public static final String COLUMN_NAME_PLAYER = "player";
        public static final String COLUMN_NAME_PLAYER_PATH = "player_path";
        public static final String COLUMN_NAME_ENEMY = "enemy";
        public static final String COLUMN_NAME_ENEMY_PATH = "enemy_path";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_DATE = "date";
    }
}
