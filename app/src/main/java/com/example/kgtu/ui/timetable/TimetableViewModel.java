package com.example.kgtu.ui.timetable;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TimetableViewModel extends AndroidViewModel {

    private FirebaseFirestore dataBase;
    private MutableLiveData<String> linkLiveData;

    public TimetableViewModel(@NonNull Application application) {
        super(application);
        dataBase = FirebaseFirestore.getInstance();
        linkLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<String> getLinkLiveData() {
        return linkLiveData;
    }

    public void setLinkLiveData(MutableLiveData<String> linkLiveData) {
        this.linkLiveData = linkLiveData;
    }

    public void LoadLink() {
        dataSourceForLink()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull String link) {
                        linkLiveData.setValue(link);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("LoadData", "Error" + e.getMessage());
                    }
                });
    }

    private Single<String> dataSourceForLink() {

        return Single.create(emitter -> {

            DocumentReference daysBeforeExams = dataBase.collection("LINKS").document("linkForTimetable");

            daysBeforeExams.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        emitter.onSuccess(documentSnapshot.getString("link"));
                    }
                }
            });

        });

    }



}
