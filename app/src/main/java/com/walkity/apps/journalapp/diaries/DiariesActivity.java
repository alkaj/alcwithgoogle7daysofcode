package com.walkity.apps.journalapp.diaries;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;
import com.walkity.apps.journalapp.R;
import com.walkity.apps.journalapp.addeditdiary.DiaryFactoryActivity;
import com.walkity.apps.journalapp.data.DiaryEntry;
import com.walkity.apps.journalapp.databinding.ActivityMainBinding;
import com.walkity.apps.journalapp.databinding.ContentMainBinding;
import com.walkity.apps.journalapp.diarydetails.DiaryActivity;
import com.walkity.apps.journalapp.utils.DiariesListAdapter;

import java.util.List;

public class DiariesActivity extends AppCompatActivity implements DiariesContract.View{

    private DiariesContract.Presenter mPresenter;
    private ActivityMainBinding mainBinding;
    private ContentMainBinding contentBinding;
    private List<DiaryEntry> entries;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        contentBinding = DataBindingUtil.getBinding(findViewById(R.id.main_content));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.newEntry();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        mPresenter = ViewModelProviders.of(this).get(DiariesPresenter.class);
        mPresenter.initView(this);
        mPresenter.loadUserData();
        mPresenter.subscribe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        //logout
        if(id == R.id.action_logout)
        {
            mPresenter.logout();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(DiariesContract.Presenter presenter)
    {
        mPresenter = presenter;
    }

    @Override
    public void showSpinner() {
        //show the spinner
        contentBinding.diariesList.setVisibility(View.GONE);
        contentBinding.diariesProgress.setVisibility(View.VISIBLE);
        contentBinding.diariesArrow.setVisibility(View.GONE);
        contentBinding.textView2.setVisibility(View.GONE);
    }

    @Override
    public void showDiaries(LiveData<List<DiaryEntry>> diaries) {
        //show the list...
        Log.w("diaries", "Showing the list");
        contentBinding.diariesList.setVisibility(View.VISIBLE);
        contentBinding.diariesProgress.setVisibility(View.GONE);
        contentBinding.diariesArrow.setVisibility(View.GONE);
        contentBinding.textView2.setVisibility(View.GONE);

        //keep the list of diaries synced...
        diaries.observe(this, new Observer<List<DiaryEntry>>(){
            @Override
            public void onChanged(@Nullable List<DiaryEntry> diaryEntries) {
                contentBinding.diariesList.setHasFixedSize(true);
                //set layout manager
                contentBinding.diariesList.setLayoutManager(new LinearLayoutManager(DiariesActivity.this));
                //set the data to the adapter...
                DiariesListAdapter dla = new DiariesListAdapter(diaryEntries,
                        DiariesActivity.this);
                contentBinding.diariesList.setAdapter(dla);
            }
        });
    }

    @Override
    public void showEmptyList() {
        //show the empty list view
        contentBinding.diariesList.setVisibility(View.GONE);
        contentBinding.diariesProgress.setVisibility(View.GONE);
        contentBinding.diariesArrow.setVisibility(View.VISIBLE);
        contentBinding.textView2.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNewEntry() {
        //launch new entry activity...
        Intent newEntry = new Intent(DiariesActivity.this, DiaryFactoryActivity.class);
        startActivity(newEntry);
    }

    @Override
    public void showEntryDetails(int id) {
        //lauch details activity...
        Intent details = new Intent(this, DiaryActivity.class);
        details.putExtra("id", id);
        startActivity(details);
    }

    @Override
    public void showUserDetails(FirebaseUser user) {
        //update the title with the user informations
        StringBuilder title = new StringBuilder();
        title.append(user.getDisplayName()).append(
                getString(R.string.journal_text));
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void showLogin() {
        //show the login activity...
        onBackPressed();
    }
}
