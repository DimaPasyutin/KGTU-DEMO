package com.example.kgtu;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.kgtu.data.pojo.DaysBeforeExams;
import com.example.kgtu.firebase.MyFirebaseDatabase;
import com.example.kgtu.ui.timetable.TimetableFragment;
import com.example.kgtu.ui.timetable.TimetableViewModel;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    TextView textViewDate;
    MyFirebaseDatabase myFirebaseDatabase;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_timetable)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //Firestore
        View view = navigationView.getHeaderView(0);
        textViewDate = view.findViewById(R.id.textViewDayBeforeExams);
        myFirebaseDatabase =
                        new ViewModelProvider(this).get(MyFirebaseDatabase.class);
        myFirebaseDatabase.LoadDateAndWish();
        myFirebaseDatabase.getDaysBeforeExamsMutableLiveData().observe(this, new Observer<DaysBeforeExams>() {
            @Override
            public void onChanged(DaysBeforeExams daysBeforeExams) {
                if (daysBeforeExams != null) {
                    String wish = String.format("%s или %s дней", daysBeforeExams.getWish(), daysBeforeExams.getTime().getSeconds() / 86_400 );
                    textViewDate.setText(wish);
                    Log.i("Date", ""+daysBeforeExams.getTime().getSeconds() / 86_400);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (TimetableFragment.goBack()) {
            // надо сделать нормально
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}