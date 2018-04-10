package com.hiking.humusicplayer.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.hiking.basemvp.imp.BaseActivity;
import com.hiking.basemvp.inter.IPresenter;
import com.hiking.humusicplayer.IPlayController;
import com.hiking.humusicplayer.R;
import com.hiking.humusicplayer.bean.IConstant;
import com.hiking.humusicplayer.bean.Song;
import com.hiking.humusicplayer.model.client.MusicPlayer;
import com.hiking.humusicplayer.model.client.PlayServiceConnection;
import com.hiking.humusicplayer.view.fragment.QuickControlsFragment;

/**
 * Created by Administrator on 2018/4/5.
 */

public abstract class HBaseActivity<T extends IPresenter> extends BaseActivity<T> {
    public static IPlayController mPlayController;
    private QuickControlsFragment fragment; //底部播放控制栏
    HandlerThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(IConstant.TAG,"HBaseActivity onCreate");
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initSDK() {
        super.initSDK();
        thread=new HandlerThread("get_music_service");
        thread.start();
//fragment = QuickControlsFragment.newInstance();

    }

    @Override
    protected void initView() {
        super.initView();
        Log.d(IConstant.TAG,"HBaseActivity initView");

    }

    @Override
    protected void initEvent() {
        super.initEvent();
        showQuickControl(true);

        final PlayServiceConnection connection=new PlayServiceConnection(fragment,this);
        new Thread(){
            @Override
            public void run() {
                super.run();
                MusicPlayer.getInstance(getApplicationContext()).bindService(connection);
            }
        }.start();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(IConstant.TAG,"HBaseActivity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(IConstant.TAG,"HBaseActivity onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(IConstant.TAG,"HBaseActivity onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(IConstant.TAG,"HBaseActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(IConstant.TAG,"HBaseActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        thread.quit();
//        mPlayController =null;
        Log.d(IConstant.TAG,"HBaseActivity onDestroy");
    }

    /**
     * @param show 显示或关闭底部播放控制栏
     */
    protected void showQuickControl(boolean show) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (show) {
            if (fragment == null) {
                fragment = QuickControlsFragment.newInstance();
                ft.add(R.id.bottom_container, fragment).commitAllowingStateLoss();
            } else {
                ft.show(fragment).commitAllowingStateLoss();
            }
        } else {
            if (fragment != null)
                ft.hide(fragment).commitAllowingStateLoss();
        }
    }

    public MusicHandler getMusicHandler(){
        return new MusicHandler(thread.getLooper());
    }

    public static class MusicHandler extends Handler {

        public MusicHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case IConstant.CONN_TO_NEXT:
                    try {
                        MusicPlayer.PLAYER.play(MusicPlayer.PLAYER.next());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case IConstant.CONN_TO_PAUSE:
                    try {
                        MusicPlayer.PLAYER.pause();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case IConstant.CONN_TO_PLAY:
                    try {
                        if (msg.obj instanceof Song){
                            MusicPlayer.PLAYER.play((Song)msg.obj);
                        }else{
                            MusicPlayer.PLAYER.resume();
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case IConstant.CONN_TO_UP:
                    try {
                        MusicPlayer.PLAYER.play(MusicPlayer.PLAYER.pre());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                default:
                    break;
            }
        }
    }


}
