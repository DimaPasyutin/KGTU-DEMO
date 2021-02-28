package com.example.kgtu.data.converters;

import android.os.Build;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConverterPhotos {
    @TypeConverter
    public String listPhotosToString(List<String> photos) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return photos.stream().collect(Collectors.joining(","));
        } else
            return null;
    }

    @TypeConverter
    public List<String> stringToListPhotos(String photosAsString) {
        return Arrays.asList(photosAsString.split(","));
    }
}
