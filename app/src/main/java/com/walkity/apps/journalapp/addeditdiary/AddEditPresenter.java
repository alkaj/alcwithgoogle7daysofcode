package com.walkity.apps.journalapp.addeditdiary;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.walkity.apps.journalapp.data.AppDatabase;
import com.walkity.apps.journalapp.data.DiaryEntry;
import com.walkity.apps.journalapp.utils.AppExecutor;

import java.util.Date;

/**
 * Created by alkaj on 7/1/18.
 * Add and Edit Presenter
 */

public class AddEditPresenter extends AndroidViewModel implements AddEditContract.Presenter{

    private AddEditContract.View mView;
    private DiaryEntry mDraft;
    private AppDatabase database;

    public AddEditPresenter(@NonNull Application application) {
        super(application);
        database = AppDatabase.getInstance(this.getApplication());
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void loadEntry(int id) {
        //create a new draft if no valid entry provided
        if(id == 0)
            mDraft = new DiaryEntry("", "", new Date(), "");
        else mDraft = database.dao().getNonReactiveEntry(id);
        mView.showUI(mDraft);
    }

    @Override
    public void getView(@NonNull AddEditContract.View view) {
        this.mView = view;
    }

    @Override
    public void saveEntry(@NonNull DiaryEntry entry)
    {
        mDraft = entry;
        //check if there is
        //a title
        if(mDraft.getTitle().equals(""))
            mView.showErrorNoTitle();
        //a narration
        else if (mDraft.getNarration().equals(""))
            mView.showErrorNoNarration();
        else
        {
            new AppExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    database.dao().addEntry(mDraft);
                }
            });
            mView.showEntrySaved();
            mView.showList();
        }
    }

    @Override
    public void deleteDraft() {
        mDraft = new DiaryEntry("", "", new Date(), "");
        mView.showList();
        mView.showDraftDeleted();
    }
}
