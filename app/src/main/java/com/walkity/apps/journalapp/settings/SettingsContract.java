package com.walkity.apps.journalapp.settings;

import com.walkity.apps.journalapp.BasePresenter;

public interface SettingsContract
{
    interface Presenter extends BasePresenter
    {
        /**
         * Save this users content online or not
         * @param value the decision
         */
        public void saveOnline(boolean value);

        /**
         * Clear or not the user data(images and diaries) when logged out
         * @param value determine the decision defaults to false;
         */
        public void clearWhenLoggedOut(boolean value);
    }
}
