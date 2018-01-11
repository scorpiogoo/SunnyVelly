package com.erp.zhubenerp.presenter;

import com.erp.zhubenerp.view.IBaseView;
/**
 * Created by gubin on 2017/12/29.
 */

public interface IPresenter<T extends IBaseView> {
    void attachView(T v);
    void detachView();
}
