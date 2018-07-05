package tutorial.lorence.template.data.storage.database.entities.recycler;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public class Data {

    @SerializedName("id")
    @Expose
    private String userid;

    @SerializedName("uploaded")
    @Expose
    private String uploaded;

    @SerializedName("updated")
    @Expose
    private String updated;

    @SerializedName("uploader")
    @Expose
    private String uploader;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("tags")
    @Expose
    private String[] tags;

    @SerializedName("thumbnail")
    @Expose
    private Thumbnail thumbnail;

    @SerializedName("player")
    @Expose
    private Player player;

    @SerializedName("content")
    @Expose
    private Content content;

    @SerializedName("duration")
    @Expose
    private Integer duration;

    @SerializedName("aspectRatio")
    @Expose
    private String aspectRatio;

    @SerializedName("rating")
    @Expose
    private Double rating;

    @SerializedName("likeCount")
    @Expose
    private String likeCount;

    @SerializedName("ratingCount")
    @Expose
    private Integer ratingCount;

    @SerializedName("viewCount")
    @Expose
    private Integer viewCount;

    @SerializedName("favoriteCount")
    @Expose
    private Integer favoriteCount;

    @SerializedName("commentCount")
    @Expose
    private Integer commentCount;

    @SerializedName("status")
    @Expose
    private Status status;

    @SerializedName("restrictions")
    @Expose
    private List<Restrictions> restrictions;

    @SerializedName("accessControl")
    @Expose
    private AccessControl accessControl;

    public Data(String userid, String uploaded, String updated, String uploader, String category, String title, String description, String[] tags, Thumbnail thumbnail, Player player, Content content, Integer duration, String aspectRatio, Double rating, String likeCount, Integer ratingCount, Integer viewCount, Integer favoriteCount, Integer commentCount, Status status, List<Restrictions> restrictions, AccessControl accessControl) {
        this.userid = userid;
        this.uploaded = uploaded;
        this.updated = updated;
        this.uploader = uploader;
        this.category = category;
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.thumbnail = thumbnail;
        this.player = player;
        this.content = content;
        this.duration = duration;
        this.aspectRatio = aspectRatio;
        this.rating = rating;
        this.likeCount = likeCount;
        this.ratingCount = ratingCount;
        this.viewCount = viewCount;
        this.favoriteCount = favoriteCount;
        this.commentCount = commentCount;
        this.status = status;
        this.restrictions = restrictions;
        this.accessControl = accessControl;
    }

    public String getUserid() {
        return userid;
    }

    public String getUploaded() {
        return uploaded;
    }

    public String getUpdated() {
        return updated;
    }

    public String getUploader() {
        return uploader;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String[] getTags() {
        return tags;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public Player getPlayer() {
        return player;
    }

    public Content getContent() {
        return content;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getAspectRatio() {
        return aspectRatio;
    }

    public Double getRating() {
        return rating;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public Integer getRatingCount() {
        return ratingCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public Status getStatus() {
        return status;
    }

    public List<Restrictions> getRestrictions() {
        return restrictions;
    }

    public AccessControl getAccessControl() {
        return accessControl;
    }
}
