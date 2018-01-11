package com.erp.zhubenerp.widget.bottombar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.erp.zhubenerp.R;
/**
 * Created by gubin on 2018/1/3.
 */

public class BottomTab extends LinearLayout implements BottomTabImpl{

    private final static int DEFAULT_TITLE_TEXT_SIZE = 36;
    private final static int DEFAULT_VERTICAL_SPACE = 8;
    private final static int DEFAULT_HINT_TEXT_SIZE = 12;

    private Drawable mTabDrawable;
    private int mVerticalSpace;

    private CharSequence mTabTitle;
    private int mTabTitleSize;
    private ColorStateList mTabTextColors;

    private CharSequence mHintText;
    private int mHintTextSize;
    private ColorStateList mHintTextColors;
    private Drawable mHintBackgroundDrawable;
    private boolean mHintEnable;

    private TextView mTvHint;
    private RadioImageView mRivTabIcon;
    private RadioTextView mRtvTabTitle;

    private boolean mChecked = false;

    private OnCheckedChangeListener mOnCheckedChangeWidgetListener;

    public BottomTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs == null) {
            throw new NullPointerException();
        }

        TypedArray tTypedArray = null;

        try {
            tTypedArray = context.obtainStyledAttributes(attrs, R.styleable.BottomTab);

            mTabDrawable = tTypedArray.getDrawable(R.styleable.BottomTab_drawableTop);
            mVerticalSpace = tTypedArray.getDimensionPixelSize(R.styleable.BottomTab_verticalSpace, DEFAULT_VERTICAL_SPACE);

            mTabTextColors = tTypedArray.getColorStateList(R.styleable.BottomTab_barTextColor);
            mTabTitle = tTypedArray.getText(R.styleable.BottomTab_tabText);
            mTabTitleSize = tTypedArray.getDimensionPixelSize(R.styleable.BottomTab_barTextSize, DEFAULT_TITLE_TEXT_SIZE);

            mHintEnable = tTypedArray.getBoolean(R.styleable.BottomTab_hintEnable, true);
            mHintText = tTypedArray.getText(R.styleable.BottomTab_hintText);
            mHintTextSize = tTypedArray.getDimensionPixelSize(R.styleable.BottomTab_hintTextSize, DEFAULT_HINT_TEXT_SIZE);
            mHintTextColors = tTypedArray.getColorStateList(R.styleable.BottomTab_hintTextColor);
            mHintBackgroundDrawable = tTypedArray.getDrawable(R.styleable.BottomTab_hintTextBackground);
        } finally {
            if (tTypedArray != null) tTypedArray.recycle();
        }

        this.initViews();
    }

    private void initViews() {
        super.setOrientation(VERTICAL);
        inflate(getContext(), getLayoutRes(), this);

        mTvHint = (TextView) findViewById(R.id.tab_hint);
        mRivTabIcon = (RadioImageView) findViewById(R.id.tab_icon);
        mRtvTabTitle = (RadioTextView) findViewById(R.id.tab_title);

        mRivTabIcon.setImageDrawable(mTabDrawable);
        mRtvTabTitle.setText(mTabTitle);
        mRtvTabTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabTitleSize);
        if (mTabTextColors != null) mRtvTabTitle.setTextColor(mTabTextColors);
        mRivTabIcon.setClickable(false);
        mRtvTabTitle.setClickable(false);

        mTvHint.setText(mHintText);
        mTvHint.setTextSize(mHintTextSize);
        if (mHintTextColors != null) mTvHint.setTextColor(mHintTextColors);
        mTvHint.setBackgroundDrawable(mHintBackgroundDrawable);
        mTvHint.setVisibility(mHintEnable ? View.VISIBLE : View.INVISIBLE);

        LayoutParams tTabtextLp = (LayoutParams) mRtvTabTitle.getLayoutParams();
        tTabtextLp.topMargin = mVerticalSpace;
        mRtvTabTitle.setLayoutParams(tTabtextLp);

        this.setClickable(true);
    }

    @Override
    public boolean performClick() {
        if (!mChecked) {
            setChecked(true);
            return true;
        }

        return super.performClick();
    }

    public int getLayoutRes() {
        return R.layout.bottom_tab_main;
    }

    @Override
    public int getId() {
        return super.getId();
    }

    public BottomTab setTabTitles(String text) {
        mRtvTabTitle.setText(text);
        return this;
    }

    public BottomTab setTabDrawable(int drawableResId) {
        mRivTabIcon.setImageDrawable(getResources().getDrawable(drawableResId));
        return this;
    }

    public BottomTab setTabDrawable(Drawable drawable) {
        mRivTabIcon.setImageDrawable(drawable);
        return this;
    }

    public BottomTab setHint(String msg) {
        mTvHint.setText(msg);
        return this;
    }

    public BottomTab setHintBackgroundResource(int res) {
        mHintBackgroundDrawable = getResources().getDrawable(res);
        mTvHint.setBackgroundDrawable(mHintBackgroundDrawable);
        return this;
    }

    public BottomTab setHintTextColor(int color) {
        mHintTextColors = ColorStateList.valueOf(color);
        mTvHint.setTextColor(mHintTextColors);
        return this;
    }

    public BottomTab setHintEnable(boolean enable) {
        this.mHintEnable = enable;
        mTvHint.setVisibility(mHintEnable ? View.VISIBLE : View.INVISIBLE);
        return this;
    }

    @Override
    public void setOnCheckedChangeWidgetListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeWidgetListener = listener;
    }

    @Override
    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            mRivTabIcon.setChecked(mChecked);
            mRtvTabTitle.setChecked(mChecked);
            mTvHint.refreshDrawableState();
            mOnCheckedChangeWidgetListener.onCheckedChanged(this, checked);
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
    }
}
