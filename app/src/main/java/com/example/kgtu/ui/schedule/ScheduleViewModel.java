package com.example.kgtu.ui.schedule;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kgtu.data.pojo.Deanery;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ScheduleViewModel extends AndroidViewModel {

    private FirebaseFirestore dataBase;
    private MutableLiveData<List<Deanery>> deaneryLiveData;

    public ScheduleViewModel(@NonNull Application application) {
        super(application);
        dataBase = FirebaseFirestore.getInstance();
        deaneryLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Deanery>> getDeaneryLiveData() {
        return deaneryLiveData;
    }

    public void LoadDeanerys() {
        dataSourceForDeanery()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull String deanerys) {
                        Gson gson = new Gson();
                        Type collectionType = new TypeToken<Collection<Deanery>>(){}.getType();
                        List<Deanery> deaneryExample = gson.fromJson(deanerys, collectionType);
                        deaneryLiveData.postValue(deaneryExample);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("JSON", "Error" + e.getMessage());
                    }
                });
    }

    private Single<String> dataSourceForDeanery() {

        return Single.create(emitter -> {

            DocumentReference daysBeforeExams = dataBase.collection("schedule").document("deanerys");

            daysBeforeExams.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        emitter.onSuccess(documentSnapshot.getString("deanerys"));
                    }
                }
            });

        });

    }
}