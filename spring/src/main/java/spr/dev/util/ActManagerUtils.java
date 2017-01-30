package spr.dev.util;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by hanki on 2017/1/20.
 */

public class ActManagerUtils {
    private static ActManagerUtils actManagerUtils;
    private ArrayList<Activity> activityList = new ArrayList<Activity>();

    private ActManagerUtils() {

    }

    public static ActManagerUtils getInstance() {
        if (actManagerUtils == null) {
            actManagerUtils = new ActManagerUtils();
        }
        return actManagerUtils;
    }

    public Activity getTopActivity() {
        return activityList.get(activityList.size() - 1);
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void removeAllActivity() {
        for (Activity activity : activityList) {
            if (activity != null) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
                activity = null;
            }
        }
        activityList.clear();
    }

}

