package com.erp.zhubenerp.http;

import com.erp.zhubenerp.base.BaseObject;
import com.erp.zhubenerp.model.UserWrapper;
import com.erp.zhubenerp.model.WaitingItemWrapper;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import rx.Observable;
/**
 * Created by gubin on 2018/1/9.
 */

public interface ApiServer {

    @FormUrlEncoded
    public Observable<UserWrapper> login(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    public Observable<BaseObject> changePwd(@FieldMap Map<String,String> params);

    @FormUrlEncoded
    public Observable<WaitingItemWrapper> getWaitDoneItems(@FieldMap Map<String,String> params);
}
