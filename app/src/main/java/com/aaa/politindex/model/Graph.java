package com.aaa.politindex.model;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Graph {

    @SerializedName("items")
    @Expose
    private List<ItemDiagram> items = null;
    @SerializedName("itemCount")
    @Expose
    private Integer itemCount;

    public List<ItemDiagram> getItems() {
        return items;
    }

    public void setItems(List<ItemDiagram> items) {
        this.items = items;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

}