package tutorial.lorence.template.data.storage;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public class DatabaseInfo {

    // Database version
    public static final int DB_VERSION = 1;

    // Database name
    public static final String DB_NAME = "world_cup";

    // Table Listing
    public static class Tables {
        public static final String WorldCup = "worldcup";
    }

    public static class Schedule {
        public static final String COLUMN_ID = "index";
        public static final String COLUMN_PLAYER = "player";
        public static final String COLUMN_ENEMY = "enemy";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_DATE = "date";
    }
}
