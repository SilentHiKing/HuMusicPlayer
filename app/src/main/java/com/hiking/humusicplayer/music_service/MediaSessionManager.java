package com.hiking.humusicplayer.music_service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.RemoteException;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.hiking.humusicplayer.IPlayController;
import com.hiking.humusicplayer.R;
import com.hiking.humusicplayer.bean.MusicInfo;
import com.hiking.humusicplayer.util.StringUtils;


/**
 * * 参考文章 http://www.jianshu.com/p/bc2f779a5400;
 * https://blog.csdn.net/lucky9322/article/details/79247031
 */

public class MediaSessionManager {

    private static final String TAG = "MediaSessionManager";

    private static final long MEDIA_SESSION_ACTIONS =
            PlaybackStateCompat.ACTION_PLAY
                    | PlaybackStateCompat.ACTION_PAUSE
                    | PlaybackStateCompat.ACTION_PLAY_PAUSE
                    | PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                    | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                    | PlaybackStateCompat.ACTION_STOP
                    | PlaybackStateCompat.ACTION_SEEK_TO;

    private final IPlayController control;
    private final Context context;
    private MediaSessionCompat mMediaSession;
    private final MediaManager mediaManager;

    public MediaSessionManager(Context context, IPlayController controller) {
        this.context = context;
        this.control = controller;
        this.mediaManager = MediaManager.getInstance();
        setupMediaSession();
    }

    /**
     * 初始化并激活MediaSession
     */
    private void setupMediaSession() {
        mMediaSession = new MediaSessionCompat(context, TAG);
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
        );
        mMediaSession.setCallback(callback);
        mMediaSession.setActive(true);
    }

    /**
     * 更新播放状态，播放/暂停/拖动进度条时调用
     */
    public void updatePlaybackState() {
        int state = isPlaying() ? PlaybackStateCompat.STATE_PLAYING : PlaybackStateCompat.STATE_PAUSED;
        mMediaSession.setPlaybackState(
                new PlaybackStateCompat.Builder()
                        .setActions(MEDIA_SESSION_ACTIONS)
                        .setState(state, getCurrentPosition(), 1)
                        .build());
    }

    private long getCurrentPosition() {
        try {
            return control.getProgress();
        } catch (RemoteException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private boolean isPlaying() {
        try {
            return control.status() == PlayControllerImp.STATUS_PLAYING;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新正在播放的音乐信息，切换歌曲时调用
     */
    public void updateMetaData(String path) {
        if (!StringUtils.isReal(path)) {
            mMediaSession.setMetadata(null);
            return;
        }

        MusicInfo info = mediaManager.getSongInfo(context, path);
        MediaMetadataCompat.Builder metaData = new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, info.musicName)
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, info.artist)
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, info.albumName)
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ARTIST, info.artist)
                .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, info.duration)
                .putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, getCoverBitmap(info));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            metaData.putLong(MediaMetadataCompat.METADATA_KEY_NUM_TRACKS, getCount());
        }

        mMediaSession.setMetadata(metaData.build());
    }

    private long getCount() {
        try {
            return control.getPlayList().size();
        } catch (RemoteException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private Bitmap getCoverBitmap(MusicInfo info) {
        if (StringUtils.isReal(info.albumData)) {
            return BitmapFactory.decodeFile(info.albumData);
        } else {
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.default_song);
        }
    }

    /**
     * 释放MediaSession，退出播放器时调用
     */
    public void release() {
        mMediaSession.setCallback(null);
        mMediaSession.setActive(false);
        mMediaSession.release();
    }

    private MediaSessionCompat.Callback callback = new MediaSessionCompat.Callback() {
        @Override
        public void onPlay() {
            try {
                control.resume();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onPause() {
            try {
                control.pause();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSkipToNext() {
            try {
                control.next();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSkipToPrevious() {
            try {
                control.pre();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onStop() {
            try {
                control.pause();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSeekTo(long pos) {
            try {
                control.seekTo((int) pos);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

}
