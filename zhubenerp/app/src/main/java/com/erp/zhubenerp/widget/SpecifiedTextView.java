package com.erp.zhubenerp.widget;


import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by gubin on 2018/1/1.
 */

public class SpecifiedTextView extends TextView {
    public SpecifiedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSpecifiedTextsColor(String text, String specifiedTexts, int color) {
        List<Integer> sTextsStartList = new ArrayList<>();

        int sTextLength = specifiedTexts.length();
        String temp = text;
        int lengthFront = 0;
        int start = -1;
        do {
            start = temp.indexOf(specifiedTexts);

            if (start != -1) {
                start = start + lengthFront;
                sTextsStartList.add(start);
                lengthFront = start + sTextLength;
                temp = text.substring(lengthFront);
            }
        }while (start != -1);

        SpannableStringBuilder styledText = new SpannableStringBuilder(text);
        for (Integer i : sTextsStartList) {
            styledText.setSpan(new ForegroundColorSpan(color),i,i + sTextLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        setText(styledText);
    }
}
