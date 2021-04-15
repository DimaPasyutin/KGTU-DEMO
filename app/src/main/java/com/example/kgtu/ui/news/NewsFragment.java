package com.example.kgtu.ui.news;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kgtu.R;
import com.example.kgtu.adapters.NewsAdapter;
import com.example.kgtu.data.pojo.Post;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private NewsViewModel newsViewModel;
    private RecyclerView recyclerViewNews;
    private NewsAdapter newsAdapter;
    private long countStart = 50;
    private List<Post> generalListPost;
    private List<Post> betweenListPost;
    private static long userId =  39135494;
//    private long userId = 31547740;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newsViewModel =
                new ViewModelProvider(this).get(NewsViewModel.class);
        newsViewModel.loadData(-userId, countStart);
        View root = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerViewNews = root.findViewById(R.id.recyclerViewNews);
        recyclerViewNews.setLayoutManager(new LinearLayoutManager(getContext()));
        newsAdapter = new NewsAdapter(getContext());
        generalListPost = new ArrayList<>(1000);
        betweenListPost = new ArrayList<>();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Интерфейс, необходимый для подгрузки постов, но доходя до определенного элемента
        // начинается бесконечный вызов onReachEnd, так как список в recyclerView перестает обновляться
        newsAdapter.setOnReachEndListener(new NewsAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd(long count) {
                newsViewModel.loadData(-userId, count*50);
                Log.i("onReachEnd", "Called"+count*50);
            }
        });

        // Изменение списка в RecyclerView
        newsViewModel.getPosts().observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                if(generalListPost.size() > 3) {
                    generalListPost.clear();
                    posts.removeAll(generalListPost);
                    betweenListPost.addAll(posts);
                    newsAdapter.addPosts(betweenListPost);
                    betweenListPost.clear();
                } else {
                    generalListPost.addAll(posts);
                    newsAdapter.setPosts(generalListPost);
                    recyclerViewNews.setAdapter(newsAdapter);
                }
            }
        });

        // Подгрузка данных из базы данных при ошибке в методе loadData(не работает)
        newsViewModel.getReservPosts().observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                newsAdapter.setPosts(posts);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        newsViewModel.closeLoadData();
    }

}