package com.etermax.iflickr.app;

import android.app.Application;
import android.content.Context;

import com.etermax.iflickr.apimodel.Base.DaoMaster;
import com.etermax.iflickr.apimodel.Base.DaoSession;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import org.greenrobot.greendao.database.Database;

/**
 * Created by rnet_ on 20/03/2017.
 */

public class MyApplication extends Application {

    private static Context context;
    public static final boolean ENCRYPTED = true;
    private static DaoSession daoSession;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "flickr-photo-db-encrypted" : "flickr-photo-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("sssecret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        Fresco.initialize(this);
        setupPicasso();

    }

    public static Context getAppContext() {
        return MyApplication.context;
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    private void setupPicasso() {
        /*
        Cache diskCache = new Cache(getDir(CacheConstants.DISK_CACHE_DIRECTORY, Context.MODE_PRIVATE), CacheConstants.DISK_CACHE_SIZE);
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setCache(diskCache);
        File cachePath = FuncFileDownload.getStoragePath(context, "pre");
        */

        Picasso picasso = new Picasso.Builder(this.getApplicationContext())
                .downloader(new OkHttpDownloader(this.getApplicationContext(), Integer.MAX_VALUE))
                .build();

        picasso.setIndicatorsEnabled(true); // For debugging
        try {
            Picasso.setSingletonInstance(picasso);
        } catch (IllegalStateException ignored) {
            Picasso.setSingletonInstance(picasso);
        }
    }


}
