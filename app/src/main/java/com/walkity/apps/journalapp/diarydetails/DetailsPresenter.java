package com.walkity.apps.journalapp.diarydetails;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.walkity.apps.journalapp.data.DiaryEntry;

/**
 * Created by alkaj on 7/2/18.
 * ViewDetails Presenter
 */

public class DetailsPresenter extends AndroidViewModel implements DetailsContract.Presenter{

    public DetailsPresenter(@NonNull Application application) {
        super(application);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void loadEntry(DiaryEntry entry) {

    }

    @Override
    public void loadList() {

    }
}
