package tutorial.lorence.template.data.storage.database.entities.recycler;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public class Content {

    @SerializedName("5")
    @Expose
    private String five;

    public Content(String five) {
        this.five = five;
    }

    public String getFive() {
        return five;
    }
}
