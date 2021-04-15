package com.example.kgtu.ui.navigator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.kgtu.R;
import com.example.kgtu.data.pojo.Room;
import com.example.kgtu.ui.timetable.TimetableViewModel;
import com.google.firebase.database.collection.LLRBNode;

public class NavigatorFragment extends Fragment {

    private NavigatorViewModel navigatorViewModel;
    private Button findRoom;
    private ImageView map;
    private EditText secondRoom;
    private TextView hint;
    private boolean isExist = true;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        navigatorViewModel =
                new ViewModelProvider(this).get(NavigatorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_navigation, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findRoom = view.findViewById(R.id.button);
        map = view.findViewById(R.id.map);
        secondRoom = view.findViewById(R.id.required_room);
        hint = view.findViewById(R.id.hint);

        findRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(secondRoom.getText() != null && String.valueOf(secondRoom.getText()).length() > 0) {
                    navigatorViewModel.LoadInfoRoom(secondRoom.getText().toString());
                } else {
                    isExist = false;
                }

                navigatorViewModel.getInfoRoom().observe(getViewLifecycleOwner(), new Observer<Room>() {
                    @Override
                    public void onChanged(Room room) {
                        if (room.equals(new Room("1", "#FF0000", "1"))) {

                            hint.setVisibility(View.GONE);
                            map.setVisibility(View.GONE);
                            isExist = false;

                        } else {
                            hint.setText(String.format("Кабинет, который вы ищите, отмечен %s цветом и расположен на %s этаже",
                                    room.getColor(), room.getFloor()));
                            hint.setTextColor(Color.parseColor(room.getColorCod()));
                            hint.setVisibility(View.VISIBLE);
                            map.setVisibility(View.VISIBLE);
                            isExist = true;

                            switch (Integer.decode(String.valueOf(room.getFloor()))) {
                                case 3:
                                    map.setImageResource(R.drawable.map);
                                    break;
                            }

                            // Скрываем клавиатуру
                            if(secondRoom.requestFocus()) {
                                InputMethodManager inputMethodManager =
                                        (InputMethodManager) getContext().getSystemService(
                                                Activity.INPUT_METHOD_SERVICE);
                                inputMethodManager.hideSoftInputFromWindow(
                                        getActivity().getCurrentFocus().getWindowToken(), 0);
                            }
                        }
                    }
                });

                if (!isExist) {
                    Toast.makeText(getContext(), "Укажите существующий номер кабнета", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}