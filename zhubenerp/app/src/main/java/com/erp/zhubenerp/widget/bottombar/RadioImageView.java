package com.erp.zhubenerp.widget.bottombar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.ImageView;

/**
 * Created by gubin on 2018/1/3.
 */

public class RadioImageView extends ImageView implements Checkable{

    private boolean mChecked = false;

    private boolean mBroadcasting;
    private OnCheckedChangeListener mOnCheckedChangeListener;

    public interface OnCheckedChangeListener {
        void onCheckedChanged(RadioImageView pView, boolean isChecked);
    }

    int[] CHECKED_STATE_SET = {android.R.attr.state_checked};

    public RadioImageView(Context context) {
        this(context, null);
    }

    public RadioImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        this.setClickable(true);
    }

    @Override
    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            refreshDrawableState();
        }

        if (mBroadcasting)
            return;

        mBroadcasting = true;
        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChanged( this, mChecked);
        }
        mBroadcasting = false;
    }

    public void setChecked(boolean checked, boolean broadcast) {
        if (broadcast) this.setChecked(checked);
        else {
            if(mChecked != checked) {
                mChecked = checked;
                refreshDrawableState();
            }
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        this.setChecked(!mChecked);
    }

    @Override
    public boolean performClick() {
        toggle();
        return super.performClick();
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.mOnCheckedChangeListener = listener;
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

}
