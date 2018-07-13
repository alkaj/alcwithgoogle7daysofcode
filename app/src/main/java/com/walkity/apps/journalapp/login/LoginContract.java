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
        /**
         * Triggers the login
         * @param request the request nomber of the forresult call on the auth activity.
         */
        void showLogin(int request);

        /**
         * show the logged user informations
         */
        void showLogged();

        /**
         * Close the Login activity and the app.
         */
        void close();
    }


    interface Presenter extends BasePresenter {

        /**
         * show the login view
         */
        void login();

        /**
         * show the authentication results view..
         * @param request the auth request code
         * @param result the auth result code.
         */
        void loadResults(int request, int result);

        /**
         * Send this view to the attached presenters instance.
         * @param view the view instance to be attached
         */
        void getView(@NonNull LoginContract.View view);
    }
}
