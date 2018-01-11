package com.erp.zhubenerp.presenter;

import com.erp.zhubenerp.app.ZhubenerpApp;
import com.erp.zhubenerp.model.WaitingItemWrapper;
import com.erp.zhubenerp.utils.NetworkExceptionUtils;
import com.erp.zhubenerp.view.IWaitingItems;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by gubin on 2018/1/9.
 */

public class WaitItemPresenter extends BasePresenter<IWaitingItems> {
    public WaitItemPresenter(IWaitingItems from) {
        super(from);
    }

    public void loadData(Map<String,String> param) {
/*        ZhubenerpApp.getApiServer().getWaitDoneItems(param).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Observer<WaitingItemWrapper> (){
           @Override
            public void onCompleted() {

           }

           @Override
            public void onError(Throwable e) {
               if (e != null && baseView != null) {
                   String error = NetworkExceptionUtils.getErrorByException(e);
                   baseView.showError(error);
               }
           }

           @Override
            public void onNext(WaitingItemWrapper wrapper) {
               int status = wrapper.getStatus();
               if (status == 1) {
                   baseView.onSucceed(wrapper);
               } else {
                   baseView.showError(wrapper.getMessage());
               }
           }
        });*/

    }
}
