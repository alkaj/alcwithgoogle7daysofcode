package com.walkity.apps.journalapp.addeditdiary;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.walkity.apps.journalapp.R;
import com.walkity.apps.journalapp.databinding.ActivityDiaryFactoryBinding;

import java.util.Date;
import java.util.Locale;

public class DiaryFactoryActivity extends AppCompatActivity {
    private ActivityDiaryFactoryBinding factoryBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        factoryBinding = DataBindingUtil.setContentView(this, R.layout.activity_diary_factory);
        fillUi();
        assert(getSupportActionBar()!=null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_factory, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            onBackPressed();
        else
        if(item.getItemId() == R.id.factory_action_delete)
            delete();
        else
        if (item.getItemId() == R.id.factory_action_save)
            save();
        return super.onOptionsItemSelected(item);
    }

    /*
      Save a new diary
     */
    public void save()
    {
        Toast.makeText(this, getString(R.string.diary_saved_text), Toast.LENGTH_LONG).show();
        onBackPressed();
    }

    /*
    Delete a drafted diary
     */
    public void delete()
    {
        Toast.makeText(this, getString(R.string.draft_deleted_text), Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    /*
    Fill the ui with data
     */
    public void fillUi()
    {
        Date d = new Date();
        java.text.SimpleDateFormat sdfdate = new java.text.SimpleDateFormat(getString(R.string.date_format), Locale.getDefault());
        java.text.SimpleDateFormat sdftime = new java.text.SimpleDateFormat(getString(R.string.time_format), Locale.getDefault());
        factoryBinding.textView.setText(sdfdate.format(d).toUpperCase());
        factoryBinding.time.setText(sdftime.format(d));
    }
}
