package com.erp.zhubenerp.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.erp.zhubenerp.R;

/**
 * Created by gubin on 2018/1/5.
 */

public class CircleHeadView extends View{
    private String firstName = "";

    private Paint borderPaint;
    private Paint textPaint;

    private int widgetWidth = 120;
    private int widgetHeight = 120;

    private int circleRadius = 60;

    private int bgColor = Color.parseColor("#2196FC");

    public CircleHeadView(Context context) {
        this(context, null);
    }

    public CircleHeadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(60);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setColor(Color.WHITE);

        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setStyle(Paint.Style.FILL);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                widgetHeight = getHeight();
                widgetWidth = getWidth();
                circleRadius = Math.max(widgetHeight, widgetWidth) / 2;
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        borderPaint.setColor(bgColor);
        canvas.drawCircle(widgetWidth * 0.5f, widgetHeight * 0.5f, circleRadius - 1.0f, borderPaint);
        canvas.drawText(firstName, widgetWidth * 0.5f - 30, widgetHeight * 0.5f + 22, textPaint);
    }

    public void setNameText(String firstName, int onlineStatus) {
        if (firstName != null && firstName.length() != 0) {
            this.firstName = firstName.substring(0, 2);
        } else {
            this.firstName = firstName.substring(0,1);
        }

        if (onlineStatus == 0) {
            bgColor = Color.parseColor("#BEBEBE");
        } else {
            bgColor = Color.parseColor("#2196FC");
        }

        invalidate();
    }
}
