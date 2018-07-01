package com.walkity.apps.journalapp.addeditdiary;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.walkity.apps.journalapp.R;
import com.walkity.apps.journalapp.data.DiaryEntry;
import com.walkity.apps.journalapp.databinding.ActivityDiaryFactoryBinding;

import java.util.Locale;

public class DiaryFactoryActivity extends AppCompatActivity implements AddEditContract.View {
    private ActivityDiaryFactoryBinding factoryBinding;
    private AddEditPresenter mPresenter;
    private DiaryEntry mEntry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        factoryBinding = DataBindingUtil.setContentView(this, R.layout.activity_diary_factory);
        assert(getSupportActionBar()!=null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter = ViewModelProviders.of(this).get(AddEditPresenter.class);
        mPresenter.getView(this);
        //load the ui...
        mPresenter.loadEntry(getIntent().getIntExtra("id", 0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_factory, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            showList();
        else
        if(item.getItemId() == R.id.factory_action_delete)
            mPresenter.deleteDraft();
        else
        if (item.getItemId() == R.id.factory_action_save)
        {
            //update the draft and then... save it
            mEntry.setTitle(factoryBinding.textInputLayout.getEditText().getText().toString());
            mEntry.setNarration(factoryBinding.textInputLayout2.getEditText().getText().toString());
            mPresenter.saveEntry(mEntry);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(AddEditContract.Presenter presenter) {

    }

    @Override
    public void showUI(@NonNull DiaryEntry entry) {
        mEntry = entry;
        java.text.SimpleDateFormat sdfdate = new java.text.SimpleDateFormat(getString(R.string.date_format), Locale.getDefault());
        java.text.SimpleDateFormat sdftime = new java.text.SimpleDateFormat(getString(R.string.time_format), Locale.getDefault());
        factoryBinding.textView.setText(sdfdate.format(mEntry.getDate()).toUpperCase());
        factoryBinding.time.setText(sdftime.format(mEntry.getDate()));
        factoryBinding.textInputLayout.getEditText().setText(mEntry.getTitle());
        factoryBinding.textInputLayout2.getEditText().setText(mEntry.getNarration());
    }

    @Override
    public void showEntrySaved() {
        Toast.makeText(this, R.string.entry_saved_text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDraftSaved() {
        Toast.makeText(this, R.string.draft_saved_text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showList()
    {
        onBackPressed();
    }

    @Override
    public void showErrorNoTitle() {
        Toast.makeText(this, R.string.error_no_title_text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorNoNarration() {
        Toast.makeText(this, R.string.error_no_narration_text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDraftDeleted() {
        Toast.makeText(this, getString(R.string.draft_deleted_text), Toast.LENGTH_SHORT)
                .show();
    }
}
