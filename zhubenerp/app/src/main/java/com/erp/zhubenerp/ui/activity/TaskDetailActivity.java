package com.erp.zhubenerp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.erp.zhubenerp.R;
import com.erp.zhubenerp.app.ZhubenerpApp;
import com.erp.zhubenerp.base.BaseActivity;
import com.erp.zhubenerp.model.WaitingItemWrapper;
import com.erp.zhubenerp.utils.DateTimeUtils;
import com.erp.zhubenerp.utils.MD5Utils;
import com.erp.zhubenerp.widget.CustomTextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by gubin on 2018/1/9.
 */

public class TaskDetailActivity extends BaseActivity{
    @Bind(R.id.tvGenjinTime)
    AppCompatTextView tvGenjinTime;
    @Bind(R.id.tvGenjinTimeLeft)
    AppCompatTextView tvGenjinTimeLeft;
    @Bind(R.id.tvExtra)
    AppCompatTextView tvExtra;
    @Bind(R.id.tvExtraLeft)
    AppCompatTextView tvExtraLeft;
    @Bind(R.id.ivHeader)
    ImageView ivHeader;
    @Bind(R.id.tvTaskName)
    AppCompatTextView tvTaskName;
    @Bind(R.id.tvCustomer)
    AppCompatTextView tvCustomer;
    @Bind(R.id.ctvStatus)
    CustomTextView ctvStatus;
    private WaitingItemWrapper.WaitingItem item;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_task_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void setData() {
        setTitle("跟进");
        item = (WaitingItemWrapper.WaitingItem) getIntent().getSerializableExtra("item");
        if (item != null) {
            String followType = item.getFollowType();
            if ("4".equals(followType)) {
                ivHeader.setImageResource(R.mipmap.genjin);
                tvGenjinTimeLeft.setText("跟进时间");
                tvExtraLeft.setText("特殊说明");
                setTitle("跟进");
                tvGenjinTime.setText(DateTimeUtils.formatMillsStr(item.getNextShopDate(),DateTimeUtils.YYYYMMDD) +" "+ (TextUtils.isEmpty(item.getNextShopTime())?"":item.getNextShopTime()));
                tvExtra.setText(item.getSpecialInstruction());
            } else if ("5".equals(followType)) {
                ivHeader.setImageResource(R.mipmap.daodian);
                tvGenjinTimeLeft.setText("到店时间");
                setTitle("客户到店");
                tvGenjinTime.setText(DateTimeUtils.formatMillsStr(item.getOnShopDate(),DateTimeUtils.YYYYMMDD)+" "+(TextUtils.isEmpty(item.getNextShopTime())?"":item.getNextShopTime()));
                tvExtraLeft.setVisibility(View.GONE);
                tvExtra.setVisibility(View.GONE);
            } else if ("6".equals(followType)) {
                tvGenjinTimeLeft.setText("分配时间");
                tvExtraLeft.setText("分配人");
                setTitle("安排跟进");
                tvExtra.setText(item.getCreateUser());
                ivHeader.setImageResource(R.mipmap.anpai);
                tvGenjinTime.setText(DateTimeUtils.formatMillsStr(item.getNextShopDate(),DateTimeUtils.YYYYMMDDHHMM));
            }
            tvTaskName.setText(item.getCustomerName());
            String customerName = item.getCustomerName();
            if(!TextUtils.isEmpty(item.getLinkMan()))
                customerName = item.getLinkMan();
            tvTaskName.setText(customerName);
            String customerStatus = item.getCustomerStatus();
            String customerLevel = item.getPreCharDate();
            tvCustomer.setText(customerStatus + " " + (TextUtils.isEmpty(customerLevel)?"无等级":customerLevel));
//            tvExtra.setText(item.getSpecialInstruction());
            int followUpItemState = item.getFollowUpItemState();
            if(followUpItemState==0){
                ctvStatus.setStrokeColorAndWidth(1,R.color.colorPrimary);
                ctvStatus.setSelectedTextColor(R.color.colorPrimary,R.color.colorPrimary);
                ctvStatus.setText("待处理");
            }else{
                ctvStatus.setText("已完成");
                ctvStatus.setStrokeColorAndWidth(1,R.color.common_gray_light);
                ctvStatus.setSelectedTextColor(R.color.common_gray_light,R.color.common_gray_light);
            }
  /*          int isRead = item.getIsRead();
            if (isRead == 0) {
                Map<String, String> params = new HashMap<>();
                params.put("op", "changRead");
                params.put("FollowupId", String.valueOf(item.getFollowupId()));
                Map<String, Object> map = new HashMap<>();
                map.putAll(params);
                String sign = MD5Utils.getMD5Sign(map);
                params.put("sign", sign);
                presenter.changeTaskItemStatus(params);
            }*/
        }
    }
}
