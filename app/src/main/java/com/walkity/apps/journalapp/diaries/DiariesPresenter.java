package com.walkity.apps.journalapp.diaries;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.walkity.apps.journalapp.data.AppDatabase;
import com.walkity.apps.journalapp.data.DiaryEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alkaj on 7/1/18.
 * The presenter class
 */

public class DiariesPresenter extends AndroidViewModel implements DiariesContract.Presenter{

    //Reference to the contract view we are manipulating.
    private DiariesContract.View mView;
    //our database
    AppDatabase database;
    //Firebase auth
    FirebaseAuth mAuth;
    //Live Diaries list..
    LiveData<List<DiaryEntry>> diaries;


    public DiariesPresenter(@NonNull Application app)
    {
        super(app);
        //let's get an instance of our db...
        database = AppDatabase.getInstance(this.getApplication());
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void subscribe() {
        loadDiaries();
    }

    @Override
    public void initView(@NonNull DiariesContract.View view)
    {
       mView = view;
    }

    @Override
    public void unsubscribe() {
    }

    @Override
    public void loadDiaries() {
        diaries = database.dao().getEntries();
        mView.showDiaries(diaries);
    }

    @Override
    public void loadDiary(@NonNull DiaryEntry entry) {
    }

    @Override
    public void loadUserData() {
        mView.showUserDetails(mAuth.getCurrentUser());
    }

    @Override
    public void loadUi() {

    }

    @Override
    public void logout() {
        mAuth.signOut();
        mView.showLogin();
    }

    @Override
    public void newEntry() {
        mView.showNewEntry();
    }
}
