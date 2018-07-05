package tutorial.lorence.template.data.storage.database.entities.recycler;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public class Restrictions {

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("relationship")
    @Expose
    private String relationship;

    @SerializedName("countries")
    @Expose
    private String countries;

    public Restrictions(String type, String relationship, String countries) {
        this.type = type;
        this.relationship = relationship;
        this.countries = countries;
    }

    public String getCountries() {
        return countries;
    }

    public String getRelationship() {
        return relationship;
    }

    public String getType() {
        return type;
    }
}
