package uk.ab.baking;

import android.app.Application;

import timber.log.Timber;

public class BakingApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Add logging using Timber.
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
