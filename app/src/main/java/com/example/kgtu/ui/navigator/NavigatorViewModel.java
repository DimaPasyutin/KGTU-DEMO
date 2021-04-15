package com.example.kgtu.ui.navigator;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.kgtu.data.pojo.Room;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NavigatorViewModel extends AndroidViewModel {

    private FirebaseFirestore dataBase;
    private MutableLiveData<Room> infoRoom;

    public NavigatorViewModel(@NonNull Application application) {
        super(application);
        dataBase = FirebaseFirestore.getInstance();
        infoRoom = new MutableLiveData<>();
    }

    public MutableLiveData<Room> getInfoRoom() {
        return infoRoom;
    }

    public void LoadInfoRoom(String numberOfRoom) {
        dataSourceForLink(numberOfRoom)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Room>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull Room room) {
                        infoRoom.postValue(room);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Toast.makeText(getApplication().getApplicationContext(), "Включите интернет", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private Single<Room> dataSourceForLink(String numberOfRoom) {

        return Single.create(emitter -> {

            DocumentReference daysBeforeExams = dataBase.collection("map").document(numberOfRoom);

            daysBeforeExams.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        Room room = new Room(documentSnapshot.getString("color"), documentSnapshot.getString("cod"),
                                documentSnapshot.getString("floor"));
                        emitter.onSuccess(room);
                    } else {
                        emitter.onSuccess(new Room("1", "#FF0000", "1"));
                    }
                }
            });

        });

    }
}