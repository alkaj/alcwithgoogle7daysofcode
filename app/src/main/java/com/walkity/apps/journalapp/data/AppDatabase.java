package com.walkity.apps.journalapp.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

/**
 * Created by alkaj on 6/30/18.
 * Room database holder for our diaries database.
 */

@Database(entities = {DiaryEntry.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getCanonicalName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "diaries_db";
    private static AppDatabase database;

    public static AppDatabase getInstance(Context context)
    {
       if(database == null)
       {
           synchronized (LOCK)
           {
               Log.w(LOG_TAG, "Creating a new database instance");
               database = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                       AppDatabase.DATABASE_NAME).build();
           }
       }
       Log.w(LOG_TAG, "Getting the database instance");
       return database;
    }

    public abstract DiaryDao dao();
}
