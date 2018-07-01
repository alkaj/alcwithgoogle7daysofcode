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
        void loadEntry(int id);
        void getView(@NonNull View view);
        void saveEntry(@NonNull DiaryEntry entry);
        void deleteDraft();

    }
    interface View extends BaseView<Presenter>
    {
        void showUI(@NonNull DiaryEntry entry);
        void showEntrySaved();
        void showDraftSaved();
        void showList();
        void showErrorNoTitle();
        void showErrorNoNarration();
        void showDraftDeleted();
    }
}
