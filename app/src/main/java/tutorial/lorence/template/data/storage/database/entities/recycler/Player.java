package tutorial.lorence.template.data.storage.database.entities.recycler;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */


public class Player {

    @SerializedName("default")
    @Expose
    private String _default;

    public Player(String _default) {
        this._default = _default;
    }

    public String get_default() {
        return _default;
    }
}
