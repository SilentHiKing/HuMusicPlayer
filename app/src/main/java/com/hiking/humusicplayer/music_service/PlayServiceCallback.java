package com.hiking.humusicplayer.music_service;

import com.hiking.humusicplayer.IPlayController;
import com.hiking.humusicplayer.bean.Song;

/**
 */

public interface PlayServiceCallback {
    /**
     * 当前歌曲改变时回调
     * @see com.hiking.humusicplayer.music_service.aidl_imp.OnSongChangedListener(Song, int, boolean)
     */
    void songChanged(Song song, int index, boolean isNext);
    /**
     * 此方法由服务端控制调用
     * @see com.hiking.humusicplayer.music_service.aidl_imp.OnPlayStatusChangedListener#playStart(Song, int, int)
     */
    void startPlay(Song song, int index, int status);
    /**
     * 此方法由服务端控制调用
     * @see com.hiking.humusicplayer.music_service.aidl_imp.OnPlayStatusChangedListener#playStop(Song, int, int)
     */
    void stopPlay(Song song, int index, int status);

    /**
     * 此方法由服务端控制调用
     *
     * @see com.hiking.humusicplayer.music_service.aidl_imp.OnPlayListChangedListener#onPlayListChange(Song, int, int)
     */
    void onPlayListChange(Song current, int index, int id);

    /**
     * 服务端数据初始化完成时回调，客户端与服务器的交互应在此调用到达时才开始
     *
     * @param mControl
     */
    void dataIsReady(IPlayController mControl);

}
