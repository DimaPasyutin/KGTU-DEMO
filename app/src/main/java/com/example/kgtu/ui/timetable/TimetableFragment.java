package com.example.kgtu.ui.timetable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.kgtu.R;
import com.example.kgtu.ui.navigator.NavigatorViewModel;

public class TimetableFragment extends Fragment {

    private NavigatorViewModel navigatorViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_timetable, container, false);
        TextView textView = root.findViewById(R.id.text_time);
        textView.setText("Расписание");
        return root;
    }

}
