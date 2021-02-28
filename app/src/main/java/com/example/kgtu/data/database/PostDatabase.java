package com.example.kgtu.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.kgtu.data.pojo.Post;

@Database(entities = {Post.class}, version = 1, exportSchema = false)
public abstract class PostDatabase extends RoomDatabase {

    public static final String DB_NAME = "posts.db";
    private static PostDatabase database;
    public static final Object LOCK = new Object();

    public static PostDatabase getInstance (Context context) {
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context, PostDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
            }
        }
        return database;
    }

    public abstract PostDao postDao();

}
