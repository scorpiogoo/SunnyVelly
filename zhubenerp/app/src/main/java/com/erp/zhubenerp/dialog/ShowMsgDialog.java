package com.erp.zhubenerp.dialog;


import android.app.Dialog;
import android.content.Context;
/**
 * Created by gubin on 2018/1/5.
 */

public class ShowMsgDialog extends Dialog{
    private ShowMsgDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public ShowMsgDialog(Context context, int theme) {
        super(context, theme);
    }

    private ShowMsgDialog(Context context) {
        super(context);
    }
}
