package com.aaa.politindex.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Biography {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("birth")
    @Expose
    private String birth;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

}
