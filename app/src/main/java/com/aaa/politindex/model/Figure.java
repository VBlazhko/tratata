package com.aaa.politindex.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Figure {

    @SerializedName("idFigure")
    @Expose
    private Integer idFigure;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("middlename")
    @Expose
    private String middlename;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("avatarSmall")
    @Expose
    private String avatarSmall;
    @SerializedName("isLiked")
    @Expose
    private Integer isLiked;
//    @SerializedName("today")
//    @Expose
//    private Today today;
//    @SerializedName("graph")
    @Expose
    private Graph graph;

    public Integer getIdFigure() {
        return idFigure;
    }

    public void setIdFigure(Integer idFigure) {
        this.idFigure = idFigure;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatarSmall() {
        return avatarSmall;
    }

    public void setAvatarSmall(String avatarSmall) {
        this.avatarSmall = avatarSmall;
    }

    public Integer getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Integer isLiked) {
        this.isLiked = isLiked;
    }

//    public Today getToday() {
//        return today;
//    }

//    public void setToday(Today today) {
//        this.today = today;
//    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

}
