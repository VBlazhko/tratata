package com.aaa.politindex.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemTimeLine {

    @SerializedName("idUser")
    @Expose
    private Integer idUser;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("like")
    @Expose
    private Integer like;
    @SerializedName("fulldate")
    @Expose
    private String fulldate;
    @SerializedName("isYour")
    @Expose
    private Boolean isYour;

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public String getFulldate() {
        return fulldate;
    }

    public void setFulldate(String fulldate) {
        this.fulldate = fulldate;
    }

    public Boolean getIsYour() {
        return isYour;
    }

    public void setIsYour(Boolean isYour) {
        this.isYour = isYour;
    }

}
