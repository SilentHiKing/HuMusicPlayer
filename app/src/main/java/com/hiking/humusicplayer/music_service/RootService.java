package com.hiking.humusicplayer.music_service;

import android.app.Service;

/**
 * Created by DuanJiaNing on 2017/6/24.
 */

public abstract class RootService extends Service {

    /*protected DBMusicocoController dbController;
    protected MediaManager mediaManager;

    protected PlayPreference playPreference;
    protected AppPreference appPreference;
    protected SettingPreference settingPreference;

    public RootService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.playPreference = new PlayPreference(this);
        this.appPreference = new AppPreference(this);
        this.settingPreference = new SettingPreference(this);
        this.dbController = new DBMusicocoController(this, false);
        this.mediaManager = MediaManager.getInstance();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (dbController != null) {
            dbController.close();
        }
    }*/
}
