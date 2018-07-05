package tutorial.lorence.template.data.storage.database.entities.recycler;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */
public class Thumbnail {

    @SerializedName("sqDefault")
    @Expose
    private String sqDefault;

    @SerializedName("hqDefault")
    @Expose
    private String hqDefault;

    public Thumbnail(String sqDefault, String hqDefault) {
        this.sqDefault = sqDefault;
        this.hqDefault = hqDefault;
    }

    public String getSqDefault() {
        return sqDefault;
    }

    public String getHqDefault() {
        return hqDefault;
    }
}
