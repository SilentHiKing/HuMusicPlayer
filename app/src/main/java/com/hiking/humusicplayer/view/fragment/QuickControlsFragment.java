package com.hiking.humusicplayer.view.fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hiking.basemvp.imp.BaseFragment;
import com.hiking.humusicplayer.IPlayController;
import com.hiking.humusicplayer.R;
import com.hiking.humusicplayer.base.HBaseActivity;
import com.hiking.humusicplayer.bean.IConstant;
import com.hiking.humusicplayer.bean.Song;
import com.hiking.humusicplayer.model.client.MusicPlayer;
import com.hiking.humusicplayer.music_service.PlayServiceCallback;

public class QuickControlsFragment extends BaseFragment implements PlayServiceCallback, View.OnClickListener {


    private ImageView mPlayPause;
    private TextView mTitle;
    private TextView mArtist;
    private ImageView mAlbumArt;
    private ImageView next;
//    ImageView playQueue;
    ImageView up;
    View rootView;
    private boolean isPlaying =false;
    private String TAG = "QuickControlsFragment";
    private static QuickControlsFragment fragment;
    HBaseActivity.MusicHandler mMusicHandler;

    public static QuickControlsFragment newInstance() {
        return new QuickControlsFragment();
    }


    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.bottom_nav, container, false);
        this.rootView = rootView;
        return rootView;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void bindView() {
        super.bindView();
        mPlayPause = (ImageView) rootView.findViewById(R.id.control);
        mTitle = (TextView) rootView.findViewById(R.id.playbar_info);
        mArtist = (TextView) rootView.findViewById(R.id.playbar_singer);
        mAlbumArt = (ImageView) rootView.findViewById(R.id.playbar_img);
        next = (ImageView) rootView.findViewById(R.id.play_next);
//        playQueue = (ImageView) rootView.findViewById(R.id.play_list);
        up = (ImageView) rootView.findViewById(R.id.play_up);
        mPlayPause.setOnClickListener(this);
        mTitle.setOnClickListener(this);
        mArtist.setOnClickListener(this);
        mAlbumArt.setOnClickListener(this);
        next.setOnClickListener(this);
//        playQueue.setOnClickListener(this);
        up.setOnClickListener(this);
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();
        mMusicHandler=((HBaseActivity)getActivity()).getMusicHandler();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.control:
                if (isPlaying){mMusicHandler.sendEmptyMessage(IConstant.CONN_TO_PAUSE);}
                else{
                    mMusicHandler.sendEmptyMessage(IConstant.CONN_TO_PLAY);
                }
                break;
            case R.id.play_next:
                mMusicHandler.sendEmptyMessage(IConstant.CONN_TO_NEXT);
                break;
            case R.id.play_up:
                mMusicHandler.sendEmptyMessage(IConstant.CONN_TO_UP);
                break;
            default:
                break;
        }

    }




    @Override
    public void songChanged(Song song, int index, boolean isNext) {
        mTitle.setText(MusicPlayer.mCurrentMusicList.get(index).musicName);
        mArtist.setText(MusicPlayer.mCurrentMusicList.get(index).artist);
    }

    @Override
    public void startPlay(Song song, int index, int status) {
        mTitle.setText(MusicPlayer.mCurrentMusicList.get(index).musicName);
        mArtist.setText(MusicPlayer.mCurrentMusicList.get(index).artist);
        Log.d(IConstant.TAG,"musicinfo albumData="+ MusicPlayer.mCurrentMusicList.get(index).albumData);
        mPlayPause.setImageResource(R.drawable.playbar_btn_pause);
        isPlaying =true;

    }

    @Override
    public void stopPlay(Song song, int index, int status) {
        mPlayPause.setImageResource(R.drawable.playbar_btn_play);
        isPlaying =false;

    }

    @Override
    public void onPlayListChange(Song current, int index, int id) {

    }

    @Override
    public void dataIsReady(IPlayController mControl) {

    }


}