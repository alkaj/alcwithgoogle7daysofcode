package com.walkity.apps.journalapp.login;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by alkaj on 7/1/18.
 * Login presenter
 */

public class LoginPresenter extends AndroidViewModel implements LoginContract.Presenter{

    private LoginContract.View mView;
    private final String TAG = getClass().getName();
    private FirebaseAuth mAuth;
    private boolean dejavu = false;

    private final int RC_SIGN_IN = 1132;

    public LoginPresenter(@NonNull Application application) {
        super(application);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {
    }

    @Override
    public void login() {
        mView.showLogin(RC_SIGN_IN);
    }

    @Override
    public void loadResults(int request, int result) {
        if(request == RC_SIGN_IN)
        {
            Log.w(TAG, "Got a response");
            if (Activity.RESULT_OK == result)
                mView.showLogged();
            Log.w(TAG, "Done with the checking");
        }
    }

    @Override
    public void getView(@NonNull LoginContract.View view) {
        mView = view;
        //go straight to the list if logged
        if(mAuth.getCurrentUser() != null)
        {
            if(!dejavu)
                mView.showLogged();
            else mView.close();
            dejavu = true;
        }
    }
}
