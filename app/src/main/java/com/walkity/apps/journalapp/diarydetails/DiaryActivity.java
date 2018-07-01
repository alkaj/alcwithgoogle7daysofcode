package com.walkity.apps.journalapp.diarydetails;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.walkity.apps.journalapp.R;
import com.walkity.apps.journalapp.data.DiaryEntry;

public class DiaryActivity extends AppCompatActivity implements DetailsContract.View{

    private DetailsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dirary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter = ViewModelProviders.of(this).get(DetailsPresenter.class);
    }

    @Override
    public void setPresenter(DetailsContract.Presenter presenter) {

    }

    @Override
    public void showEntry(DiaryEntry entry) {

    }

    @Override
    public void showList() {

    }
}
