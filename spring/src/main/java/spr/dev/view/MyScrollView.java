package spr.dev.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.ScrollView;

/**
 * Created by hanki on 2017/2/20.
 */

public class MyScrollView extends ScrollView {
    private Context mContext;

    public MyScrollView(Context context) {
        super(context);
        init(mContext);
    }


    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mContext =context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        try {
            Display display = ((Activity)mContext).getWindowManager().getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            display.getMetrics(displayMetrics);

            heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                    displayMetrics.heightPixels/4,
                    MeasureSpec.AT_MOST);
        }catch (Exception e){
            e.printStackTrace();
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
