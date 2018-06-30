package com.walkity.apps.journalapp.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by alkaj on 6/30/18.
 * Class to access our Diary entries
 */

@Dao
public interface DiaryDao {

    @Query("select * from diaries order by date")
    List<DiaryEntry> getEntries();

    @Insert
    void addEntry(DiaryEntry entry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateEntry(DiaryEntry entry);

    @Delete
    void deleteEntry(DiaryEntry entry);
}
