package com.example.kgtu.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.kgtu.R;
import com.example.kgtu.data.pojo.Post;
import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context context;
    private List<Post> posts;
    private OnReachEndListener onReachEndListener;
    private long counter = 0;

    public NewsAdapter(Context context) {
        this.context = context;
        this.posts = new ArrayList<>();
    }

    public NewsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    public void addPosts(List<Post> posts) {
        this.posts.addAll(posts);
        notifyDataSetChanged();
    }

    public interface OnReachEndListener {
        void onReachEnd(long count);
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        Post post = posts.get(position);

        // Обновление списка постов
        if (posts.size() >= 5 && position == posts.size() - 3 && onReachEndListener != null) {
            onReachEndListener.onReachEnd(++counter);
        }

        //Работа с размером текста
        if(post.getText().length() > 150) {
            holder.textViewArticle.setText(post.getPreview());
            LinkBuilder.on(holder.textViewArticle).addLink(post.getLink(holder.textViewArticle)).build();
        } else {
            holder.textViewArticle.setText(post.getText());
        }

        // Устанавливаем дату
        holder.textViewDate.setText(post.getDate());

        // Устанавливаем ViewPager
        setItemRecycler(holder.viewPager2Photos, post.getPhoto1280());

        // Создаю метки переключения между фото
        if(post.getPhoto1280().size() > 1) {
            holder.setDots(new TextView[post.getPhoto1280().size()]);
            holder.setIndicators();

            holder.viewPager2Photos.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
                @SuppressLint("ResourceAsColor")
                @Override
                public void onPageSelected(int position) {
                    if(holder.dots[position] != null) {
                        for (int i = 0; i < post.getPhoto1280().size(); i++) {
                            if (i == position) {
                                holder.dots[i].setTextColor(R.color.purple_500);
                            }else {
                                holder.dots[i].setTextColor(R.color.black);
                            }
                        }
                    }
                    super.onPageSelected(position);
                }
            });
        } else if (post.getPhoto1280().isEmpty()){
            holder.layout.setVisibility(LinearLayout.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static final class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView textViewDate;
        TextView textViewArticle;
        ViewPager2 viewPager2Photos;
        TextView[] dots;
        LinearLayout layout;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewArticle = itemView.findViewById(R.id.textViewArticle);
            viewPager2Photos = itemView.findViewById(R.id.image_container);
            layout = itemView.findViewById(R.id.dots_container);
        }

        public void setDots(TextView[] dots) {
            this.dots = dots;
        }

        private void setIndicators() {
            for (int i = 0; i < dots.length; i++) {
                dots[i] = new TextView(itemView.getContext());
                dots[i].setText(Html.fromHtml("&#9679;"));
                dots[i].setTextSize(12);
                layout.addView(dots[i]);
            }

        }

    }

    private void setItemRecycler(ViewPager2 viewPager2Photos, List<String> photos){

        PhotoAdapter photoAdapter = new PhotoAdapter(context, photos);
        viewPager2Photos.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager2Photos.setAdapter(photoAdapter);

    }

}
