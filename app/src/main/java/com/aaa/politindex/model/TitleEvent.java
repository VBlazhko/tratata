package com.aaa.politindex.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TitleEvent {

    @SerializedName("idEvent")
    @Expose
    public Integer idEvent;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("icon")
    @Expose
    public String icon;


    public Integer getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Integer idEvent) {
        this.idEvent = idEvent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}