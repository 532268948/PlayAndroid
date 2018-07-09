package com.zust.playandroid.custom.ProgressButton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.StateSet;
import android.view.View;
import android.widget.Button;

import com.zust.playandroid.R;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/4/18
 * 时 间： 16:13
 * 项 目： PlayAndroid
 * 描 述：自定义控件,带有进度条
 */


@SuppressLint("AppCompatCustomView")
public class ProgressButton extends Button {

    private enum State {
        IDLE, PROGRESS, COMPLETE, ERROR
    }

    private StrokeGradientDrawable background;

    private CircularAnimatedDrawable mCircularAnimatedDrawable;

    private LoadDataListener loadDataListener;

    private int mStrokeWidth;
    private int mCircleStrokeWidth;
    private int mRadius;

    private int mIconComplete;

    private int mIconError;

    private ColorStateList mIdleColorState;
    private ColorStateList mCompleteColorState;
    private ColorStateList mErrorColorState;
    private StateListDrawable mIdleStateDrawable;
    private StateListDrawable mCompleteStateDrawable;
    private StateListDrawable mErrorStateDrawable;

    private int mColorProgress;
    private int mColorIndicator;
    private int mColorCircle;

    private String mTextIdle;
    private String mTextComplete;
    private String mTextError;

    private int mProgress;
    private State mState;
    private boolean animating;

    public ProgressButton(Context context) {
        this(context, null);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        initAttributes(context, attrs);
        mState = State.IDLE;
        setText(mTextIdle);
        initIdleStateDrawable();
        setBackground(mIdleStateDrawable);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setText("");
                removeIcon();
                invalidate();
                loadAnimation();
            }
        });
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.ProgressButton, 0, 0);
        if (attr == null) {
            return;
        }
        try {
            mIconComplete = attr.getResourceId(R.styleable.ProgressButton_pb_icon_complete, 0);
            mIconError = attr.getResourceId(R.styleable.ProgressButton_pb_icon_error, 0);

            mIdleColorState = getResources().getColorStateList(attr.getResourceId(R.styleable.ProgressButton_pb_selector_idle,R.color.white_30));
            mCompleteColorState = getResources().getColorStateList(attr.getResourceId(R.styleable.ProgressButton_pb_selector_complete, R.color.white_30));
            mErrorColorState = getResources().getColorStateList(attr.getResourceId(R.styleable.ProgressButton_pb_selector_error, R.color.white_30));

            mColorProgress = attr.getResourceId(R.styleable.ProgressButton_pb_color_progress_background, getResources().getColor(R.color.white));
            mColorIndicator = attr.getResourceId(R.styleable.ProgressButton_pb_color_progress_indicator, getResources().getColor(R.color.button_progress_green));
            mColorCircle = attr.getResourceId(R.styleable.ProgressButton_pb_color_circle, getResources().getColor(R.color.colorAccent));

            mTextIdle = attr.getString(R.styleable.ProgressButton_pb_text_idle);
            mTextComplete = attr.getString(R.styleable.ProgressButton_pb_text_complete);
            mTextError = attr.getString(R.styleable.ProgressButton_pb_text_error);

            mRadius = attr.getDimensionPixelSize(R.styleable.ProgressButton_pb_radius, 0);
            mStrokeWidth = attr.getDimensionPixelSize(R.styleable.ProgressButton_pb_stroke_width, 0);
            mCircleStrokeWidth=attr.getDimensionPixelSize(R.styleable.ProgressButton_pb_circle_stroke_width,10);

        } finally {
            attr.recycle();
        }
    }

    private void initIdleStateDrawable() {
        int colorNormal = mIdleColorState.getColorForState(new int[]{android.R.attr.state_enabled}, 0);
        int colorPressed = mIdleColorState.getColorForState(new int[]{android.R.attr.state_pressed}, 0);
        int colorFocused = mIdleColorState.getColorForState(new int[]{android.R.attr.state_focused}, 0);
        int colorDisabled = mIdleColorState.getColorForState(new int[]{-android.R.attr.state_enabled}, 0);

        if (background == null) {
            background = createDrawable(colorNormal);
        }
        StrokeGradientDrawable drawableDisabled = createDrawable(colorDisabled);
        StrokeGradientDrawable drawableFocused = createDrawable(colorFocused);
        StrokeGradientDrawable drawablePressed = createDrawable(colorPressed);
        mIdleStateDrawable = new StateListDrawable();

        mIdleStateDrawable.addState(new int[]{android.R.attr.state_pressed}, drawablePressed.getGradientDrawable());
        mIdleStateDrawable.addState(new int[]{android.R.attr.state_focused}, drawableFocused.getGradientDrawable());
        mIdleStateDrawable.addState(new int[]{-android.R.attr.state_enabled}, drawableDisabled.getGradientDrawable());
        mIdleStateDrawable.addState(StateSet.WILD_CARD, background.getGradientDrawable());
    }

    private void initErrorStateDrawable() {
        int colorPressed = mErrorColorState.getColorForState(new int[]{android.R.attr.state_pressed}, 0);

        StrokeGradientDrawable drawablePressed = createDrawable(colorPressed);
        mErrorStateDrawable = new StateListDrawable();

        mErrorStateDrawable.addState(new int[]{android.R.attr.state_pressed}, drawablePressed.getGradientDrawable());
        mErrorStateDrawable.addState(StateSet.WILD_CARD, background.getGradientDrawable());
    }

    private void initCompleteStateDrawable() {
        int colorPressed = mCompleteColorState.getColorForState(new int[]{android.R.attr.state_pressed}, 0);

        StrokeGradientDrawable drawablePressed = createDrawable(colorPressed);
        mCompleteStateDrawable = new StateListDrawable();

        mCompleteStateDrawable.addState(new int[]{android.R.attr.state_pressed}, drawablePressed.getGradientDrawable());
        mCompleteStateDrawable.addState(StateSet.WILD_CARD, background.getGradientDrawable());
    }

    private StrokeGradientDrawable createDrawable(int color) {
        GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.button_progress).mutate();
        drawable.setColor(color);
        drawable.setCornerRadius(mRadius);
