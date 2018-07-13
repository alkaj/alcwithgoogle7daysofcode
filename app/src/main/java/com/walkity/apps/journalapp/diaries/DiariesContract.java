package com.walkity.apps.journalapp.diaries;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.walkity.apps.journalapp.BasePresenter;
import com.walkity.apps.journalapp.BaseView;
import com.walkity.apps.journalapp.data.DiaryEntry;

import java.util.List;

/**
 * Created by alkaj on 7/1/18.
 * Contract interface
 */

public interface DiariesContract {
    interface View extends BaseView<Presenter>
    {
        /**
         * Trigger the loading spinner
         */
        void showSpinner();

        /**
         * Get the diaries from the db...
         * @param entries from the db.
         */
        void showDiaries(LiveData<List<DiaryEntry>> entries);

        /**
         * Trigger empty list visual
         */
        void showEmptyList();

        /**
         * Trigger new entry view
         * @param id of the view to edit
         */
        void showNewEntry(int id);

        /**
         * Trigger entry details view
         * @param id of the view to detail
         */
        void showEntryDetails(int id);

        /**
         * Send user data in the view
         * @param user authenticated
         */
        void showUserDetails(FirebaseUser user);

        /**
         * Trigger show login
         */
        void showLogin();

        /**
         * Trigger delete confirmation view
         * @param entry to be deleted
         */
        void showDeleteConfirmation(@NonNull DiaryEntry entry);
    }

    interface Presenter extends BasePresenter
    {
        /**
         * Show diaries list
         */
        void loadDiaries();

        /**
         * Attach this view to a controller
         * @param view to be attached
         */
        void initView(@NonNull View view);

        /**
         * Show diary details view
         * @param entry to be shown
         */
        void loadDiary(@NonNull DiaryEntry entry);

        /**
         * Show user data view
         */
        void loadUserData();

        /**
         * Show logout
         */
        void logout();

        /**
         * Show new entry view
         */
        void newEntry();

        /**
         * Show delete entry view
         * @param entry to be deleted
         */
        void deleteEntry(@NonNull DiaryEntry entry);

        /**
         * Show update entry view
         * @param entry to be updated
         */
        void updateEntry(@NonNull DiaryEntry entry);

        /**
         * Confirm entry deletion
         * @param entry deleted
         */
        void confirmDelete(@NonNull DiaryEntry entry);
    }
}
