package com.example.kgtu;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kgtu.data.pojo.DaysBeforeExams;
import com.example.kgtu.data.pojo.Example;
import com.example.kgtu.firebase.MyFirebaseDatabase;
import com.example.kgtu.ui.timetable.TimetableFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.gson.internal.bind.DateTypeAdapter;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

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

        //Firebase
        textViewDate = findViewById(R.id.textViewDayBeforeExams);
        myFirebaseDatabase = new MyFirebaseDatabase(getApplication());
//        myFirebaseDatabase.LoadDate();
//        textViewDate.setText(daysBeforeExams.getWish());
//        myFirebaseDatabase.getDaysBeforeExamsMutableLiveData().observe(this, new Observer<DaysBeforeExams>() {
//            @Override
//            public void onChanged(DaysBeforeExams daysBeforeExams) {
//                if(daysBeforeExams != null) {
//                    textViewDate.setText(daysBeforeExams.getWish());
//                }
//            }
//        });
    }

    @Override
    public void onBackPressed() {

        if (TimetableFragment.goBack())
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT);
        else
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