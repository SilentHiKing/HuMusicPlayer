package com.hiking.humusicplayer;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.hiking.humusicplayer.base.HBaseActivity;
import com.hiking.humusicplayer.bean.IConstant;
import com.hiking.humusicplayer.bean.MusicInfo;
import com.hiking.humusicplayer.presenter.imp.MainPresenterImp;
import com.hiking.humusicplayer.view.IMainView;
import com.hiking.humusicplayer.view.adapter.RefreshRecyclerAdapter;

import java.util.List;

public class MainActivity extends HBaseActivity<MainPresenterImp> implements IMainView{
    SwipeRefreshLayout mSwipe;
    RecyclerView mRecyclerView;

    RefreshRecyclerAdapter mAdapter;





    @Override
    protected MainPresenterImp initInjector() {
        return new MainPresenterImp();
    }

    @Override
    protected void onSetContentView() {
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void initData() {


    }

    @Override
    protected void initView() {
        super.initView();
        mRecyclerView=(RecyclerView)findViewById(R.id.main_rv);
        mSwipe=(SwipeRefreshLayout)findViewById(R.id.main_srl);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RefreshRecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        Log.d(IConstant.TAG,"MainActivity initEvent");
        mPresenter.querySongList();
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(IConstant.TAG,"MainActivity OnRefreshListener onRefresh");
                setSwipeRefreshLayoutVisible(true);
                mPresenter.querySongList();
            }
        });



    }

    @Override
    public void refreshMusicList(List<MusicInfo> list) {
        Log.d(IConstant.TAG,"MainActivity refreshMusicList");
        mAdapter.setMusicInfoList(list);
    }

    @Override
    public void setSwipeRefreshLayoutVisible(boolean b) {
        mSwipe.setRefreshing(b);
    }


}
