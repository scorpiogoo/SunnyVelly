package com.erp.zhubenerp.widget.bottombar;

import android.widget.Checkable;
/**
 * Created by gubin on 2018/1/2.
 */

public interface BottomTabImpl extends Checkable{

    interface OnCheckedChangeListener {
        void onCheckedChanged(BottomTabImpl buttonView, boolean isChecked);
    }

    int getId();

    void setOnCheckedChangeWidgetListener(OnCheckedChangeListener listener);
}
