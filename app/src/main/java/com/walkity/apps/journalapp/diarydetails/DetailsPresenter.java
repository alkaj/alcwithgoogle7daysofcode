package com.walkity.apps.journalapp.diarydetails;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.persistence.room.RoomDatabase;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.walkity.apps.journalapp.data.AppDatabase;
import com.walkity.apps.journalapp.data.DiaryEntry;
import com.walkity.apps.journalapp.utils.AppExecutors;

/**
 * Created by alkaj on 7/2/18.
 * ViewDetails Presenter
 */

public class DetailsPresenter extends AndroidViewModel implements DetailsContract.Presenter{

    private DiaryEntry mEntry;
    private AppDatabase mDataBase;
    private DetailsContract.View mView;
    public DetailsPresenter(@NonNull Application application) {
        super(application);
        mDataBase = AppDatabase.getInstance(this.getApplication());
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void loadEntry(final int id) {
        if(id == 0) mView.showList();
        //the handler for the ui ops...
        final Handler uiHandler = new Handler();
        //get the entry
        new AppExecutors().execute(new Runnable() {
            @Override
            public void run() {
                mEntry = mDataBase.dao().getNonReactiveEntry(id);
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                       //load the view...
                        mView.showEntry(mEntry);
                    }
                });
            }
        });


    }

    @Override
    public void loadList() {

    }

    @Override
    public void getView(DetailsContract.View v) {
        mView = v;
    }
}
