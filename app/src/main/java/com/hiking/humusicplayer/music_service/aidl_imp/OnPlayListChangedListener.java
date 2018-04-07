package com.hiking.humusicplayer.music_service.aidl_imp;

import android.os.IBinder;

import com.hiking.humusicplayer.IOnPlayListChangedListener;
import com.hiking.humusicplayer.bean.Song;

/**
 * Created by Administrator on 2018/4/5.
 * 服务端的播放列表改变时回调
 * 1 改变歌单时回调
 * 2 从歌单中移除歌曲时回调
 */

public abstract class OnPlayListChangedListener extends IOnPlayListChangedListener.Stub {

    @Override
    public IBinder asBinder() {
        return super.asBinder();
    }

    /**
     * 服务端的播放列表改变时回调
     *
     * @param current 当前曲目
     * @param index   曲目下标
     * @param id      歌单 id
     */
    @Override
    public abstract void onPlayListChange(Song current, int index, int id);

}
