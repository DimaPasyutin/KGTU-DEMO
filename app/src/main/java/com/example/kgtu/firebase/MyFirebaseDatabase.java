package com.example.kgtu.firebase;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.kgtu.data.pojo.DaysBeforeExams;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MyFirebaseDatabase extends AndroidViewModel {

    private FirebaseFirestore dataBase;
    private MutableLiveData<DaysBeforeExams> daysBeforeExamsMutableLiveData;
    Timestamp timestamp = new Timestamp(1615504259, 0);

    public MyFirebaseDatabase(@NonNull Application application) {
        super(application);
        dataBase = FirebaseFirestore.getInstance();
        daysBeforeExamsMutableLiveData = new MutableLiveData<>(new DaysBeforeExams(timestamp, "All will be okey"));
    }

    public MutableLiveData<DaysBeforeExams> getDaysBeforeExamsMutableLiveData() {
        return daysBeforeExamsMutableLiveData;
    }

    public void LoadDateAndWish() {
        dataSourceForDateAndWish()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<DaysBeforeExams>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
//                        За что отвечает этот метод?
//                        d.dispose();
                    }

                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull DaysBeforeExams daysBeforeExams) {
                        daysBeforeExamsMutableLiveData.setValue(daysBeforeExams);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.i("LoadData", "Error" + e.getMessage());
                    }
                });
    }

    private Single<DaysBeforeExams> dataSourceForDateAndWish() {

        return Single.create(emitter -> {

            DaysBeforeExams days = new DaysBeforeExams();
            DocumentReference daysBeforeExams = dataBase.collection("newClass").document("firstTry");

            daysBeforeExams.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        days.setWish(documentSnapshot.toObject(DaysBeforeExams.class).getWish());
                        days.setTime(documentSnapshot.getTimestamp("time"));

                        emitter.onSuccess(days);
                    }
                }
            });

        });


    }

    public void setInfoInDatabase() {
        DaysBeforeExams daysBeforeExams = new DaysBeforeExams(timestamp, "Времени еще много");
        dataBase.collection("newClass").document("firstTry").set(daysBeforeExams);

    }

}
