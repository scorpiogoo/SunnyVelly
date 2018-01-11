package com.erp.zhubenerp.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.erp.zhubenerp.R;
import com.erp.zhubenerp.utils.LogUtil;
import com.erp.zhubenerp.utils.ShowDialogTool;
import com.erp.zhubenerp.utils.UIUtils;
import com.erp.zhubenerp.view.IBaseView;

/**
 * Created by gubin on 2018/1/5.
 */

public abstract class NewBaseFragment extends Fragment implements View.OnClickListener, IBaseView{

    protected String TAG = NewBaseFragment.this.getClass().getSimpleName();
    private TextView tvTitle;
    private TextView tvLeft;
    private TextView tvRight;
    protected ImageView ivRight;
    private ImageView ivLeft;
    private RelativeLayout rlTitle;
    protected BaseActivity context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (BaseActivity) getActivity();
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initViews(inflater, container, savedInstanceState);
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvRight = (TextView) view.findViewById(R.id.tvRight);
        ivRight = (ImageView) view.findViewById(R.id.ivRight);
        ivLeft = (ImageView) view.findViewById(R.id.ivLeft);
        tvLeft = (TextView) view.findViewById(R.id.tvLeft);
        rlTitle = (RelativeLayout) view.findViewById(R.id.rlTitle);

        if (tvRight != null) tvRight.setOnClickListener(this);
        if (ivRight != null) ivRight.setOnClickListener(this);
        if (ivLeft != null) ivLeft.setOnClickListener(this);
        if (tvLeft != null) tvLeft.setOnClickListener(this);

        return view;
    }

    protected void hideTitle() {
        if (rlTitle != null)
            rlTitle.setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setView();
    }

    protected abstract void setView();

    protected abstract View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void initData();

    protected void showBack(boolean isShow) {
        if (isShow) {
            ivLeft.setVisibility(View.VISIBLE);
        } else {
            ivLeft.setVisibility(View.GONE);
        }
    }

    protected void setTextLeft(String text) {
        if (ivLeft != null) {
            ivLeft.setVisibility(View.GONE);
        }

        tvLeft.setVisibility(View.VISIBLE);
        tvLeft.setText(text);
    }

    protected void setTextLeft(int res) {
        setTextLeft(UIUtils.getString(res));
    }

    protected void setTextRight(String text) {
        if (ivLeft != null) {
            ivLeft.setVisibility(View.GONE);
        }
        tvLeft.setVisibility(View.VISIBLE);
        tvLeft.setText(text);
    }

    protected void setTextRight(int res) {
        setTextLeft(UIUtils.getString(res));
    }

    public void setImageLeft(int res) {
        if (tvLeft != null) {
            tvLeft.setVisibility(View.GONE);
        }
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setImageResource(res);
    }

    public void setImageRight(int res) {
        if (tvRight != null) {
            tvRight.setVisibility(View.GONE);
        }

        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(res);
    }

    protected void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setTitle(int textId) {
        tvTitle.setText(textId);
    }

    protected void jump(Intent intent) {
        startActivity(intent);
    }

    public void showLoading() {
        ShowDialogTool.showLoadingDialog(context);
    }

    public void hideLoading() {
        ShowDialogTool.dismissLoadingDialog();
    }

    @Override
    public void showError(String error) {
        LogUtil.e(TAG, error);
    }

    @Override
    public void showDialog() {
        ShowDialogTool.showLoadingDialog(context);
    }

    @Override
    public void dismissDialog() {
        ShowDialogTool.dismissLoadingDialog();
    }

    @Override
    public void onClick(View v) {

    }
}
