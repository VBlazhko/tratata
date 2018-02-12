package com.aaa.politindex.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemComment {

    @SerializedName("idComment")
    @Expose
    private Integer idComment;
    @SerializedName("fulldate")
    @Expose
    private String fulldate;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("isSelf")
    @Expose
    private Boolean isSelf;

    public Integer getIdComment() {
        return idComment;
    }

    public void setIdComment(Integer idComment) {
        this.idComment = idComment;
    }

    public String getFulldate() {
        return fulldate;
    }

    public void setFulldate(String fulldate) {
        this.fulldate = fulldate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(Boolean isSelf) {
        this.isSelf = isSelf;
    }

}
