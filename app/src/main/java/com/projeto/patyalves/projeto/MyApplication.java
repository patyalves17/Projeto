package com.projeto.patyalves.projeto;
import android.app.Application;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

/**
 * Created by logonrm on 25/07/2017.
 */

public class MyApplication extends Application {


    private static final String TWITTER_KEY = "3TXPDtvCPOR4MQM9QrIKJLHdO";
    private static final String TWITTER_SECRET = "6txlJmRbuSrKtvMZWM5p2DhvzsUJdxjG7Psof7wF50xVgz6m8L";


    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET))
                .debug(true)
                .build();
        Twitter.initialize(config);
    }
}