package com.example.kgtu.data.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.example.kgtu.data.converters.ConverterAttachment;
import com.example.kgtu.data.pojo.Item;
import com.example.kgtu.data.pojo.Post;

import java.util.List;

@Dao
public interface PostDao {

    @Query("SELECT * FROM posts")
    LiveData<List<Post>> getAllPosts();

    @Query("SELECT * FROM posts WHERE id == :PostId")
    Post getPostById(int PostId);

    @Query("DELETE FROM posts")
    void deleteAllPosts();

    @Insert
    void insertPost(Post post);

    @Delete
    void deletePost(Post post);

}
