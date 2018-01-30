package com.aaa.politindex.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Today implements Parcelable{

    @SerializedName("like")
    @Expose
    private Integer like;
    @SerializedName("dislike")
    @Expose
    private Integer dislike;
    @SerializedName("rating")
    @Expose
    private Integer rating;

    protected Today(Parcel in) {
        if (in.readByte() == 0) {
            like = null;
        } else {
            like = in.readInt();
        }
        if (in.readByte() == 0) {
            dislike = null;
        } else {
            dislike = in.readInt();
        }
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readInt();
        }
    }

    public static final Creator<Today> CREATOR = new Creator<Today>() {
        @Override
        public Today createFromParcel(Parcel in) {
            return new Today(in);
        }

        @Override
        public Today[] newArray(int size) {
            return new Today[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (like == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(like);
        }
        if (dislike == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(dislike);
        }
        if (rating == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(rating);
        }
    }
}