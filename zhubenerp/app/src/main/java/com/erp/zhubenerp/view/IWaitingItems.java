package com.erp.zhubenerp.view;


import com.erp.zhubenerp.model.WaitingItemWrapper;
/**
 * Created by gubin on 2018/1/8.
 */

public interface IWaitingItems extends IBaseView{
    void onSucceed(WaitingItemWrapper wrapper);
}
