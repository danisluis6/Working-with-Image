package tutorial.lorence.template.data.storage.database.entities;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public class Schedule implements Parcelable {

    private int index;
    private String player;
    private String player_path;
    private String enemy;
    private String enemy_path;
    private String time;
    private String date;
    public ArrayList<Schedule> schedules;

    public Schedule() {
    }

    public Schedule(int index, String player, String player_path, String enemy, String enemy_path, String time, String date) {
        this.index = index;
        this.player = player;
        this.player_path = player_path;
        this.enemy = enemy;
        this.enemy_path = enemy_path;
        this.time = time;
        this.date = date;
    }

    public Schedule(String player, String player_path, String enemy, String enemy_path, String time, String date) {
        this.player = player;
        this.player_path = player_path;
        this.enemy = enemy;
        this.enemy_path = enemy_path;
        this.time = time;
        this.date = date;
    }

    protected Schedule(Parcel in) {
        index = in.readInt();
        player = in.readString();
        player_path = in.readString();
        enemy = in.readString();
        enemy_path = in.readString();
        time = in.readString();
        date = in.readString();
    }

    public static final Creator<Schedule> CREATOR = new Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel in) {
            return new Schedule(in);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getPlayerPath() {
        return player_path;
    }

    public void setPlayerPath(String player_path) {
        this.player_path = player_path;
    }

    public String getEnemy() {
        return enemy;
    }

    public void setEnemy(String enemy) {
        this.enemy = enemy;
    }

    public String getEnemyPath() {
        return enemy_path;
    }

    public void setEnemyPath(String enemy_path) {
        this.enemy_path = enemy_path;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(index);
        dest.writeString(player);
        dest.writeString(player_path);
        dest.writeString(enemy);
        dest.writeString(enemy_path);
        dest.writeString(time);
        dest.writeString(date);
    }
}
