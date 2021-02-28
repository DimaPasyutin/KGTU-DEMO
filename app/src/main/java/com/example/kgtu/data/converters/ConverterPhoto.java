package com.example.kgtu.data.converters;

import androidx.room.TypeConverter;

import com.example.kgtu.data.pojo.Photo;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ConverterPhoto {

    @TypeConverter
    public String listItemsToString(List<Photo> photos) {
        return new Gson().toJson(photos);
    }

    @TypeConverter
    public List<Photo> stringToListItems(String itemsAsString) {
        Gson gson = new Gson();
        ArrayList object = gson.fromJson(itemsAsString, ArrayList.class);
        ArrayList<Photo> items = new ArrayList<>();
        for (Object o: object) {
            items.add(gson.fromJson(o.toString(), Photo.class));
        }
        return items;
    }

}
