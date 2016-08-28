package com.beijing.navi.utils;

import android.app.Activity;

import com.beijing.navi.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mengxc
 */
public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static Activity currentActivity;

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }


    public static void finishAllExceptMain() {
        for (Activity activity : activities) {
            if (!(activity instanceof MainActivity)) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
    }


    public static boolean isExistMainActivity() {
        for (Activity activity : activities) {
            if (activity instanceof MainActivity) {
                return true;
            }
        }
        return false;
    }


    public static void finishActivity(Class<? extends Activity>... clazzs) {
        for (Activity activity : activities) {
            for (Class clazz : clazzs) {
                if (activity.getClass().isAssignableFrom(clazz)) {
                    activity.finish();
                }
            }
        }
    }


    public static boolean isExistActivity(Class<? extends Activity> clazz) {
        for (Activity activity : activities) {
            if (activity.getClass().isAssignableFrom(clazz)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCurrentActivity(Class<? extends Activity> clazz) {
        if (currentActivity != null && currentActivity.getClass().isAssignableFrom(clazz)) {
            return true;
        }
        return false;
    }


}
