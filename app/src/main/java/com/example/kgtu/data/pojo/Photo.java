package com.example.kgtu.data.pojo;

import androidx.room.TypeConverters;

import com.example.kgtu.data.converters.ConverterPhoto;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@TypeConverters(value = ConverterPhoto.class)
public class Photo {

    @SerializedName("album_id")
    @Expose
    private int albumId;
    @SerializedName("date")
    @Expose
    private int date;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("owner_id")
    @Expose
    private int ownerId;
    @SerializedName("has_tags")
    @Expose
    private boolean hasTags;
    @SerializedName("access_key")
    @Expose
    private String accessKey;
    @SerializedName("height")
    @Expose
    private int height;
    @SerializedName("photo_1280")
    @Expose
    private String photo1280;
    @SerializedName("photo_130")
    @Expose
    private String photo130;
    @SerializedName("photo_2560")
    @Expose
    private String photo2560;
    @SerializedName("photo_604")
    @Expose
    private String photo604;
    @SerializedName("photo_75")
    @Expose
    private String photo75;
    @SerializedName("photo_807")
    @Expose
    private String photo807;
    @SerializedName("post_id")
    @Expose
    private int postId;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("width")
    @Expose
    private int width;

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public boolean isHasTags() {
        return hasTags;
    }

    public void setHasTags(boolean hasTags) {
        this.hasTags = hasTags;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getPhoto1280() {
        return photo1280;
    }

    public void setPhoto1280(String photo1280) {
        this.photo1280 = photo1280;
    }

    public String getPhoto130() {
        return photo130;
    }

    public void setPhoto130(String photo130) {
        this.photo130 = photo130;
    }

    public String getPhoto2560() {
        return photo2560;
    }

    public void setPhoto2560(String photo2560) {
        this.photo2560 = photo2560;
    }

    public String getPhoto604() {
        return photo604;
    }

    public void setPhoto604(String photo604) {
        this.photo604 = photo604;
    }

    public String getPhoto75() {
        return photo75;
    }

    public void setPhoto75(String photo75) {
        this.photo75 = photo75;
    }

    public String getPhoto807() {
        return photo807;
    }

    public void setPhoto807(String photo807) {
        this.photo807 = photo807;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

}
