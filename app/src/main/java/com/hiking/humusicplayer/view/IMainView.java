package com.hiking.humusicplayer.view;

import com.hiking.humusicplayer.bean.MusicInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/4/5.
 */

public interface IMainView{
    void refreshMusicList(List<MusicInfo> list);

    void setSwipeRefreshLayoutVisible(boolean b);
}
