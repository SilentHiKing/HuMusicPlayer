package com.hiking.basemvp.imp;

import android.support.annotation.NonNull;

import com.hiking.basemvp.inter.IPresenter;
import com.hiking.basemvp.inter.IView;

public class BasePresenterImpl<T extends IView> implements IPresenter {
    protected T mView;

    @Override
    public void attachView(@NonNull IView iView) {
        mView = (T) iView;
    }

    @Override
    public void detachView() {
        mView =null;
    }


}
