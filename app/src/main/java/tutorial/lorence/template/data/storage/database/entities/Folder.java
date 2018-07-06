package tutorial.lorence.template.data.storage.database.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public class Folder implements Parcelable {

    private int resId;

    private String name;

    private String type;

    public Folder() {}

    public Folder(int resId, String name, String type) {
        this.resId = resId;
        this.name = name;
        this.type = type;
    }

    protected Folder(Parcel in) {
        resId = in.readInt();
        name = in.readString();
        type = in.readString();
    }

    public static final Creator<Folder> CREATOR = new Creator<Folder>() {
        @Override
        public Folder createFromParcel(Parcel in) {
            return new Folder(in);
        }

        @Override
        public Folder[] newArray(int size) {
            return new Folder[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(resId);
        parcel.writeString(name);
        parcel.writeString(type);
    }
}
