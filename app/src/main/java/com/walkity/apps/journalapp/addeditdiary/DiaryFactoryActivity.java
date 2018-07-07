package com.walkity.apps.journalapp.addeditdiary;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.walkity.apps.journalapp.R;
import com.walkity.apps.journalapp.data.DiaryEntry;
import com.walkity.apps.journalapp.databinding.ActivityDiaryFactoryBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.UUID;

public class DiaryFactoryActivity extends AppCompatActivity implements AddEditContract.View {
    private ActivityDiaryFactoryBinding factoryBinding;
    private AddEditPresenter mPresenter;
    private DiaryEntry mEntry;
    private final int GET_IMAGE_REQUEST = 1234;
    private String TAG = getClass().getName();

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
        if(null == mEntry) {
            mEntry = entry;
            //load preview...
            loadImage(path2image());
            java.text.SimpleDateFormat sdfdate = new java.text.SimpleDateFormat(getString(R.string.date_format), Locale.getDefault());
            java.text.SimpleDateFormat sdftime = new java.text.SimpleDateFormat(getString(R.string.time_format), Locale.getDefault());
            factoryBinding.textView.setText(sdfdate.format(mEntry.getDate()).toUpperCase());
            factoryBinding.time.setText(sdftime.format(mEntry.getDate()));
            factoryBinding.textInputLayout.getEditText().setText(mEntry.getTitle());
            factoryBinding.textInputLayout2.getEditText().setText(mEntry.getNarration());
        }
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
        //image name
        String name = null;
        File photofile = null;
        if(mEntry.getImages().equals(""))
        {
            name = UUID.randomUUID().toString();
            File storage = new File(getFilesDir(), "Images");
            storage.mkdirs();
            try {
                photofile = File.createTempFile(name, ".jpg", storage);
            }catch (Exception e)
            {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong trying to create a the image",
                        Toast.LENGTH_LONG).show();
                return;
            }
        }
        else
        {
            name = mEntry.getImages();
            photofile = new File(name);
        }
        Uri fileUri = FileProvider.getUriForFile(this, "com.walkity.apps.journalapp",
                photofile);
        //update the entry image path...
        mEntry.setImages(photofile.getAbsolutePath());
        //tell the intent to save the file...
        pickimageIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        //...
        pickimageIntent.setAction(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(pickimageIntent, GET_IMAGE_REQUEST);
    }

    /**
     * loads the provided image into the preview frame...
     * @param image to be loaded into the preview frame...
     */
    private void loadImage(Bitmap image) {
        if (null != image)
        {
            factoryBinding.imagePreview.setVisibility(View.VISIBLE);
            factoryBinding.editImagePreview.setVisibility(View.VISIBLE);
            factoryBinding.imagePreview.setImageBitmap(image);
        }
        else{
            factoryBinding.editImagePreview.setVisibility(View.GONE);
            factoryBinding.imagePreview.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GET_IMAGE_REQUEST && resultCode == RESULT_OK)
        {
            Bitmap image = null;
            if(data == null)
            {
                //This is an image from the camera... that was saved as mEntry image
                image = path2image();
            }
            else if(data.getData() != null)
            {
                //This is an existing image...

                //Get the image uri sent as data...
                Uri imageUri = data.getData();
                try {
                    image = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    //compress and save that image...
                    //the file name...
                    String name = UUID.randomUUID().toString();
                    name = getFilesDir() + "/Images/" + name + ".jpg";
                    if (!mEntry.getImages().equals(""))
                        name = mEntry.getImages();
                    File file = new File(name);
                    FileOutputStream fos = new FileOutputStream(file);
                    image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                    //save the address
                    mEntry.setImages(file.getAbsolutePath());
                    //finaly refresh the view...
                    path2image();
                }catch(IOException fne)
                {
                    fne.printStackTrace();
                }

            }else
                Log.w(TAG, "Something when wrong with the bundle, neither data nor extra");

            //and load it into the preview... only if
            if(null != image)
            {
                loadImage(image);
            }
            else Log.w(TAG, "The imgage was null with the path: " + mEntry.getImages());
        }else
            Log.w(TAG, "Something when wrong with the data");
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

    private Bitmap path2image()
    {
        String path = mEntry.getImages();
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
