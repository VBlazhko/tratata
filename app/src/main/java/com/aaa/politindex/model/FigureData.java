package com.aaa.politindex.model;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FigureData {

    @SerializedName("items")
    @Expose
    private List<ItemComment> commentItems = null;
    @SerializedName("itemCount")
    @Expose
    private Integer itemCount;
    @SerializedName("figure")
    @Expose
    private FigureStatistics figure;

    public List<ItemComment> getItems() {
        return commentItems;
    }

    public void setItems(List<ItemComment> comments) {
        this.commentItems = comments;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public FigureStatistics getFigure() {
        return figure;
    }

    public void setFigure(FigureStatistics figure) {
        this.figure = figure;
    }

}
