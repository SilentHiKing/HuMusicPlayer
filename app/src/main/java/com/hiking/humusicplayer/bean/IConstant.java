package com.hiking.humusicplayer.bean;

/**
 * Created by Administrator on 2018/4/6.
 */

public interface IConstant {
    String TAG = "当前";

    String MUSIC_COUNT_CHANGED = "com.wm.remusic.musiccountchanged";
    String PLAYLIST_ITEM_MOVED = "com.wm.remusic.mmoved";
    String PLAYLIST_COUNT_CHANGED = "com.wm.remusic.playlistcountchanged";
    String CHANGE_THEME = "com.wm.remusic.themechange";
    String EMPTY_LIST = "com.wm.remusic.emptyplaylist";
    String PACKAGE = "com.wm.remusic";
    int MUSICOVERFLOW = 0;
    int ARTISTOVERFLOW = 1;
    int ALBUMOVERFLOW = 2;
    int FOLDEROVERFLOW = 3;

    //歌手和专辑列表点击都会进入MyMusic 此时要传递参数表明是从哪里进入的
    int START_FROM_ARTIST = 1;
    int START_FROM_ALBUM = 2;
    int START_FROM_LOCAL = 3;
    int START_FROM_FOLDER = 4;
    int START_FROM_FAVORITE = 5;

    int FAV_PLAYLIST = 10;


    String NAVIGATE_NOWPLAYING = "navigate_nowplaying";

    //与MusicService的通信
    int CONN_TO_NEXT=10;
    int CONN_TO_PLAY=CONN_TO_NEXT+1;
    int CONN_TO_PAUSE=CONN_TO_NEXT+2;
}
