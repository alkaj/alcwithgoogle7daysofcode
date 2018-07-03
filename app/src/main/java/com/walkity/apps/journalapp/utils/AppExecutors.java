package com.walkity.apps.journalapp.utils;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Created by alkaj on 7/2/18.
 * App executors
 */

public class AppExecutors implements Executor{
    @Override
    public void execute(@NonNull Runnable runnable) {
       new Thread(runnable).start();
    }
}
