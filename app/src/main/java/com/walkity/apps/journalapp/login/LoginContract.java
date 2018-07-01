package com.walkity.apps.journalapp.login;

import android.support.annotation.NonNull;

import com.walkity.apps.journalapp.BasePresenter;
import com.walkity.apps.journalapp.BaseView;

/**
 * Created by alkaj on 7/1/18.
 * LoginContract between presenter and view
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void showLogin(int request);
        void showLogged();
        void close();
    }
    interface Presenter extends BasePresenter {
        void login();
        void loadResults(int request, int result);
        void getView(@NonNull LoginContract.View view);
    }
}
