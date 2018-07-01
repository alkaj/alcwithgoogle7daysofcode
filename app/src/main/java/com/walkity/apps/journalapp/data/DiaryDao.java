package com.walkity.apps.journalapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alkaj on 6/30/18.
 * Class to access our Diary entries
 */

@Dao
public interface DiaryDao {

    @Query("select * from diaries order by date desc")
    LiveData<List<DiaryEntry>> getEntries();

    //a reactive entry
    @Query("select * from diaries where id = :id")
    LiveData<DiaryEntry> getEntry(int id);

    //and a non reactive one...
    @Query("select * from diaries where id = :id")
    DiaryEntry getNonReactiveEntry(int id);

    @Insert
    void addEntry(DiaryEntry entry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateEntry(DiaryEntry entry);

    @Delete
    void deleteEntry(DiaryEntry entry);
}
