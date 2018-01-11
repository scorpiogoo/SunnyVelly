package com.erp.zhubenerp.widget.bottombar;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.concurrent.atomic.AtomicInteger;
/**
 * Created by gubin on 2018/1/2.
 */

@SuppressWarnings("unused")
public class BottomTabGroup extends LinearLayout{
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    private int mCheckedId = -1;

    private BottomTabImpl.OnCheckedChangeListener mChildOnCheckedChangeListener;

    private boolean mProtectFromCheckedChange = false;
    private OnCheckedChangeListener mOnCheckedChangeListener;

    private PassThroughHierarchyChangeListener mPassThroughListener;

    public BottomTabGroup(Context context) {
        this(context, null);
    }

    public BottomTabGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    private void init() {
        super.setOrientation(HORIZONTAL);
        mChildOnCheckedChangeListener = new CheckedStateTracker();

        mPassThroughListener = new PassThroughHierarchyChangeListener();
        super.setOnHierarchyChangeListener(mPassThroughListener);
    }

    public void check(int id) {
        if (id != -1 && id != mCheckedId) {
            clearCheck();
            setCheckedStateForView(id,true);
            setCheckedId(id);
        }
    }

    public int getCheckedChildId() {
        return mCheckedId;
    }

    public void clearCheck() {
        if (mCheckedId != -1) {
            setCheckedStateForView(mCheckedId,false);
        }
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }

    @Override
    public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
        mPassThroughListener.mOnHierarchyChangeListener = listener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (mCheckedId != -1) {
            mProtectFromCheckedChange = true;
            setCheckedStateForView(mCheckedId, true);
            mProtectFromCheckedChange = false;
            setCheckedId(mCheckedId);
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof BottomTabImpl) {
            final BottomTabImpl button = (BottomTabImpl) child;
            if (button.isChecked()) {
                mProtectFromCheckedChange = true;
                if (mCheckedId != -1) {
                    setCheckedStateForView(mCheckedId, false);
                }
                mProtectFromCheckedChange = false;
                setCheckedId(button.getId());
            }
        }
        super.addView(child, index, params);
    }

    private void setCheckedId(int id) {
        mCheckedId = id;
        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChanged(this, mCheckedId);
        }
    }

    private void setCheckedStateForView(int viewId, boolean checked) {
        View checkedView = findViewById(viewId);
        if (checkedView != null && checkedView instanceof BottomTabImpl) {
            ((BottomTabImpl) checkedView).setChecked(checked);
        }
    }

    @Override
    public LinearLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new BottomTabGroup.LayoutParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof BottomTabGroup.LayoutParams;
    }

    @Override
    protected LinearLayout.LayoutParams generateDefaultLayoutParams() {
        return new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    public static class LayoutParams extends LinearLayout.LayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int w, int h) {
            super(w,h);
        }

        public LayoutParams(int w, int h, float initWeight) {
            super(w, h, initWeight);
        }

        public LayoutParams(ViewGroup.LayoutParams p) {
            super(p);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        @Override
        protected void setBaseAttributes(TypedArray a, int widthAttr, int heightAttr) {
            if (a.hasValue(widthAttr)) {
                width = a.getLayoutDimension(widthAttr, "Layout_width");
            } else {
                width = WRAP_CONTENT;
            }

            if (a.hasValue(heightAttr)) {
                height = a.getLayoutDimension(heightAttr, "Layout_height");
            } else {
                height = WRAP_CONTENT;
            }
        }
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(BottomTabGroup root, int checkedId);
    }

    private class CheckedStateTracker implements BottomTabImpl.OnCheckedChangeListener{
        public void onCheckedChanged(BottomTabImpl buttonView, boolean isChecked) {
            if (mProtectFromCheckedChange) {
                return;
            }

            mProtectFromCheckedChange = true;
            if(mCheckedId != -1) {
                setCheckedStateForView(mCheckedId, false);
            }

            mProtectFromCheckedChange = false;

            int id = buttonView.getId();
            setCheckedId(id);
        }
    }

    private class PassThroughHierarchyChangeListener implements OnHierarchyChangeListener {

        private OnHierarchyChangeListener mOnHierarchyChangeListener;

        public void onChildViewAdded(View parent, View child) {

            if (parent == BottomTabGroup.this && child instanceof BottomTabImpl) {
                int id = child.getId();

                if (id == View.NO_ID) {
                    id = generateViewId();
                    child.setId(id);
                }

                ((BottomTabImpl) child).setOnCheckedChangeWidgetListener(mChildOnCheckedChangeListener);
            }

            if (mOnHierarchyChangeListener != null)
                mOnHierarchyChangeListener.onChildViewAdded(parent, child);
        }

        public void onChildViewRemoved(View parent, View child) {
            if (parent == BottomTabGroup.this && child instanceof BottomTabImpl) {
                ((BottomTabImpl) child).setOnCheckedChangeWidgetListener(null);
            }

            if (mOnHierarchyChangeListener != null)
                mOnHierarchyChangeListener.onChildViewRemoved(parent,child);
        }



    }

    public static int generateViewId() {
        if (Build.VERSION.SDK_INT < 17) {
            for(;;) {
                final int result = sNextGeneratedId.get();
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF)
                    newValue = 1;
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        }
        else {
            return generateViewId();
        }
    }


}
