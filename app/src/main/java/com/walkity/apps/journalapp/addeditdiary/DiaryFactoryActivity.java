package com.walkity.apps.journalapp.addeditdiary;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.walkity.apps.journalapp.R;
import com.walkity.apps.journalapp.data.DiaryEntry;
import com.walkity.apps.journalapp.databinding.ActivityDiaryFactoryBinding;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Locale;

public class DiaryFactoryActivity extends AppCompatActivity implements AddEditContract.View {
    private ActivityDiaryFactoryBinding factoryBinding;
    private AddEditPresenter mPresenter;
    private DiaryEntry mEntry;
    private final int GET_IMAGE_REQUEST = 1234;

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
        if(item.getItemId() == R.id.factory_action_photo)
            mPresenter.pickImage();
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
    public void showPickImage() {
        //show the choser dialog...
        new AlertDialog.Builder(this)
                .setTitle(R.string.choose_image_source_text)
                .setNeutralButton(R.string.camera_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        capture();
                    }
                })
                .setPositiveButton(R.string.storage_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        existing();
                    }
                })
                .show();
    }

    /**
     * launches the image picker against existing images
     */
    private void existing()
    {
        Intent pickimageIntent = new Intent();
        pickimageIntent.setType("image/*");
        pickimageIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pickimageIntent, GET_IMAGE_REQUEST);

    }

    /**
     * launches image picker against the camera
     */
    private void capture()
    {
        Intent pickimageIntent = new Intent();
        //pickimageIntent.setType("image/*");
        pickimageIntent.setAction(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(pickimageIntent, GET_IMAGE_REQUEST);

    }

    /**
     * loads the provided image into the preview frame...
     * @param image to be loaded into the preview frame...
     */
    private void loadImage(Bitmap image)
    {
       factoryBinding.imagePreview.setImageBitmap(image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GET_IMAGE_REQUEST && resultCode == RESULT_OK && null != data)
        {
            Bitmap image = null;
            if(data.getExtras() != null && data.hasExtra("data"))
            {
                //This is an image from the camera...

                //Get the image...
                image = (Bitmap) data.getExtras().get("data");
            }
            else if(data.getData() != null)
            {
                //This is an existing image...

                //Get the image uri sent as data...
                Uri imageUri = data.getData();
                //Load it into an input stream
                try {
                    InputStream imageIS = getContentResolver().openInputStream(imageUri);
                    //Get the image...
                    image = BitmapFactory.decodeStream(imageIS);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_SHORT).show();
                }
            }
            //and load it into the preview... only if
            if(null != image)
                loadImage(image);
        }
    }

    /**
     * Changes load the image picker to update this image...
     * @param v the origin of the change
     */
    public void changeImage(@NonNull View v)
    {
        //call the change man...
        showPickImage();
    }
}
