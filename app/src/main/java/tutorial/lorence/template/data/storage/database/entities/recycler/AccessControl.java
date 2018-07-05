package tutorial.lorence.template.data.storage.database.entities.recycler;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */
public class AccessControl {

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("commentVote")
    @Expose
    private String commentVote;

    @SerializedName("videoRespond")
    @Expose
    private String videoRespond;

    @SerializedName("rate")
    @Expose
    private String rate;

    @SerializedName("embed")
    @Expose
    private String embed;

    @SerializedName("list")
    @Expose
    private String list;

    @SerializedName("autoPlay")
    @Expose
    private String autoPlay;

    @SerializedName("syndicate")
    @Expose
    private String syndicate;

    public AccessControl(String comment, String commentVote, String videoRespond, String rate, String embed, String list, String autoPlay, String syndicate) {
        this.comment = comment;
        this.commentVote = commentVote;
        this.videoRespond = videoRespond;
        this.rate = rate;
        this.embed = embed;
        this.list = list;
        this.autoPlay = autoPlay;
        this.syndicate = syndicate;
    }

    public String getComment() {
        return comment;
    }

    public String getCommentVote() {
        return commentVote;
    }

    public String getVideoRespond() {
        return videoRespond;
    }

    public String getRate() {
        return rate;
    }

    public String getEmbed() {
        return embed;
    }

    public String getList() {
        return list;
    }

    public String getAutoPlay() {
        return autoPlay;
    }

    public String getSyndicate() {
        return syndicate;
    }
}
