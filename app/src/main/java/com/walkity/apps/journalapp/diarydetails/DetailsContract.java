package com.walkity.apps.journalapp.diarydetails;

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
        void loadEntry(DiaryEntry entry);
        void loadList();
    }

    interface View extends BaseView<Presenter>
    {
        void showEntry(DiaryEntry entry);
        void showList();
    }
}
