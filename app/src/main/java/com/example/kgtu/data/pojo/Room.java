package com.example.kgtu.data.pojo;

import androidx.annotation.Nullable;

public class Room {

    private String color;
    private String colorCod;
    private String floor;

    public Room(String color, String colorCod, String floor) {
        this.color = color;
        this.colorCod = colorCod;
        this.floor = floor;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColorCod() {
        return colorCod;
    }

    public void setColorCod(String colorCod) {
        this.colorCod = colorCod;
    }

    @Override
    public boolean equals(@Nullable Object obj)  {
        Room room = (Room) obj;
        if (color.equals(room.getColor()) && floor.equals(room.getFloor()) && colorCod.equals(room.getColorCod())) {
            return true;
        } else {
            return false;
        }
    }
}
