package com.hiking.humusicplayer.model.client;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;

import com.hiking.humusicplayer.IPlayController;
import com.hiking.humusicplayer.bean.IConstant;
import com.hiking.humusicplayer.bean.MusicInfo;
import com.hiking.humusicplayer.music_service.PlayService;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class PlayServiceManager {
    public static IPlayController PLAYER;
    private WeakReference<ServiceConnection> mConnection;

    public static List<MusicInfo> mCurrentMusicList=new ArrayList<>();

    private Context context;

    public PlayServiceManager(Context context) {
        this.context = context;
    }

    //启动服务，需要关闭记得一定要使用 stopService 关闭，即使没有组件绑定到服务服务也会一直运行，因为此时他是以启动的方式启动的，而不是绑定。
    public static void startPlayService(Context context) {
        Intent intent = new Intent(context, PlayService.class);
        context.startService(intent);
    }

    //绑定服务
    public void bindService(ServiceConnection connection) {
        if (PLAYER !=null){
            return;
        }
        Log.d(IConstant.TAG,"bindService");
        Intent intent = new Intent();
        ComponentName componentName =new ComponentName("com.hiking.humusicplayer","com.hiking.humusicplayer.music_service.PlayService");
        intent.setComponent(componentName);
        if (context.bindService(intent, connection, Service.BIND_AUTO_CREATE)){
            mConnection =new WeakReference<ServiceConnection>(connection);
        }

    }
    //解除绑定服务
    public void unBindService(ServiceConnection connection) {
        Log.d(IConstant.TAG,"unBindService");
        PLAYER =null;
        if (mConnection!=null){
            context.unbindService(mConnection.get());
        }
    }

}
