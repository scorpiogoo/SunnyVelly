package com.erp.zhubenerp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.erp.zhubenerp.R;
import com.erp.zhubenerp.base.NewBaseFragment;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
/**
 * Created by gubin on 2018/1/8.
 */

public class MineFragment extends NewBaseFragment{
    @Bind(R.id.sv_mine_container)
    ScrollView mineContainer;
    @Bind(R.id.civ_mine_head)
    CircleImageView headView;
    @Bind(R.id.tv_mine_name)
    TextView userName;
    @Bind(R.id.tv_mine_company)
    TextView userCompany;
    @Bind(R.id.tv_mine_department)
    TextView userDepartment;
    @Bind(R.id.ll_mine_backlog)
    LinearLayout backlogButton;
    @Bind(R.id.ll_mine_mycheckin)
    LinearLayout checkinButton;
    @Bind(R.id.ll_mine_myapply)
    LinearLayout myapplyButton;
    @Bind(R.id.ll_mine_myproject)
    LinearLayout myprojectButton;
    @Bind(R.id.itv_mine_setting)
    IconTextView settingIcon;
    @Bind(R.id.itv_mine_quit)
    IconTextView quitLoginIcon;
    @Override
    protected void setView() {
        //     showBack(false);
        initUserMessage();
    }


    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mine, container, false);
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

    private void initUserMessage() {
        userName.setText("董事长办公室");
        userCompany.setText("铸本集团");
        userDepartment.setText("本部");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_mine_backlog:
                break;
            case R.id.ll_mine_myapply:
                break;
            case R.id.ll_mine_mycheckin:
                break;
            case R.id.ll_mine_myproject:
                break;
            default:
                break;
        }
    }
}
