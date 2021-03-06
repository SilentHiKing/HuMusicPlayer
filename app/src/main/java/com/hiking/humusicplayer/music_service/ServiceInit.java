package com.hiking.humusicplayer.music_service;

import android.content.Context;

import com.hiking.humusicplayer.bean.MusicInfo;
import com.hiking.humusicplayer.bean.Song;
import com.hiking.humusicplayer.music_service.aidl_imp.PlayController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DuanJiaNing on 2017/7/12.
 */

class ServiceInit {

    private final PlayController control;
    private final MediaManager manager;
    private Context context;
    public ServiceInit(Context context, PlayController control, MediaManager manager){
        this.context = context;
        this.control = control;
        this.manager = manager;
    }

//    private final PlayPreference preference;
//    private final DBMusicocoController dbController;
//    private final SettingPreference settingPreference;
//
//
//
//    public ServiceInit(Context context, PlayController control, MediaManager manager, PlayPreference preference, DBMusicocoController dbController, SettingPreference settingPreference) {
//        this.context = context;
//        this.control = control;
//        this.manager = manager;
//        this.preference = preference;
//        this.dbController = dbController;
//        this.settingPreference = settingPreference;
//
//    }

    public void start() {

        initData();


        /*initPlayList();

        initPlayMode();

        initCurrentSong();*/

    }
    private void initData() {
        List<MusicInfo> ls=manager.refreshData(context);
        List<Song> songs = new ArrayList<>();
        for (MusicInfo info : ls) {
            Song song = new Song(info.data);
            songs.add(song);
        }
        control.setPlayList(songs, 0, 1);
    }

    /*private void initCurrentSong() {
        if (settingPreference.memoryPlay()) {
            //恢复上次播放状态
            Song song = null;
            PlayPreference.CurrentSong cur = preference.getLastPlaySong();
            if (cur != null && cur.path != null) {
                song = new Song(cur.path);
            }

            List<Song> songs = control.getPlayList();

            if (songs != null && songs.size() > 0) {
                if (song == null) {  //配置文件没有保存【最后播放曲目】信息（通常为第一次打开应用）
                    song = songs.get(0);
                } else { //配置文件有保存
                    if (!songs.contains(song)) { //确认服务端有此歌曲
                        song = songs.get(0);
                    }
                }

                // songChanged 将被回调
                control.setCurrentSong(song);

                int pro = cur.progress;
                if (pro >= 0) {
                    control.seekTo(pro);
                }
            }
        } else {
            control.setPlaySheet(MainSheetHelper.SHEET_ALL, 0);
        }

    }

    private void initPlayMode() {
        int cm = preference.getPlayMode();
        control.setPlayMode(cm);
    }*/

    // 配置文件无法跨进程共享，同步工作由客户端负责，服务端只在首次启动时读取
    /*private void initPlayList() {
        int sheetID = preference.getSheetID();
        List<DBSongInfo> list;
        if (sheetID < 0) {
            MainSheetHelper msh = new MainSheetHelper(context, dbController);
            list = msh.getMainSheetSongInfo(sheetID);
        } else {
            list = dbController.getSongInfos(sheetID);
        }
        Song s = control.setPlayList(getSongs(list), 0, sheetID);
        if (s == null) {
            //歌单设置失败，重置为【全部歌曲】歌单
            preference.updateSheet(MainSheetHelper.SHEET_ALL);
            initPlayList();
        }

    }*/

    /*private List<Song> getSongs(List<DBSongInfo> dbSongInfos) {
        List<Song> songs = new ArrayList<>();
        for (DBSongInfo info : dbSongInfos) {
            Song song = new Song(info.path);
            songs.add(song);
        }
        return songs;
    }*/


}
