package com.walkity.apps.journalapp.data;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by alkaj on 6/30/18.
 * Class to inter-convert the date so that it can be handled properly with sqlite
 */

public class DateConverter {

    @TypeConverter
    public static Date toDate(Long time)
    {
        return time == null ? null : new Date(time);
    }

    @TypeConverter
    public Long toTime(Date date)
    {
        return date == null ? null : date.getTime();
    }
}
