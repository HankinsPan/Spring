package spr.dev.util;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by hanki on 2017/2/20.
 */

public class MyTextWatcher implements TextWatcher {

    private static final String TAG = "MyTextWatcher";

    int cursor = 0;
    int before_length;
    private int limit;
    private EditText editText;
    private Context context;

    public MyTextWatcher(EditText editText, int limit, Context context) {
        this.editText = editText;
        this.limit = limit;
        this.context = context;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        before_length = s.length();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        cursor = start;
        Log.e(TAG, "-- cursor is at -> -- " + cursor);
    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.e(TAG, "-- edtText have word  --> " + s.length());

        int after_length = s.length();
        if (after_length > limit) {
            int d_value = after_length - limit;
            int d_num = after_length - before_length;

            int st = cursor + (d_num - d_value);
            int en = cursor + d_num;

            Editable s_new = s.delete(st, en);
            editText.setText(s_new.toString());
            editText.setSelection(st);

            ShowToast.ColorToast((Activity) context, "Beyond Limits Words", 1500);

        }

    }
}
