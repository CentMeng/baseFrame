package com.android.core.animation;

import android.app.Activity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.android.core.R;

/**
 * View 动画
 *
 * @author mengxc
 */
public class ViewAnimators {

    private long duration = 200;

    public ViewAnimators(long duration) {
        this.duration = duration;
    }

    public ViewAnimators() {
    }

    public void fadeAnimation(View v) {
        v.clearAnimation();
        Animation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation.setDuration(duration);
        v.startAnimation(alphaAnimation);
    }

    public void LargerAnimation(View v, Activity activity) {
        v.clearAnimation();
        Animation alphaAnimation = AnimationUtils.loadAnimation(activity,
                R.anim.grow_larger_anim);
        alphaAnimation.setDuration(duration);
        v.startAnimation(alphaAnimation);
    }

    /**
     * 搜索编辑框出现动画
     *
     * @param v
     * @param activity
     */
    public void search_Anim_in(View v, Activity activity) {
        v.clearAnimation();
        Animation alphaAnimation = AnimationUtils.loadAnimation(activity,
                R.anim.search_anim_in);
        alphaAnimation.setDuration(duration / 2);
        v.startAnimation(alphaAnimation);
    }

    /**
     * 搜索编辑框消失动画
     *
     * @param v
     * @param activity
     */
    public void search_Anim_out(View v, Activity activity) {
        v.clearAnimation();
        Animation alphaAnimation = AnimationUtils.loadAnimation(activity,
                R.anim.search_anim_out);
        alphaAnimation.setDuration(duration / 2);
        v.startAnimation(alphaAnimation);
    }

    /**
     * 按钮抖动动画
     *
     * @param v
     * @param activity
     */
    public void shakeAnimation(View v, Activity activity) {
        Animation shake = AnimationUtils.loadAnimation(activity, R.anim.shake);
        v.startAnimation(shake);
    }

    /**
     * 从小到大动画
     */
    public static void smallToBigAnimation(View v, Activity activity) {
        Animation small = AnimationUtils.loadAnimation(activity, R.anim.small_to_big);
        v.startAnimation(small);
    }

    /**
     * 从大到小动画
     */
    public static void bigToSmallAnimation(View v, Activity activity) {
        Animation small = AnimationUtils.loadAnimation(activity, R.anim.big_to_small);
        v.startAnimation(small);
    }

    /**
     * 顶部进入
     * @param v
     * @param activity
     */
    public static void transInToTop(View v ,Activity activity){
        Animation in = AnimationUtils.loadAnimation(activity, R.anim.trans_top_in);
        v.startAnimation(in);
    }

    /**
     * 顶部出去
     * @param v
     * @param activity
     */
    public static void transOutToTop(View v ,Activity activity){
        Animation out = AnimationUtils.loadAnimation(activity, R.anim.trans_top_out);
        v.startAnimation(out);
    }

}
