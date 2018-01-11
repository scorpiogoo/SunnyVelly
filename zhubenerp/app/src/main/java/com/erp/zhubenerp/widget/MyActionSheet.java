package com.erp.zhubenerp.widget;


import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.lang.reflect.Method;
/**
 * Created by gubin on 2018/1/1.
 */

public class MyActionSheet extends Fragment implements View.OnClickListener{

    private static final String ARG_CANCEL_BUTTON_TITLE = "cancel_button_title";
    private static final String ARG_OTHER_BUTTON_TITLES = "other_button_titles";
    private static final String ARG_TITLE_TEXT = "title_text";
    private static final String ARG_CANCELABLE_ONTOUCHOUTSIDE = "cancelable_ontouchoutside";
    private static final int CANCEL_BUTTON_ID = 100;
    private static final int BG_VIEW_ID = 10;
    private static final int TRANSLATE_DURATION = 200;
    private static final int ALPHA_DURATION = 300;

    private static final String EXTRA_DISMISSED = "extra_dismissed";

    private boolean mDismissed = true;
    private ActionSheetListener mListener;
    private View mView;
    private LinearLayout mPanel;
    private ViewGroup mGroup;
    private View mBg;
    private Attributes mAttrs;
    private boolean isCancel = true;

    public void show(final FragmentManager manager, final String tag) {
        if (!mDismissed || manager.isDestroyed()) {
            return;
        }
        mDismissed = false;
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction ft = manager.beginTransaction();
                ft.add(MyActionSheet.this, tag);
                ft.addToBackStack(null);
                ft.commitAllowingStateLoss();
            }
        });
    }

    public void dismiss() {
        if (mDismissed) {
            return;
        }
        mDismissed = true;
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                getFragmentManager().popBackStack();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.remove(MyActionSheet.this);
                ft.commitAllowingStateLoss();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mDismissed = savedInstanceState.getBoolean(EXTRA_DISMISSED);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) {
            View focusView = getActivity().getCurrentFocus();
            if (focusView != null) {
                imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
            }
        }

        mAttrs = readAttribute();

        mView = createView();
        mGroup = (ViewGroup) getActivity().getWindow().getDecorView();

        createItems();

        mGroup.addView(mView);

        mBg.startAnimation(createAlphaInAnimation());
        mPanel.startAnimation(createTranslationInAnimation());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        mPanel.startAnimation(createTranslationOutAnimation());
        mBg.startAnimation(createAlphaOutAnimation());
        mView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mGroup.removeView(mView);
            }
        },ALPHA_DURATION);
        if (mListener != null) {
            mListener.onDismiss(this,isCancel);
        }

        super.onDestroyView();
    }

    private Animation createTranslationInAnimation() {
        int type = TranslateAnimation.RELATIVE_TO_SELF;
        TranslateAnimation ta = new TranslateAnimation(type,0,type,0,type,1,type,0);
        ta.setDuration(TRANSLATE_DURATION);
        return ta;
    }

    private Animation createAlphaInAnimation() {
        AlphaAnimation aa = new AlphaAnimation(0,1);
        aa.setDuration(ALPHA_DURATION);
        return aa;
    }

    private Animation createTranslationOutAnimation() {
        int type = TranslateAnimation.RELATIVE_TO_SELF;
        TranslateAnimation ta = new TranslateAnimation(type,0,type,0,type,0,type,0);
        ta.setDuration(TRANSLATE_DURATION);
        ta.setFillAfter(true);
        return ta;
    }

    private Animation createAlphaOutAnimation() {
        AlphaAnimation aa = new AlphaAnimation(1,0);
        aa.setDuration(ALPHA_DURATION);
        aa.setFillAfter(true);
        return aa;
    }

    private View createView() {
        FrameLayout parent = new FrameLayout(getActivity());
        parent.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT));
        mBg = new View(getActivity());
        mBg.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT));
        mBg.setBackgroundColor(Color.argb(136,0,0,0));
        mBg.setId(MyActionSheet.BG_VIEW_ID);
        mBg.setOnClickListener(this);

        mPanel  = new LinearLayout(getActivity());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        mPanel.setLayoutParams(params);
        mPanel.setOrientation(LinearLayout.VERTICAL);
        parent.setPadding(0,0,0,getNavBarHeight(getActivity()));
        parent.addView(mBg);
        parent.addView(mPanel);
        return parent;
    }

    public int getNavBarHeight(Context c) {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            boolean hasMenuKey = ViewConfiguration.get(c).hasPermanentMenuKey();
            boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

            if(!hasMenuKey && !hasBackKey) {
                Resources resources = c.getResources();

                int orientation = getResources().getConfiguration().orientation;
                int resourceId;
                if (isTablet(c)) {
                    resourceId = resources.getIdentifier(orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_height_landscape","dimen","android");

                } else {
                    resourceId = resources.getIdentifier(orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_width","dimen","android");
                }

                if (resourceId > 0) {
                    return getResources().getDimensionPixelOffset(resourceId);
                }
            }
        }
        return result;
    }

    private boolean isTablet(Context c) {
        return (c.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    private void createItems() {
        String[] titles = getOtherButtonTitles();
        String title = getTitleText();
        if (!TextUtils.isEmpty(title)) {
            TextView bt = new TextView(getActivity());
            bt.setTextColor(Color.GRAY);
            bt.setGravity(Gravity.CENTER);
            bt.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            bt.setBackgroundDrawable(getOtherButtonBg(titles, 0));
            bt.setText(title);
            mPanel.addView(bt);
        }
        if (titles != null) {
            for (int i = 0; i < titles.length; i++) {
                Button bt = new Button(getActivity());
                bt.setId(CANCEL_BUTTON_ID + i + 1);
                bt.setOnClickListener(this);
                bt.setTextColor(mAttrs.otherButtonTextColor);
                bt.setTextSize(TypedValue.COMPLEX_UNIT_PX, mAttrs.actionSheetTextSize);
                bt.setBackgroundDrawable(getOtherButtonBg(titles, i));
                bt.setText(titles[i]);
                if (i > 0) {
                    LinearLayout.LayoutParams params = createButtonLayoutParams();
                    params.topMargin = mAttrs.otherButtonSpacing;
                    mPanel.addView(bt, params);
                } else {
                    mPanel.addView(bt);
                }
            }
        }
        Button bt = new Button(getActivity());
        bt.getPaint().setFakeBoldText(true);
        bt.setTextSize(TypedValue.COMPLEX_UNIT_PX,mAttrs.actionSheetTextSize);
        bt.setId(MyActionSheet.CANCEL_BUTTON_ID);
        bt.setBackgroundDrawable(mAttrs.cancelButtonBackground);
        bt.setText(getCancelButtonTitle());
        bt.setTextColor(mAttrs.cancelButtonTextColor);
        bt.setOnClickListener(this);
        LinearLayout.LayoutParams params = createButtonLayoutParams();
        params.topMargin = mAttrs.cancelButtonMarginTop;

        Rect rect = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int winHeight = getActivity().getWindow().getDecorView().getHeight();
        int virtualKeyHeight = winHeight - rect.bottom;
        int realHeight = getBottomStatusHeight(getActivity());
        params.bottomMargin = realHeight;
        mPanel.setFitsSystemWindows(true);
        mPanel.addView(bt, params);

        mPanel.setBackgroundDrawable(mAttrs.background);
        mPanel.setPadding(mAttrs.padding, mAttrs.padding,mAttrs.padding,mAttrs.padding);
    }

    public LinearLayout.LayoutParams createButtonLayoutParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        return params;
    }

    private Drawable getOtherButtonBg(String[] titles, int i) {
        if (titles.length == 1) {
            return mAttrs.otherButtonSingleBackground;
        }

        if (titles.length == 2) {
            switch (i) {
                case 0:
                    return mAttrs.otherButtonTopBackground;
                case 1:
                    return mAttrs.otherButtonBottomBackground;
            }
        }

        if(titles.length > 2) {
            if (i == 0) {
                return mAttrs.otherButtonTopBackground;
            }
            if (i == (titles.length -1)) {
                return mAttrs.otherButtonBottomBackground;
            }
            return mAttrs.getOtherButtonMiddleBackground();
        }
        return null;
    }

    private Attributes readAttribute() {
        Attributes attrs = new Attributes(getActivity());
        TypedArray a = getActivity().getTheme().obtainStyledAttributes(null,com.baoyz.actionsheet.R.styleable.ActionSheet,com.baoyz.actionsheet.R.attr.actionSheetStyle, 0);
        Drawable background = a.getDrawable(com.baoyz.actionsheet.R.styleable.ActionSheet_actionSheetBackground);
        if (background != null) {
            attrs.background = background;
        }
        Drawable cancelButtonBackground = a.getDrawable(com.baoyz.actionsheet.R.styleable.ActionSheet_cancelButtonBackground);
        if (cancelButtonBackground != null) {
            attrs.cancelButtonBackground = cancelButtonBackground;
        }

        Drawable otherButtonTopBackground = a.getDrawable(com.baoyz.actionsheet.R.styleable.ActionSheet_otherButtonTopBackground);
        if (otherButtonTopBackground != null) {
            attrs.otherButtonTopBackground = otherButtonTopBackground;
        }

        Drawable otherButtonMiddleBackground = a.getDrawable(com.baoyz.actionsheet.R.styleable.ActionSheet_otherButtonMiddleBackground);
        if (otherButtonMiddleBackground != null) {
            attrs.otherButtonMiddleBackground = otherButtonMiddleBackground;
        }

        Drawable otherButtonBottomBackground = a.getDrawable(com.baoyz.actionsheet.R.styleable.ActionSheet_otherButtonBottomBackground);
        if (otherButtonBottomBackground != null) {
            attrs.otherButtonBottomBackground = otherButtonBottomBackground;
        }

        Drawable otherButtonSingleBackground = a.getDrawable(com.baoyz.actionsheet.R.styleable.ActionSheet_otherButtonSingleBackground);
        if (otherButtonSingleBackground != null) {
            attrs.otherButtonSingleBackground = otherButtonSingleBackground;
        }

        attrs.cancelButtonTextColor = a.getColor(com.baoyz.actionsheet.R.styleable.ActionSheet_cancelButtonTextColor,attrs.cancelButtonTextColor);

        attrs.otherButtonTextColor = a.getColor(com.baoyz.actionsheet.R.styleable.ActionSheet_otherButtonTextColor,attrs.otherButtonTextColor);

        attrs.padding = (int) a.getDimension(com.baoyz.actionsheet.R.styleable.ActionSheet_actionSheetPadding, attrs.padding);

        attrs.otherButtonSpacing = (int) a.getDimension(com.baoyz.actionsheet.R.styleable.ActionSheet_otherButtonSpacing,attrs.otherButtonSpacing);

        attrs.cancelButtonMarginTop = (int) a.getDimension(com.baoyz.actionsheet.R.styleable.ActionSheet_cancelButtonMarginTop,attrs.cancelButtonMarginTop);

        a.recycle();
        return attrs;
    }

    private String getCancelButtonTitle() {
        return getArguments().getString(ARG_CANCEL_BUTTON_TITLE);
    }

    private String getTitleText() {
        return getArguments().getString(ARG_TITLE_TEXT);
    }

    private String[] getOtherButtonTitles() {
        return getArguments().getStringArray(ARG_OTHER_BUTTON_TITLES);
    }

    private boolean getCancelableOnTouchOutside() {
        return getArguments().getBoolean(ARG_CANCELABLE_ONTOUCHOUTSIDE);
    }

    private static class Attributes {
        private Context mContext;
        Drawable background;
        Drawable cancelButtonBackground;
        Drawable otherButtonTopBackground;
        Drawable otherButtonMiddleBackground;
        Drawable otherButtonBottomBackground;
        Drawable otherButtonSingleBackground;
        int cancelButtonTextColor;
        int otherButtonTextColor;
        int padding;
        int otherButtonSpacing;
        int cancelButtonMarginTop;
        float actionSheetTextSize;


        public Attributes(Context context) {
            mContext = context;
            this.background = new ColorDrawable(Color.TRANSPARENT);
            this.cancelButtonBackground = new ColorDrawable(Color.BLACK);
            ColorDrawable gray = new ColorDrawable(Color.GRAY);
            this.otherButtonTopBackground = gray;
            this.otherButtonMiddleBackground = gray;
            this.otherButtonBottomBackground = gray;
            this.otherButtonSingleBackground = gray;
            this.cancelButtonTextColor = Color.WHITE;
            this.otherButtonTextColor = Color.BLACK;
            this.padding = dp2px(20);
            this.otherButtonSpacing = dp2px(2);
            this.cancelButtonMarginTop = dp2px(10);
            this.actionSheetTextSize = dp2px(16);
        }

        private int dp2px(int dp) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,mContext.getResources().getDisplayMetrics());
        }

        public Drawable getOtherButtonMiddleBackground() {
            if (otherButtonMiddleBackground instanceof StateListDrawable) {
                TypedArray a = mContext.getTheme().obtainStyledAttributes(null,
                        com.baoyz.actionsheet.R.styleable.ActionSheet,com.baoyz.actionsheet.R.attr.actionSheetStyle,0);
                otherButtonMiddleBackground = a.getDrawable(com.baoyz.actionsheet.R.styleable.ActionSheet_otherButtonMiddleBackground);
                a.recycle();
            }
            return otherButtonMiddleBackground;
        }
    }

    public static MyBuilder createBuilder(Context context, FragmentManager fragmentManager) {
        return new MyBuilder(context, fragmentManager);
    }

    public static class MyBuilder {
        private Context mContext;
        private FragmentManager mFragmentManager;
        private String mCancelButtonTitle;
        private String[] mOtherButtonTitles;
        private String mTitleText;
        private String mTag = "actionSheet";
        private boolean mCancelableOnTouchOutside;
        private ActionSheetListener mListener;

        public MyBuilder(Context context, FragmentManager fragmentManager) {
            mContext = context;
            mFragmentManager = fragmentManager;
        }

        public MyBuilder setCancelButtonTitle(String title) {
            mCancelButtonTitle = title;
            return this;
        }

        public MyBuilder setCancelButtonTitle(int strId) {
            return setCancelButtonTitle(mContext.getString(strId));
        }

        public MyBuilder setOtherButtonTitles(String... titles) {
            mOtherButtonTitles = titles;
            return this;
        }

        public MyBuilder setTag(String tag) {
            mTag = tag;
            return this;
        }

        public MyBuilder setTitleText(String titleText) {
            mTitleText = titleText;
            return this;
        }

        public MyBuilder setListener(ActionSheetListener listener) {
            this.mListener = listener;
            return this;
        }

        public MyBuilder setCancelableOnTouchOutside(boolean cancelable) {
            mCancelableOnTouchOutside = cancelable;
            return this;
        }

        public Bundle prepareArguments() {
            Bundle bundle = new Bundle();
            bundle.putString(ARG_CANCEL_BUTTON_TITLE, mCancelButtonTitle);
            bundle.putString(ARG_TITLE_TEXT, mTitleText);
            bundle.putStringArray(ARG_OTHER_BUTTON_TITLES, mOtherButtonTitles);
            bundle.putBoolean(ARG_CANCELABLE_ONTOUCHOUTSIDE, mCancelableOnTouchOutside);
            return bundle;
        }

        public MyActionSheet show() {
            MyActionSheet actionSheet = (MyActionSheet) Fragment.instantiate(mContext, MyActionSheet.class.getName(), prepareArguments());
            actionSheet.setActionSheetListener(mListener);
            actionSheet.show(mFragmentManager, mTag);
            return actionSheet;
        }
    }

    public void setActionSheetListener(ActionSheetListener listener) {
        mListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == MyActionSheet.BG_VIEW_ID && !getCancelableOnTouchOutside()) {
            return;
        }
        dismiss();
        if(v.getId() != MyActionSheet.CANCEL_BUTTON_ID && v.getId() != MyActionSheet.BG_VIEW_ID) {
            if (mListener != null) {
                mListener.onOtherButtonClick(this,v.getId() - CANCEL_BUTTON_ID - 1);
            }
            isCancel = false;
        }
    }

    public static interface ActionSheetListener {
        void onDismiss(MyActionSheet actionSheet, boolean isCancel);
        void onOtherButtonClick(MyActionSheet actionSheet, int index);
    }

    public static int getDpi(Context context) {
        int dpi = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
                Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
                    Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi = displayMetrics.heightPixels;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    public static int getBottomStatusHeight(Context context) {
        int totalHeight = getDpi(context);

        int contentHeight = getScreenHeight(context);

        return totalHeight - contentHeight;
    }

    public static int getTitleHeight(Activity activity) {
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
    }

    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
}
