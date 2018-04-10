package com.hiking.humusicplayer.view.adapter;


import android.os.RemoteException;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hiking.humusicplayer.MainActivity;
import com.hiking.humusicplayer.R;
import com.hiking.humusicplayer.bean.IConstant;
import com.hiking.humusicplayer.bean.MusicInfo;
import com.hiking.humusicplayer.bean.Song;
import com.hiking.humusicplayer.model.client.MusicPlayer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RefreshRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private List<MusicInfo> mMusicInfoList;
    MainActivity mMainActivity;


    public RefreshRecyclerAdapter(){
        mMusicInfoList = new ArrayList<>();
    }
    public void setMusicInfoList(List<MusicInfo> musicInfoList){
        if (null != musicInfoList && musicInfoList.size() > 0) {
            mMusicInfoList.clear();
            mMusicInfoList.addAll(musicInfoList);
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_songinfo,parent,false);
        return new ContentViewHoler(v,mMusicInfoList);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MusicInfo info = mMusicInfoList.get(position);
        ((ContentViewHoler)holder).mTvMusicName.setText(info.musicName);
        ((ContentViewHoler)holder).mTvSonger.setText(info.artist);
    }

    @Override
    public int getItemCount() {
        return mMusicInfoList.size();
    }

    static class ContentViewHoler extends RecyclerView.ViewHolder {
        @BindView(R.id.item_musicinfo) View mItem;
        @BindView(R.id.tv_musicname) public TextView mTvMusicName;
        @BindView(R.id.tv_songer)TextView mTvSonger;
//        @BindView(R.id.iv_more)ImageView mIvMore;

        private List<MusicInfo> mMusicInfoList;
        public ContentViewHoler(View itemView,List<MusicInfo> musicList) {
            super(itemView);
            mMusicInfoList =musicList;
            ButterKnife.bind(this,itemView);
        }
        @OnClick(R.id.item_musicinfo)
        public void onItemClick(View v){
            Log.e(IConstant.TAG,"点击Item="+getAdapterPosition());
            Log.e(IConstant.TAG,"歌曲/n"+mMusicInfoList.get(getAdapterPosition()).toString());
            String path=mMusicInfoList.get(getAdapterPosition()).data;
            try {
                MusicPlayer.PLAYER.play(new Song(path));
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
//        @OnClick(R.id.iv_more)
//        public void onMoreClick(View v){
//            Log.e(IConstant.TAG,"点击更多");
//        }
    }
}