package com.hiking.humusicplayer.music_service;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hiking.humusicplayer.bean.MusicInfo;
import com.hiking.humusicplayer.bean.Song;
import com.hiking.humusicplayer.util.MusicUtil;

import java.util.ArrayList;
import java.util.List;

import static com.hiking.humusicplayer.bean.IConstant.START_FROM_LOCAL;

/**

 * 线程安全的单例，该类在播放进程中也会用到，此时单例失效。
 */

public class MediaManager {

    private List<MusicInfo> songs;

    private static volatile MediaManager MEDIAMANAGER;

    private MediaManager() {
    }

    //传入 Application Context
    public static MediaManager getInstance() {
        if (MEDIAMANAGER == null) {
            synchronized (MediaManager.class) {
                if (MEDIAMANAGER == null)
                    MEDIAMANAGER = new MediaManager();
            }
        }
        return MEDIAMANAGER;
    }

    public List<MusicInfo> refreshData(Context context) {
        songs=MusicUtil.queryMusic(context,START_FROM_LOCAL);
        return songs;
    }

    //根据专辑 id 获得专辑图片保存路径
    private synchronized String getAlbumArtPicPath(Context context, String albumId) {



        String[] projection = {MediaStore.Audio.Albums.ALBUM_ART};
        String imagePath = null;
        Uri uri = Uri.parse("content://media" + MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI.getPath() + "/" + albumId);

        Cursor cur = context.getContentResolver().query(uri, projection, null, null, null);
        if (cur == null) {
            return null;
        }

        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            imagePath = cur.getString(0);
        }
        cur.close();


        return imagePath;
    }

    public MusicInfo getSongInfo(Context context, @NonNull Song song) {
        check(context);
        MusicInfo info = null;
        for (MusicInfo s : songs) {
            info = s;
            if (info.data.equals(song.path)) {
                break;
            }
        }
        return info;
    }

    public MusicInfo getSongInfo(Context context, @NonNull String path) {
        return getSongInfo(context, new Song(path));
    }

    public List<Song> getSongList(Context context) {
        check(context);
        List<Song> songInfos = new ArrayList<>();
        for (MusicInfo song : songs) {
            songInfos.add(new Song(song.data));
        }
        return songInfos;
    }

    public List<MusicInfo> getSongInfoList(Context context) {
        check(context);
        List<MusicInfo> songInfos = new ArrayList<>();
        for (MusicInfo song : songs) {
            songInfos.add(song);
        }
        return songInfos;
    }

    private void check(Context context) {
        if (songs == null)
            refreshData(context);
    }

    public void scanSdCard(Context context, @Nullable MediaScannerConnection.OnScanCompletedListener listener) {
        MediaScannerConnection.scanFile(context, new String[]{Environment
                .getExternalStorageDirectory().getAbsolutePath()}, null, listener);

    }

    /**
     * 检查媒体库是否为空
     *
     * @param refresh 是否要重新获取数据之后再确定，这个过程可能比较耗时
     * @return 为空返回 true
     */
    public boolean emptyMediaLibrary(Context context, boolean refresh) {
        if (refresh) {
            refreshData(context);
        } else {
            check(context);
        }

        if (songs.size() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
