package com.example.kgtu.data.converters;

import androidx.room.TypeConverter;

import com.example.kgtu.data.pojo.Item;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ConverterItem {

    @TypeConverter
    public String listItemsToString(List<Item> items) {
        return new Gson().toJson(items);
    }

    @TypeConverter
    public List<Item> stringToListItems(String itemsAsString) {
        Gson gson = new Gson();
        ArrayList object = gson.fromJson(itemsAsString, ArrayList.class);
        ArrayList<Item> items = new ArrayList<>();
        for (Object o: object) {
            items.add(gson.fromJson(o.toString(), Item.class));
        }
        return items;
    }

}
