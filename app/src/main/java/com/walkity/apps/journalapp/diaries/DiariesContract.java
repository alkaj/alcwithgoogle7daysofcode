package com.walkity.apps.journalapp.diaries;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.walkity.apps.journalapp.BasePresenter;
import com.walkity.apps.journalapp.BaseView;
import com.walkity.apps.journalapp.data.DiaryEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alkaj on 7/1/18.
 * Contract interface
 */

public interface DiariesContract {
    interface View extends BaseView<Presenter>
    {
        void showSpinner();
        void showDiaries(LiveData<List<DiaryEntry>> entries);
        void showEmptyList();
        void showNewEntry();
        void showEntryDetails(int id);
        void showUserDetails(FirebaseUser user);
        void showLogin();
    }

    interface Presenter extends BasePresenter
    {
        void loadDiaries();
        void initView(@NonNull View view);
        void loadDiary(@NonNull DiaryEntry entry);
        void loadUserData();
        void loadUi();
        void logout();
        void newEntry();
    }
}
