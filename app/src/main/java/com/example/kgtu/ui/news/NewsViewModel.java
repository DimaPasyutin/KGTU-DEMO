package com.example.kgtu.ui.news;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.kgtu.data.api.ApiNews;
import com.example.kgtu.data.api.ApiService;
import com.example.kgtu.data.database.PostDatabase;
import com.example.kgtu.data.pojo.Attachment;
import com.example.kgtu.data.pojo.Example;
import com.example.kgtu.data.pojo.Item;
import com.example.kgtu.data.pojo.Post;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NewsViewModel extends AndroidViewModel {

    private static PostDatabase database;
    private MutableLiveData<List<Post>> posts;
    private MutableLiveData<List<Post>> reservPosts;
    private List<Item> items;
    private Disposable disposable;

    public static final String TOKEN = "5dab56bc5dab56bc5dab56bc525ddd054955dab5dab56bc3d8a54c1e4b840b5f7a23512";

    public NewsViewModel(@NonNull Application application) {
        super(application);
        database = PostDatabase.getInstance(getApplication());
        posts = new MutableLiveData<>();
        reservPosts = new MutableLiveData<>();
    }

    // Загружает данные с помощью API в фоновом потоке и результат выдаёт в UI поток,
    // а при сбое пытается забрать данные из базы данных(последнее пока не получается)
    public void loadData(long ownerId, long count) {
        Log.i("PostLoad", "done");
        ApiNews apiNews = ApiNews.getInstance();
        ApiService apiservice = apiNews.getApiNews();
        disposable = apiservice.getResponse(ownerId, count, TOKEN, 5.21)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Example>() {

                    @Override
                    public void accept(Example example) throws Exception {
                        if (example != null) {
                            Log.i("PostDone", "Done" + example.getResponse().getItems().size());
                            items = example.getResponse().getItems();
                            convertListToPosts(items);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
//                        reservPosts = (MutableLiveData) database.postDao().getAllPosts();
                        Log.i("PostError", throwable.getMessage());
                    }
                });
    }

    public LiveData<List<Post>> getPosts() {
        return posts;
    }

    public MutableLiveData<List<Post>> getReservPosts() {
        return reservPosts;
    }

    // Попытка запихнуть операцию в фоновый поток, но не знаю насколько верное решение
    public void deleteAllPosts() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                database.postDao().deleteAllPosts();
            }
        });

    }

    // Из списка items(получаем при загрузке с поомщью API) получаем список с объектами Post,
    // которые вставляем в наблюдаемый список posts, необходимый для отображения в RecyclerView
    // P.S попытался запихнуть все в базу данных, но видимо проблема с преобразованием типов данных
    @SuppressLint("SimpleDateFormat")
    private void convertListToPosts(List<Item> items) {
        List<Post> result = new ArrayList<>();
        if(items != null && items.size() > 0) {
            for(Item item: items) {

                int id = 0;
                String text = null;
                String date = null;
                List<String> photo = new ArrayList<>();

                if (item != null ) {
                    id = item.getId();
                    text = item.getText();
                    long unix = (long) item.getDate();
                    date= new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new java.util.Date (unix*1000));


                    List<Attachment> attachments = item.getAttachments();
                    if (attachments != null && attachments.size() > 0) {
                        for(Attachment attachment: attachments) {
                            if (attachment.getPhoto() != null  && attachment.getType().equals("photo")) {
                                photo.add(attachment.getPhoto().getPhoto1280());
                            }
                        }
                    }
                }
                if(id != 0 && text != null && photo.size() > 0) {
//                                        database.postDao().insertPost(new Post(id, text, photo));
                    result.add(new Post(id, text, date,  photo));
                }
            }
        }
        posts.setValue(result);
//        new InsertPostTask().execute(result);
    }

    // Вставляю полученный список объектов Post в базу данных
    private static class InsertPostTask extends AsyncTask<List<Post>, Void, Void> {

            @SafeVarargs
            @Override
            protected final Void doInBackground(List<Post>... lists) {
                if (lists != null && lists.length > 0) {
                    List<Post> posts = lists[0];
                    for (Post post: posts)
                    database.postDao().insertPost(post);
                }
                return null;
            }
        }

    public void closeLoadData() {
        if (disposable != null) {
            disposable.dispose();
        }
    }


    public void deletePost(Post post) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                database.postDao().deletePost(post);
            }
        });

    }

}