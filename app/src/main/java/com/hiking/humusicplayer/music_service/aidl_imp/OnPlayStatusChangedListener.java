package com.hiking.humusicplayer.music_service.aidl_imp;

import android.os.IBinder;

import com.hiking.humusicplayer.IOnPlayStatusChangedListener;
import com.hiking.humusicplayer.bean.Song;
import com.hiking.humusicplayer.music_service.PlayControllerImp;

/**
 * Created by Administrator on 2018/4/5.
 * 用户手动暂停，播放歌曲时回调
 */

public abstract class OnPlayStatusChangedListener extends IOnPlayStatusChangedListener.Stub {

    @Override
    public IBinder asBinder() {
        return super.asBinder();
    }

    /**
     * 1 自动播放时开始播放曲目时回调
     * 2 继续播放，开始播放时回调
     *
     * @param song   当前开始播放曲目
     * @param index  播放列表下标
     * @param status 播放状态，均为 {@link PlayControllerImp#STATUS_START}
     */
    @Override
    public abstract void playStart(Song song, int index, int status);

    /**
     * 1 自动播放时播放曲目播放完成时回调，一般情况下该方法调用后{@link #playStart(Song, int, int)}将会被调用
     * 2 暂停，停止播放时回调
     *
     * @param song   当前播放曲目
     * @param index  播放列表下标
     * @param status 播放状态，为 {@link PlayControllerImp#STATUS_STOP}（
     */
    @Override
    public abstract void playStop(Song song, int index, int status);

}