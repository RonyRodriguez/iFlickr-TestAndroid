package com.etermax.iflickr.tool;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.etermax.iflickr.R;

/**
 * Created by rnet_ on 22/03/2017.
 */

public class AnimTool {

    public static Animation fade_in(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.fade_in);
    }

    public static Animation blink(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.blink);
    }

    public static Animation bounce(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.bounce);
    }

    public static Animation slide(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.slide);
    }

    public static Animation slide_down(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.slide_down);
    }

    public static Animation slide_up(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.slide_up);
    }

    public static Animation rotate(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.rotate);
    }

}
