package com.aaa.politindex.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FigureStatistics {

    @SerializedName("like")
    @Expose
    private Integer like;
    @SerializedName("dislike")
    @Expose
    private Integer dislike;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("isLiked")
    @Expose
    private Integer isLiked;

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getDislike() {
        return dislike;
    }

    public void setDislike(Integer dislike) {
        this.dislike = dislike;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Integer isLiked) {
        this.isLiked = isLiked;
    }

}
