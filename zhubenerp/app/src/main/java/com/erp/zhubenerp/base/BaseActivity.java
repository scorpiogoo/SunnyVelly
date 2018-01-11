package com.erp.zhubenerp.base;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.erp.zhubenerp.R;

import com.erp.zhubenerp.app.ZhubenerpApp;
import com.erp.zhubenerp.global.Constant;
import com.erp.zhubenerp.utils.MyToast;
import com.erp.zhubenerp.utils.ShowDialogTool;
import com.erp.zhubenerp.utils.UIUtils;
import com.erp.zhubenerp.view.IBaseView;


/**
 * Created by gubin on 2018/1/5.
 */

public abstract class BaseActivity extends AppCompatActivity implements OnClickListener, IBaseView{
    public final String TAG = BaseActivity.this.getClass().getName();
    private TextView mTvTitle;
    private TextView mTvBack;
    private TextView mTvRight;
    private ImageView mIvRight;
    private ImageView mIvBack;
    protected RelativeLayout rlTitle;
    public DisplayMetrics mDisplayMetrics;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivLeft || v.getId() == R.id.tvLeft) {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZhubenerpApp.getAppContext().add(this);
        mDisplayMetrics = getResources().getDisplayMetrics();
        initViews(savedInstanceState);
        mTvTitle = (TextView) findViewById(R.id.tvTitle);
        mTvRight = (TextView) findViewById(R.id.tvRight);
        mIvRight = (ImageView) findViewById(R.id.ivRight);
        mIvBack = (ImageView) findViewById(R.id.ivLeft);
        mTvBack = (TextView) findViewById(R.id.tvLeft);
        rlTitle = (RelativeLayout) findViewById(R.id.rlTitle);

        if(mTvRight != null) mTvRight.setOnClickListener(this);
        if(mIvRight != null) mIvRight.setOnClickListener(this);
        if(mIvBack != null) mIvBack.setOnClickListener(this);
        if(mTvBack != null) mTvBack.setOnClickListener(this);

        setData();
    }

    protected void hideTitle() {
        rlTitle.setVisibility(View.GONE);
    }

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void setData();

    protected void showBack(boolean isShow) {
        if (isShow) {
            mIvBack.setVisibility(View.VISIBLE);
        } else {
            mIvBack.setVisibility(View.GONE);
        }
    }

    protected void setTextLeft(String text) {
        if (mIvRight != null) {
            mIvBack.setVisibility(View.GONE);
        }

        mTvBack.setVisibility(View.VISIBLE);
        mTvBack.setText(text);
    }

    protected void setTextLeft(int res) {
        setTextLeft(UIUtils.getString(res));
    }

    public void showLoading() {

    }

    public void hideLoading() {

    }

    protected void setTitle(String title) {
        mTvTitle.setText(title);
    }

    @Override
    public void setTitle(int textId) {
        mTvTitle.setText(textId);
    }

    protected void setImageRight(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        mIvRight.setVisibility(View.VISIBLE);
        mIvRight.setImageDrawable(drawable);
    }

    protected void setTextRight(int resId) {
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText(resId);
    }

    protected void setTextRight(String text) {
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText(text);
    }

    protected void setTextRightColor(int color) {
        mTvRight.setTextColor(UIUtils.getColor(color));
    }

    protected void jump(Class<?> clazz,boolean needFinish) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        if (needFinish) {
            finish();
        }
    }

    protected void jump(Intent intent, boolean needFinish) {
        startActivity(intent);
        if (needFinish) {
            finish();
        }
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        ZhubenerpApp.getAppContext().remove(this);
        super.onDestroy();
    }

    public Intent newIntent(Class clazz) {
        return new Intent(this, clazz);
    }

    @Override
    public void showError(String error) {
        if (!TextUtils.isEmpty(error)) {
            MyToast.showLong(error);
        }
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }


}
