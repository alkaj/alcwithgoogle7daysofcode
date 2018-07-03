package com.walkity.apps.journalapp.diarydetails;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.walkity.apps.journalapp.R;
import com.walkity.apps.journalapp.data.DiaryEntry;
import com.walkity.apps.journalapp.databinding.ActivityDiraryBinding;
import com.walkity.apps.journalapp.databinding.ContentDiraryBinding;

public class DiaryActivity extends AppCompatActivity implements DetailsContract.View{

    private DetailsPresenter mPresenter;
    private ContentDiraryBinding mBinding;
    private ActivityDiraryBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_dirary);
        mBinding = DataBindingUtil.getBinding(findViewById(R.id.content_diary));
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
        mPresenter.getView(this);
        mPresenter.loadEntry(getIntent().getIntExtra("id", 0));
    }

    @Override
    public void setPresenter(DetailsContract.Presenter presenter) {

    }

    @Override
    public void showEntry(DiaryEntry entry) {
        if(mBinding == null)
            Log.w("test", entry.getTitle());
        mBinding.setItem(entry);
    }

    @Override
    public void showList() {
        onBackPressed();
    }
}
