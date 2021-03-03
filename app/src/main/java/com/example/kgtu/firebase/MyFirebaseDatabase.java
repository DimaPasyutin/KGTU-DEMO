package com.example.kgtu.firebase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.kgtu.data.pojo.DaysBeforeExams;
import com.example.kgtu.data.pojo.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MyFirebaseDatabase extends AndroidViewModel {

     private FirebaseFirestore dataBase;
    private MutableLiveData<DaysBeforeExams> daysBeforeExamsMutableLiveData;

    public MyFirebaseDatabase(@NonNull Application application) {
        super(application);
        dataBase = FirebaseFirestore.getInstance();
        daysBeforeExamsMutableLiveData = new MutableLiveData<>(new DaysBeforeExams(70, "Don't worry"));
    }

    public MutableLiveData<DaysBeforeExams> getDaysBeforeExamsMutableLiveData() {
        return daysBeforeExamsMutableLiveData;
    }

    public void LoadDate() {
        dataSource()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<DaysBeforeExams>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        d.dispose();
                    }

                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull DaysBeforeExams daysBeforeExams) {
                        Log.i("YES", daysBeforeExams.getWish());
//                        daysBeforeExamsMutableLiveData.setValue(daysBeforeExams);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("LoadDate", "Error" + e.getMessage());
                    }
                });
    }

    private Single<DaysBeforeExams> dataSource() {
        DaysBeforeExams days = new DaysBeforeExams();

        return Single.create(emitter -> {

            dataBase = FirebaseFirestore.getInstance();
            DocumentReference daysBeforeExams = dataBase.collection("newClass").document("firstTry");

            daysBeforeExams.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot != null) {
                        emitter.onSuccess(Objects.requireNonNull(documentSnapshot.toObject(DaysBeforeExams.class)));
                    }
                }
            });

        });
    }

    public void setInfoInDatabase() {

        DaysBeforeExams daysBeforeExams = new DaysBeforeExams(69, "Времени еще много");
        dataBase.collection("newClass").document("firstTry").set(daysBeforeExams);

    }

}
