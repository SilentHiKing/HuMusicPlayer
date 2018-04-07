// IOnSongChange.aidl
package com.hiking.humusicplayer;
import com.hiking.humusicplayer.bean.Song;

// Declare any non-default types here with import statements

interface IOnSongChangedListener {

    //该方法运行在线程池中（非 UI 线程）
    void onSongChange(in Song which,int index,boolean isNext);
}
