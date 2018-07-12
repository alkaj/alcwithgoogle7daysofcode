package com.walkity.apps.journalapp.utils;

import android.content.ClipData;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.walkity.apps.journalapp.R;
import com.walkity.apps.journalapp.data.DiaryEntry;
import com.walkity.apps.journalapp.databinding.JournalItemBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by alkaj on 7/1/18.
 * The adapter to our diaries recycler view
 */

public class DiariesListAdapter extends RecyclerView.Adapter<DiariesListAdapter.ViewHolder>
{
    private List<DiaryEntry> diaries;
    private Context context;
    private ListClickListener cl;
    private int clickedPosition;

    public DiariesListAdapter(List<DiaryEntry> entries, Context ctx)
    {
        diaries = entries;
        context = ctx;
        cl = (ListClickListener)ctx;
    }

    @NonNull
    @Override
    public DiariesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        JournalItemBinding item = JournalItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DiaryEntry entry = getItem(position);
        holder.bind(entry);
    }

    @Override
    public int getItemCount() {
        return diaries.size();
    }

    /*
    Get an item according to its position
     */
    DiaryEntry getItem(int position)
    {
        return diaries.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        final JournalItemBinding item;
        public ViewHolder(JournalItemBinding itemView) {
            super(itemView.getRoot());
            item = itemView;
            item.executePendingBindings();
        }



        public void bind(DiaryEntry entry)
        {
            View.OnClickListener ct = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cl.onClick(getItem(getAdapterPosition()), view);
                }
            };
            item.setItem(entry);
            item.getRoot().setOnClickListener(ct);
            item.iconDelete.setOnClickListener(ct);
            item.iconEdit.setOnClickListener(ct);
            //get the image if any... and set it as the background
            if(!entry.getImages().equals(""))
            {
                //then create a bitmap of that image...
                Drawable image = null;
                File imageFile = new File(entry.getImages());
                Uri authorized = FileProvider.getUriForFile(context, "com.walkity.apps.journalapp", imageFile);
                try {
                    InputStream is = context.getContentResolver().openInputStream(authorized);
                    image = BitmapDrawable.createFromStream(is, "entry_background");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (null != image)
                    item.image.setBackground(image);
            }
            item.executePendingBindings();
        }
    }

    public interface ListClickListener {
        void onClick(DiaryEntry entry, View v);
    }
}
