package com.walkity.apps.journalapp.login;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.walkity.apps.journalapp.R;
import com.walkity.apps.journalapp.diaries.DiariesActivity;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    List<AuthUI.IdpConfig> providers;
    private LoginPresenter  mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set back the theme to the normal...
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_login);
        //build the google signin provider...
        providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        findViewById(R.id.sign_in_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.login();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter = ViewModelProviders.of(this).get(LoginPresenter.class);
        mPresenter.getView(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.loadResults(requestCode, resultCode);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
    }

    @Override
    public void showLogin(int request) {
        //now call it
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(), request
        );
    }

    @Override
    public void showLogged() {
        Intent loggedIntent = new Intent(this, DiariesActivity.class);
        startActivity(loggedIntent);
    }

    @Override
    public void close() {
        onBackPressed();
    }
}
