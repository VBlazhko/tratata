package com.aaa.politindex.model;

/**
 * Created by 11 on 21.01.2018.
 */

public class Item {
    String key;
    String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Item{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
