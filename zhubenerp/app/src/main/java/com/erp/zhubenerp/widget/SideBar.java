package com.erp.zhubenerp.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.erp.zhubenerp.R;
/**
 * Created by gubin on 2018/1/2.
 */

public class SideBar extends View{
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;

    public static String[] b = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","#"};
    private int choose = -1;
    private Paint paint = new Paint();

    private TextView mTextDialog;

    public void setTextView(TextView TextDialog) {
        this.mTextDialog = TextDialog;
    }

    public SideBar (Context context, AttributeSet attrs, int defStyle) {
        super(context,attrs,defStyle);
    }

    public SideBar (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideBar(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int height = getHeight() - 10;
        int width = getWidth();
        int singleHeight = height / b.length;

        for (int i = 0; i < b.length; i++) {
            paint.setColor(Color.BLUE);
            paint.setTypeface(Typeface.DEFAULT);
            paint.setAntiAlias(true);
            paint.setTextSize(16);

            if (i == choose) {
                paint.setColor(Color.parseColor("#ffffff"));
                paint.setFakeBoldText(true);
            }
            float xPos = width / 2 - paint.measureText(b[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(b[i], xPos, yPos, paint);
            paint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int)(y / getHeight() * b.length);

        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackgroundDrawable(new ColorDrawable(0x00000000));
                choose = -1;
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                setBackgroundResource(R.drawable.sidebar_background);
                if (oldChoose != c) {
                    if (c >= 0 && c < b.length) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(b[c]);
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(b[c]);
                            mTextDialog.setVisibility(View.VISIBLE);
                        }
                        choose = c;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener {
        public void onTouchingLetterChanged(String s);
    }
}
