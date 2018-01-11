package com.erp.zhubenerp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.support.v4.app.Fragment;

import com.erp.zhubenerp.R;
import com.erp.zhubenerp.base.NewBaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by gubin on 2018/1/6.
 */

public class DepartmentFragment extends NewBaseFragment{

    @Override
    protected void setView() {
   //     showBack(false);

    }


    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_department, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }



}
