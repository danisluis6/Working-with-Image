package tutorial.lorence.template.data.storage.database.entities.recycler;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public class Item {

    @SerializedName("apiVersion")
    @Expose
    private String apiVersion;

    @SerializedName("data")
    @Expose
    private Data data;

    public Item() {
    }

    public Item(String userid, Data data) {
        this.apiVersion = userid;
        this.data = data;
    }

    public String getUserid() {
        return apiVersion;
    }


    public Data getData() {
        return data;
    }

}
