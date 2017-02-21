package spr.dev.view.adapter;

import android.annotation.TargetApi;
import android.view.View;

/**
 * Created by hanki on 2017/2/21.
 */

@TargetApi(16)
public class SDK16 {

    public static void postOnAnimation(View view, Runnable r) {
        view.postOnAnimation(r);
    }
}
