package com.walkity.apps.journalapp.utils;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.walkity.apps.journalapp.R;
import com.walkity.apps.journalapp.data.DiaryEntry;
import com.walkity.apps.journalapp.databinding.JournalItemBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by alkaj on 7/1/18.
 * The adapter to our diaries recycler view
 */

public class DiariesListAdapter extends RecyclerView.Adapter<DiariesListAdapter.ViewHolder> {
    private List<DiaryEntry> diaries;
    private Context context;

    public DiariesListAdapter(List<DiaryEntry> entries, Context ctx)
    {
        diaries = entries;
        context = ctx;
    }

    @NonNull
    @Override
    public DiariesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //date formatting...
        java.text.SimpleDateFormat sdfdate = new java.text.SimpleDateFormat(context.getString(R.string.date_format), Locale.getDefault());
        java.text.SimpleDateFormat sdftime = new java.text.SimpleDateFormat(context.getString(R.string.time_format), Locale.getDefault());
        holder.item.date.setText(sdfdate.format(diaries.get(position).getDate()));
        holder.item.time.setText(sdftime.format(diaries.get(position).getDate()));
        holder.item.title.setText(diaries.get(position).getTitle());
        holder.item.narration.setText(diaries.get(position).getNarration());
        holder.item.iconDelete.setOnClickListener(null);
        holder.item.iconEdit.setOnClickListener(null);
    }

    @Override
    public int getItemCount() {
        return diaries.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        JournalItemBinding item;
        public ViewHolder(View itemView) {
            super(itemView);
            item = DataBindingUtil.getBinding(itemView);
        }
    }
}
