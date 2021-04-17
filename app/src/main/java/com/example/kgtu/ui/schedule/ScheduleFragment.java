package com.example.kgtu.ui.schedule;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kgtu.R;
import com.example.kgtu.adapters.ScheduleAdapter;
import com.example.kgtu.data.pojo.Deanery;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ScheduleFragment extends Fragment {

    private ScheduleViewModel scheduleViewModel;
    private RecyclerView recyclerViewSchedule;
    private ScheduleAdapter scheduleAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        scheduleViewModel =
                new ViewModelProvider(this).get(ScheduleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_schedules, container, false);

        scheduleViewModel.LoadDeanerys();
        recyclerViewSchedule = root.findViewById(R.id.recyclerViewSchedule);
        recyclerViewSchedule.setLayoutManager(new LinearLayoutManager(getContext()));
        scheduleAdapter = new ScheduleAdapter(getContext());
        scheduleAdapter.setDeaneryItems(new ArrayList<>());
        recyclerViewSchedule.setAdapter(scheduleAdapter);


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scheduleViewModel.getDeaneryLiveData().observe(getViewLifecycleOwner(), new Observer<List<Deanery>>() {
            @Override
            public void onChanged(List<Deanery> deanerys) {
                if(deanerys != null && deanerys.size() > 0) {
                    scheduleAdapter.setDeaneryItems(deanerys);
                } else {
                    Log.i("JSON", "ERROR");
                }
            }
        });

    }
}