//        drawable.setStroke(mStrokeWidth,color);
        StrokeGradientDrawable strokeGradientDrawable = new StrokeGradientDrawable(drawable);
        strokeGradientDrawable.setStrokeWidth(mStrokeWidth);
        strokeGradientDrawable.setStrokeColor(color);
//        Log.e("ProgressButton",color+" ");
        return strokeGradientDrawable;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        Log.e("ProgessButton","onDraw()1");
        super.onDraw(canvas);
        if (mProgress > 0 && mState == State.PROGRESS) {
//            Log.e("ProgessButton","onDraw()2");
            drawIndeterminedProgress(canvas);
        }
    }


    public void loadAnimation() {
//        Log.e("ProgressButton", "onClick()");
        if (animating) {
            return;
        } else {
            animating = true;
            setProgress(50);
        }
    }

    private void drawIndeterminedProgress(Canvas canvas) {
        if (mCircularAnimatedDrawable == null) {
            int offset = (getWidth() - getHeight()) / 2;
            mCircularAnimatedDrawable = new CircularAnimatedDrawable(mColorIndicator, mStrokeWidth);
            int left = offset;
            int right = getWidth() - offset;
            int bottom = getHeight();
            int top = 0;
            mCircularAnimatedDrawable.setBounds(left, top, right, bottom);
            mCircularAnimatedDrawable.setCallback(this);
            mCircularAnimatedDrawable.start();
            loadDataListener.startLoadListener();
        } else {
            mCircularAnimatedDrawable.draw(canvas);
        }
    }

    @Override
    protected void drawableStateChanged() {
        if (mState == State.COMPLETE) {
            initCompleteStateDrawable();
            setBackground(mCompleteStateDrawable);
        } else if (mState == State.IDLE) {
            initIdleStateDrawable();
            setBackground(mIdleStateDrawable);
        } else if (mState == State.ERROR) {
            initErrorStateDrawable();
            setBackground(mErrorStateDrawable);
        }

        if (mState != State.PROGRESS) {
            super.drawableStateChanged();
        }
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return who == mCircularAnimatedDrawable || super.verifyDrawable(who);
    }

    public void setProgress(int progress) {
        mProgress = progress;
        if (mProgress >= 100) {
            if (mState == State.COMPLETE) {
                morphProgressToComplete();
            }
        } else if (mProgress > 0) {
            if (mState == State.IDLE) {
                morphToProgress();
//                Log.e("ProgressButton","morthToProgress()");
            } else {
                return;
            }
        } else if (mProgress == 0) {

        } else if (mProgress == -1) {
            if (mState == State.ERROR) {
                morphProgressToError();
            }
        }
    }

    private void morphProgressToError() {
        MorphingAnimation animation = createProgressMorphing(getHeight(), mRadius, getHeight(), getWidth());

        animation.setFromColor(mColorProgress);
        animation.setToColor(mErrorColorState.getColorForState(new int[]{android.R.attr.state_enabled}, 0));

        animation.setFromStrokeColor(mColorIndicator);
        animation.setToStrokeColor(mErrorColorState.getColorForState(new int[]{android.R.attr.state_enabled}, 0));
        animation.setListener(mErrorStateListener);

        animation.start();
    }

    private OnAnimationEndListener mErrorStateListener = new OnAnimationEndListener() {
        @Override
        public void onAnimationEnd() {
            if (mIconError != 0) {
                setText(null);
                setIcon(mIconError);
            } else {
                setText(mTextError);
            }
            animating = false;
            mState = State.IDLE;
        }
    };

    private void morphProgressToComplete() {
        MorphingAnimation animation = createProgressMorphing(getHeight(), mRadius, getHeight(), getWidth());

        animation.setFromColor(mColorProgress);
        animation.setToColor(mCompleteColorState.getColorForState(new int[]{android.R.attr.state_enabled}, 0));

        animation.setFromStrokeColor(mColorIndicator);
        animation.setToStrokeColor(mCompleteColorState.getColorForState(new int[]{android.R.attr.state_enabled}, 0));

        animation.setListener(mCompleteStateListener);

        animation.start();
    }

    private void morphToProgress() {
        setWidth(getWidth());
        MorphingAnimation animation = createProgressMorphing(mRadius, getHeight(), getWidth(), getHeight());
        animation.setFromColor(mIdleColorState.getColorForState(new int[]{android.R.attr.state_enabled}, 0));
        animation.setToColor(mColorCircle);
        animation.setFromStrokeColor(mIdleColorState.getColorForState(new int[]{android.R.attr.state_enabled}, 0));
        animation.setToStrokeColor(mColorProgress);
        animation.setListener(mProgressStateListener);
        animation.start();
    }

    private MorphingAnimation createProgressMorphing(float fromCorner, float toCorner, int fromWidth, int toWidth) {

        MorphingAnimation animation = new MorphingAnimation(this, background);
        animation.setFromCornerRadius(fromCorner);
        animation.setToCornerRadius(toCorner);
        animation.setPadding(0);
        animation.setFromWidth(fromWidth);
        animation.setToWidth(toWidth);
        animation.setDuration(MorphingAnimation.DURATION_NORMAL);
        return animation;
    }

    private OnAnimationEndListener mProgressStateListener = new OnAnimationEndListener() {
        @Override
        public void onAnimationEnd() {
            mState = State.PROGRESS;
        }
    };
    private OnAnimationEndListener mCompleteStateListener = new OnAnimationEndListener() {
        @Override
        public void onAnimationEnd() {
            if (mIconComplete != 0) {
                setText(null);
                setIcon(mIconComplete);
            } else {
                setText(mTextComplete);
            }
            animating = false;
            mState = State.IDLE;
        }
    };

    public void setLoadDataListener(LoadDataListener loadDataListener) {
        this.loadDataListener = loadDataListener;
    }

    public interface LoadDataListener {
        void startLoadListener();
    }

    public void loadDataSuccess() {
        mCircularAnimatedDrawable.stop();
        mCircularAnimatedDrawable=null;
        mState = State.COMPLETE;
        setProgress(100);
    }

    public void loadDataFalse() {
        mCircularAnimatedDrawable.stop();
        mCircularAnimatedDrawable=null;
        mState = State.ERROR;
        setProgress(-1);
    }

    private void setIcon(int icon) {
        Drawable drawable = getResources().getDrawable(icon);
        if (drawable != null) {
            int padding = (getWidth() / 2) - (drawable.getIntrinsicWidth() / 2);
            setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
            setPadding(padding, 0, 0, 0);
        }
    }

    protected void removeIcon() {
        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        setPadding(0, 0, 0, 0);
    }

}
