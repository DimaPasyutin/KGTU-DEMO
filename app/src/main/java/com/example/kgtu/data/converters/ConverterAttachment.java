package com.example.kgtu.data.converters;

import androidx.room.TypeConverter;

import com.example.kgtu.data.pojo.Attachment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ConverterAttachment {

    @TypeConverter
    public String listItemsToString(List<Attachment> photos) {
        return new Gson().toJson(photos);
    }

    @TypeConverter
    public List<Attachment> stringToListItems(String itemsAsString) {
        Gson gson = new Gson();
        ArrayList object = gson.fromJson(itemsAsString, ArrayList.class);
        ArrayList<Attachment> items = new ArrayList<>();
        for (Object o: object) {
            items.add(gson.fromJson(o.toString(), Attachment.class));
        }
        return items;
    }

}
