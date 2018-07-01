package com.walkity.apps.journalapp.utils;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Created by alkaj on 7/1/18.
 * An executor to run operations off the main thread
 */

public class AppExecutor implements Executor{
    @Override
    public void execute(@NonNull Runnable runnable) {
        new Thread(runnable).start();
    }
}
