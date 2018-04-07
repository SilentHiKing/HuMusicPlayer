package com.hiking.humusicplayer.presenter.imp;

import android.util.Log;

import com.hiking.basemvp.imp.BasePresenterImpl;
import com.hiking.humusicplayer.MainActivity;
import com.hiking.humusicplayer.bean.IConstant;
import com.hiking.humusicplayer.bean.MusicInfo;
import com.hiking.humusicplayer.model.client.PlayServiceManager;
import com.hiking.humusicplayer.presenter.IMainPresenter;
import com.hiking.humusicplayer.util.MusicUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/4/5.
 */

public class MainPresenterImp extends BasePresenterImpl<MainActivity> implements IMainPresenter {
    @Override
    public List<MusicInfo> querySongList() {
        Observable.create(new ObservableOnSubscribe<List<MusicInfo>>() {
            @Override
            public void subscribe(ObservableEmitter<List<MusicInfo>> e) throws Exception {
                List<MusicInfo> list=MusicUtil.queryMusic(mView.getContext().getApplicationContext(), IConstant.START_FROM_LOCAL);
                PlayServiceManager.mCurrentMusicList =list;
                e.onNext(list);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MusicInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<MusicInfo> value) {
                        mView.refreshMusicList(value);
                        mView.setSwipeRefreshLayoutVisible(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(IConstant.TAG,"获取歌曲列表失败");
                        mView.setSwipeRefreshLayoutVisible(false);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(IConstant.TAG,"MainPresenterImp 获取歌曲并成功显示");
                        mView.setSwipeRefreshLayoutVisible(false);

                    }
                });

        return null;
    }


}
