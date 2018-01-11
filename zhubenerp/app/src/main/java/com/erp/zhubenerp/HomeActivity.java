package com.erp.zhubenerp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.erp.zhubenerp.R;
import com.erp.zhubenerp.app.ZhubenerpApp;
import com.erp.zhubenerp.base.BaseActivity;

import com.erp.zhubenerp.ui.fragment.TaskTabFragment;
import com.erp.zhubenerp.ui.fragment.TaskFragment;
import com.erp.zhubenerp.utils.MyToast;
import com.erp.zhubenerp.widget.bottombar.BottomTab;
import com.erp.zhubenerp.widget.bottombar.BottomTabGroup;
import com.erp.zhubenerp.ui.fragment.DepartmentFragment;
import com.erp.zhubenerp.ui.fragment.MineFragment;
import java.text.DecimalFormat;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements BottomTabGroup.OnCheckedChangeListener {
    @Bind(R.id.bottom_bar_root)
    BottomTabGroup bottomBarRoot;
    private FragmentTransaction transaction;
    private TaskTabFragment taskFragment;
    private DepartmentFragment departmentFragment;
    private MineFragment mineFragment;
    private Fragment fg = departmentFragment;
    private static final int REQUEST_EXTERNAL_STORAGE = 999;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        BottomTab tab04 = (BottomTab) bottomBarRoot.getChildAt(3);
        BottomTab tab02 = (BottomTab) bottomBarRoot.getChildAt(1);
        tab04.setHint("3");
        tab02.setChecked(true);
        bottomBarRoot.setOnCheckedChangeListener(this);
    }

    @Override
    protected void setData(){
        transaction = getSupportFragmentManager().beginTransaction();
        if (departmentFragment == null) {
            departmentFragment = new DepartmentFragment();
        }
        transaction.add(R.id.rlContainer,departmentFragment);
        transaction.commit();
/*        frag = new Fragment();
        transaction.add(R.id.rlContainer,frag);
        transaction.commit();
        */
    }

    private void changeTab(Fragment fragment) {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.rlContainer,fragment);
        transaction.commit();
    }

    @Override
    public void onCheckedChanged(BottomTabGroup root, int checkedId) {
        switch (checkedId) {
            case R.id.tab_01:
                if (taskFragment == null) {
                    taskFragment = new TaskTabFragment();
                }
                changeTab(taskFragment);
                fg = taskFragment;
                break;
            case R.id.tab_02:
                if (departmentFragment == null) {
                    departmentFragment = new DepartmentFragment();
                }
                changeTab(departmentFragment);
                fg = departmentFragment;
                break;
            case R.id.tab_03:
                break;
            case R.id.tab_04:
                if(mineFragment == null) {
                    mineFragment = new MineFragment();
                }
                changeTab(mineFragment);
                fg = mineFragment;
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
