package com.erp.zhubenerp.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.erp.zhubenerp.R;

import org.w3c.dom.Attr;

/**
 * Created by gubin on 2018/1/2.
 */

public class EmptyView extends RelativeLayout {
    private TextView tv;

    public EmptyView(Context context) {
        super(context);
        initView(context);
    }

    public EmptyView(Context context, String emptyStr) {
        super(context);
        initView(context);
        setEmptytext(emptyStr);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.empty_view, null);
        tv = (TextView) view.findViewById(R.id.tv_empty_view);
        this.addView(view);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        this.setGravity(Gravity.CENTER);
        this.setLayoutParams(params);
    }

    public void setEmptytext(String string) {
        if (TextUtils.isEmpty(string)) {
            return;
        }
        tv.setText(string);
    }
}
