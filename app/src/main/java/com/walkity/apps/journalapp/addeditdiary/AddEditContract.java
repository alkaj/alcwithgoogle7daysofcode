package com.walkity.apps.journalapp.addeditdiary;

import android.support.annotation.NonNull;

import com.walkity.apps.journalapp.BasePresenter;
import com.walkity.apps.journalapp.BaseView;
import com.walkity.apps.journalapp.data.DiaryEntry;

/**
 * Created by alkaj on 7/1/18.
 * AddEdit contract
 */

public interface AddEditContract {
    interface Presenter extends BasePresenter
    {
        /**
         * Load entry to be edited
         * @param id of the entry to edit
         */
        void loadEntry(int id);

        /**
         * Attach the view instance to be used
         * @param view instance
         */
        void getView(@NonNull View view);

        /**
         * Save an entry
         * @param entry to save
         */
        void saveEntry(@NonNull DiaryEntry entry);

        /**
         * Trigger image picker
         */
        void pickImage();

    }
    interface View extends BaseView<Presenter>
    {
        /**
         * Initialize the UI
         * @param entry entry to be loaded.
         */
        void showUI(@NonNull DiaryEntry entry);

        /**
         * Show back the diaries list view
         */
        void showList();

        /**
         * Show error Diary should have a title
         */
        void showErrorNoTitle();

        /**
         * Show error Diary should have a narration
         */
        void showErrorNoNarration();

        /**
         * Show image picker view
         */
        void showPickImage();
    }
}
