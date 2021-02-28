package com.example.kgtu.data.pojo;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.kgtu.data.converters.ConverterAttachment;
import com.example.kgtu.data.converters.ConverterPhotos;
import com.squareup.picasso.Picasso;

import java.util.List;

@Entity(tableName = "posts")
@TypeConverters(value = ConverterPhotos.class)
public class Post {

    @PrimaryKey(autoGenerate = true)
    private int idPost;
    private int id;
    private String text;
    private String date;
    private List<String> photo1280;

    public Post(int idPost, int id,  String text, String date,  List<String> photo1280) {
        this.idPost = idPost;
        this.id = id;
        this.text = text;
        this.date = date;
        this.photo1280 = photo1280;
    }

    @Ignore
    public Post(int id, String text, String date, List<String> photo1280) {
        this.idPost = id;
        this.text = text;
        this.date = date;
        this.photo1280 = photo1280;
    }

    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getPhoto1280() {
        return photo1280;
    }

    public void setPhoto1280(List<String> photo1280) {
        this.photo1280 = photo1280;
    }

}
