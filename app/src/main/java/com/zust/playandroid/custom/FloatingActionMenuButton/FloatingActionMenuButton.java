package com.zust.playandroid.custom.FloatingActionMenuButton;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.FrameLayout;

import com.zust.playandroid.R;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/3
 * 时 间： 14:24
 * 项 目： PlayAndroid
 * 描 述：
 */


public class FloatingActionMenuButton extends Button {

    private int mActiveColor;
    private int mInActiveColor;

    private int mIconResourceNormal;
    private Drawable mIconNormal;

    public FloatingActionMenuButton(Context context) {
        this(context, null);
    }

    public FloatingActionMenuButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FloatingActionMenuButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public FloatingActionMenuButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initattributes(context, attrs);
        mIconNormal=context.getDrawable(mIconResourceNormal);
        setStateListDrawable();

        setBackground(mIconNormal);
    }

    private void initattributes(Context context, AttributeSet attrs) {
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.FloatingActionMenuButton, 0, 0);
        if (attr == null) {
            return;
        }
        try {
            mIconResourceNormal = attr.getResourceId(R.styleable.FloatingActionMenuButton_pb_icon_normal, 0);

            mActiveColor=attr.getResourceId(R.styleable.FloatingActionMenuButton_pb_color_active,getResources().getColor(R.color.colorAccent));
            mInActiveColor=attr.getResourceId(R.styleable.FloatingActionMenuButton_pb_color_in_active,getResources().getColor(R.color.colorPrimary));
        } finally {
            attr.recycle();
        }
    }

    private void setStateListDrawable() {
//        StateListDrawable stateListDrawable=new StateListDrawable();
//        stateListDrawable.addState(new int[]{android.R.attr.state_selected},mIconNormal);
//        stateListDrawable.addState(new int[]{-android.R.attr.state_selected},mIconNormal);
        DrawableCompat.setTintList(mIconNormal,new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_selected},
                        new int[]{android.R.attr.state_pressed},
                        new int[]{-android.R.attr.state_selected},
                        new int[]{}
                },
                new int[]{
                        mActiveColor,
                        mActiveColor,
                        mInActiveColor,
                        mInActiveColor
                }
        ));
    }


}
