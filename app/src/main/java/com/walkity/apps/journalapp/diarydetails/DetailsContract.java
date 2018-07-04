package com.walkity.apps.journalapp.diarydetails;

import android.support.annotation.NonNull;

import com.walkity.apps.journalapp.BasePresenter;
import com.walkity.apps.journalapp.BaseView;
import com.walkity.apps.journalapp.data.DiaryEntry;

/**
 * Created by alkaj on 7/2/18.
 * Contract Class for the details...
 */

public interface DetailsContract {
    interface Presenter extends BasePresenter
    {
        /**
         * Load the Diary corresponding to the provided id.
         * @param id sent from the intent action
         */
        void loadEntry(int id);

        /**
         * Recall the list page
         */
        void loadList();

        /**
         * get the view to be bound on.
         * @param v the view to bind this presenter on.
         */
        void getView(DetailsContract.View v);

        /**
         * Manage to get the sharer
         */
        void loadSharer();

        /**
         * Invoke the edition page with
         */
        void loadEdit();
    }

    interface View extends BaseView<Presenter>
    {
        /**
         * Inflate the provided Diary into the view
         * @param entry to be load in the view
         */
        void showEntry(@NonNull DiaryEntry entry);

        /**
         * Navigate to the list page
         */
        void showList();

        /**
         * show the sharer
         * @param entry the diary entry to be shared
         */
        void showShare(@NonNull DiaryEntry entry);

        /**
         * Navigate to the edition page
         * @param id of the actual entry to be edited
         */
        void showEdit(int id);
    }
}
