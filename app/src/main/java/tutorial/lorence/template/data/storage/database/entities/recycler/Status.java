package tutorial.lorence.template.data.storage.database.entities.recycler;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public class Status {

    @SerializedName("value")
    @Expose
    private String value;

    @SerializedName("reason")
    @Expose
    private String reason;

    public Status(String value, String reason) {
        this.value = value;
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public String getValue() {
        return value;
    }
}
