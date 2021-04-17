package com.example.kgtu.ui.schedule;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kgtu.R;
import com.squareup.picasso.Picasso;

public class ScheduleInfoFragment extends Fragment {

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_schedule_info, container, false);
        ImageView ImageView = root.findViewById(R.id.photo_Dekana);
        TextView nameOfDeanery = root.findViewById(R.id.nameOfDeanery);
        TextView name = root.findViewById(R.id.name_dekana);
        TextView post = root.findViewById(R.id.post_dekana);
        TextView workplace = root.findViewById(R.id.work_space);
        TextView address = root.findViewById(R.id.adress);
        TextView phone = root.findViewById(R.id.phone);
        TextView mail = root.findViewById(R.id.mail);
        TextView schedule = root.findViewById(R.id.schedule);

        Bundle bundle = getArguments();

        if(bundle != null) {
            Picasso.get().load(bundle.getString("photo")).into(ImageView);
            name.setText(bundle.getString("name"));
            post.setText("Должность: " + bundle.getString("post"));
            workplace.setText("Рабочее место: " + bundle.getString("workplace"));
            address.setText("Адресс: " + bundle.getString("address"));
            phone.setText("Номер телефона: " + bundle.getString("phone"));
            mail.setText("Почта: " + bundle.getString("mail"));
            schedule.setText("График работы: " + bundle.getString("schedule"));
        }

        return root;
    }
}