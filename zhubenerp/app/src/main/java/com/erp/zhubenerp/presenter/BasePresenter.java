package com.erp.zhubenerp.presenter;

/**
 * Created by gubin on 2017/12/29.
 */

public abstract class BasePresenter<T> {
    protected String TAG = getClass().getName();
    protected T baseView;

    public BasePresenter(T from) {
        this.baseView = from;
    }
}
