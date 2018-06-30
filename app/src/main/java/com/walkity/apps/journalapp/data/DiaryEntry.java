package com.walkity.apps.journalapp.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by alkaj on 6/30/18.
 * Simple POJO to hold the diary representation
 */

@Entity(tableName = "diaries")
public class DiaryEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String narration;
    private Date date;
    private String images;

    @Ignore
    public DiaryEntry(String title, String narration, Date date, String images)
    {
        this.title  = title;
        this.narration = narration;
        this.date = date;
        this.images = images;
    }

    public DiaryEntry(int id, String title, String narration, Date date, String images)
    {
        this.id = id;
        this.title  = title;
        this.narration = narration;
        this.date = date;
        this.images = images;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setNarration(String narration)
    {
        this.narration = narration;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public void setImages(String images)
    {
        this.images = images;
    }

    public int getId()
    {
        return this.id;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getNarration()
    {
        return this.narration;
    }

    public String getImages()
    {
        return this.images;
    }

    public Date getDate()
    {
        return this.date;
    }
}
