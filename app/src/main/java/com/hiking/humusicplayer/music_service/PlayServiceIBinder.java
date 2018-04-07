package com.hiking.humusicplayer.music_service;

import android.content.Context;

import com.hiking.humusicplayer.music_service.aidl_imp.PlayController;

/**
 * Created by DuanJiaNing on 2017/5/23.
 */

public class PlayServiceIBinder extends PlayController {

    private Context mContext;

    public PlayServiceIBinder(Context context) {
        super(context);
        this.mContext = context;
    }
}
