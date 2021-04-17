package com.example.kgtu.data.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Deanery {

    @SerializedName("nameOfDeanery")
    @Expose
    private String nameOfDeanery;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("post")
    @Expose
    private String post;
    @SerializedName("workplace")
    @Expose
    private String workplace;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("mail")
    @Expose
    private String mail;
    @SerializedName("schedule")
    @Expose
    private String schedule;

    public String getNameOfDeanery() {
        return nameOfDeanery;
    }

    public void setNameOfDeanery(String nameOfDeanery) {
        this.nameOfDeanery = nameOfDeanery;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

}
