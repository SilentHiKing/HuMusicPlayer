// IOnSongChange.aidl
package com.hiking.humusicplayer;
import com.hiking.humusicplayer.bean.Song;

// Declare any non-default types here with import statements

interface IOnPlayListChangedListener {

    void onPlayListChange(in Song currentMusicInfo,int index,int id);
}
