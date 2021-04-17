package com.example.kgtu.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kgtu.R;
import com.example.kgtu.data.pojo.Deanery;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleItemHolder> {

    private Context context;

    public void setDeaneryItems(List<Deanery> deaneryItems) {
        this.deaneryItems = deaneryItems;
        notifyDataSetChanged();
    }

    private List<Deanery> deaneryItems;

    public ScheduleAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ScheduleItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScheduleItemHolder(LayoutInflater.from(context).inflate(R.layout.item_schedule, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleItemHolder holder, int position) {

        Deanery deanery = deaneryItems.get(position);
        holder.nameOfDeanery.setText(deanery.getNameOfDeanery());
        holder.shortInfo.setText(deanery.getAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("nameOfDeanery", deanery.getNameOfDeanery());
                bundle.putString("photo", deanery.getPhoto());
                bundle.putString("name", deanery.getName());
                bundle.putString("post", deanery.getPost());
                bundle.putString("workplace", deanery.getWorkplace());
                bundle.putString("address", deanery.getAddress());
                bundle.putString("phone", deanery.getPhone());
                bundle.putString("mail", deanery.getMail());
                bundle.putString("schedule", deanery.getSchedule());
                Navigation.findNavController(v).navigate(R.id.nav_schedule_info, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return deaneryItems.size();
    }

    public static class ScheduleItemHolder extends RecyclerView.ViewHolder {

        private TextView nameOfDeanery;
        private TextView shortInfo;

        public ScheduleItemHolder(@NonNull View itemView) {
            super(itemView);
            nameOfDeanery = itemView.findViewById(R.id.nameOfDeanery);
            shortInfo = itemView.findViewById(R.id.someInfo);
        }
    }

}
