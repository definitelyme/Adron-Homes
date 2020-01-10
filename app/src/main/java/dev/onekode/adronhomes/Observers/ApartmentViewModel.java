package dev.onekode.adronhomes.Observers;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import dev.onekode.adronhomes.Models.Apartment;
import dev.onekode.adronhomes.RootInterface;

public class ApartmentViewModel extends AndroidViewModel implements EventListener<QuerySnapshot>, RootInterface {
    private ArrayList<Apartment> apartments;
    private FirebaseFirestore database;
    private MutableLiveData<ArrayList<Apartment>> mutableApartments = new MutableLiveData<>();

    public ApartmentViewModel(@NonNull Application application) {
        super(application);
        database = FirebaseFirestore.getInstance();
        apartments = new ArrayList<>();
    }

    public LiveData<ArrayList<Apartment>> all() {
        if (mutableApartments.getValue() == null)
            database.collection(Apartment.getNode(Apartment.class)).addSnapshotListener(this);
        return mutableApartments;
    }

    private ArrayList<Apartment> addApartments(@NonNull QuerySnapshot snapshots) {
        for (QueryDocumentSnapshot documentSnapshot : snapshots)
            apartments.add(documentSnapshot.toObject(Apartment.class));
        return apartments;
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
        if (e != null) {
            Log.i(TAG, "onEvent: Firestore Exception => " + e.getLocalizedMessage(), e);
            return;
        }

        if (snapshots != null && !snapshots.isEmpty())
            mutableApartments.postValue(addApartments(snapshots));
    }
}
