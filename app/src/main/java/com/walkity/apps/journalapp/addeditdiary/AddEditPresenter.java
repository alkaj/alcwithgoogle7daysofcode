package com.walkity.apps.journalapp.addeditdiary;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;

import com.walkity.apps.journalapp.data.AppDatabase;
import com.walkity.apps.journalapp.data.DiaryEntry;
import com.walkity.apps.journalapp.utils.AppExecutors;

import java.util.Date;

/**
 * Created by alkaj on 7/1/18.
 * Add and Edit Presenter
 */

public class AddEditPresenter extends AndroidViewModel implements AddEditContract.Presenter{

    private AddEditContract.View mView;
    private DiaryEntry mDraft;
    private AppDatabase database;
    private boolean hasUpdate = false;

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
    public void loadEntry(final int id) {
        //create a new draft if no valid entry provided
        if(id == 0) {
            mDraft = new DiaryEntry("", "", new Date(), "");
            mView.showUI(mDraft);
        }
        else
        {
            final Handler mainThreadHandler = new Handler();
            new AppExecutors().execute(new Runnable() {
                @Override
                public void run() {
                    mDraft = database.dao().getNonReactiveEntry(id);
                    mainThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mView.showUI(mDraft);
                        }
                    });
                }
            });
            //this is an update...
            hasUpdate = true;
        }
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
            new AppExecutors().execute(new Runnable() {
                @Override
                public void run() {
                    if(hasUpdate)
                        database.dao().updateEntry(mDraft);
                    else
                        database.dao().addEntry(mDraft);
                }
            });
            mView.showList();
            hasUpdate = false;
        }
    }

    @Override
    public void pickImage() {
        //pick an image...
        mView.showPickImage();
    }

}
