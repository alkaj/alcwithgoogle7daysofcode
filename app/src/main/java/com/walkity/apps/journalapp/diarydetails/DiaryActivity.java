package com.walkity.apps.journalapp.diarydetails;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.walkity.apps.journalapp.R;
import com.walkity.apps.journalapp.addeditdiary.DiaryFactoryActivity;
import com.walkity.apps.journalapp.data.DiaryEntry;
import com.walkity.apps.journalapp.databinding.ActivityDiraryBinding;
import com.walkity.apps.journalapp.databinding.ContentDiraryBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.content.Intent.ACTION_SEND;

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
                mPresenter.loadEdit();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case android.R.id.home:
                mPresenter.loadList();
                break;
            case R.id.action_details_share:
                mPresenter.loadSharer();
                break;
        }
        return super.onOptionsItemSelected(item);
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
        //todo: bind this view to a presenter
    }

    @Override
    public void showEntry(@NonNull DiaryEntry entry) {
        //todo: load the entry values into the view
        mBinding.setItem(entry);
        //load the correct image
        mBinding.image.setImageBitmap(path2image(entry.getImages()));
    }

    @Override
    public void showList() {
        //todo: show navigate to the list page
        onBackPressed();
    }

    @Override
    public void showShare(@NonNull DiaryEntry entry) {
        //todo: show the sharer

        //build the page...
        StringBuilder htmltext = new StringBuilder();
        //add the title
        htmltext.append("<h1>").append(entry.getTitle()).append("</h1>");
        //add the body content
        htmltext.append("<p>").append(entry.getNarration()).append("</p>");
        //add a footer
        htmltext.append("<p><small>").append(getString(R.string.created_on_text)).append(entry.dateFormatted())
                .append(getString(R.string.created_at_text)).append(entry.timeFormatted()).append("</small></p>");

        //create the intent...
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setChooserTitle(getString(R.string.share_chooser_title))
                .setHtmlText(htmltext.toString())
                .setType("text/html")
                .getIntent();
        //show the chooser
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_chooser_title)));
    }

    @Override
    public void showEdit(int id) {
        //todo: lauch an intent to the edition page
        Intent editIntent = new Intent(this, DiaryFactoryActivity.class);
        editIntent.putExtra("id", id);
        startActivity(editIntent);
    }

    private Bitmap path2image(String path)
    {
        if (null == path || path.equals(""))
            return null;
        Uri imageUri = Uri.parse(path);
        //get the permission to read that image...
        imageUri = FileProvider.getUriForFile(this, "com.walkity.apps.journalapp",
                new File(imageUri.toString()));
        try {
            InputStream is = getContentResolver().openInputStream(imageUri);
            return BitmapFactory.decodeStream(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
