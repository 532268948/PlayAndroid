package com.zust.playandroid.ui.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zust.playandroid.R;
import com.zust.playandroid.base.activity.BaseActivity;
import com.zust.playandroid.contract.splash.SplashContract;
import com.zust.playandroid.presenter.splash.SplashPresenter;
import com.zust.playandroid.ui.login.LoginActivity;
import com.zust.playandroid.ui.main.MainActivity;

import org.w3c.dom.Text;

public class SplashActivity extends BaseActivity<SplashContract.View,SplashPresenter<SplashContract.View>> implements SplashContract.View{

    private static final String TAG="SplashActivity";
    private ImageView mImageView;
    private TextView mTextView;
    AnimatorSet animSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mImageView=(ImageView)findViewById(R.id.image);
        mTextView=(TextView)findViewById(R.id.text);
//        presenter.init();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.e(TAG,"onWindowsFocusChanged()"+hasFocus);
        if (hasFocus) {
            ObjectAnimator animator1=ObjectAnimator.ofFloat(mImageView,"translationY",mImageView.getTranslationY()-200f,mImageView.getTranslationY());
            ObjectAnimator animator2=ObjectAnimator.ofFloat(mImageView,"alpha",0f,1f);
            ObjectAnimator animator3=ObjectAnimator.ofFloat(mTextView,"alpha",0f,1f);
            animSet = new AnimatorSet();
            animSet.play(animator1).with(animator2).with(animator3);
            animSet.setDuration(1500);
            animSet.start();
//        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);//第一个参数开始的透明度，第二个参数结束的透明度
//        alphaAnimation.setDuration(1500);//多长时间完成这个动作
//        alphaAnimation.setFillAfter(true);
//        mImageView.startAnimation(alphaAnimation);
            presenter.init();
        }

    }

    @Override
    protected SplashPresenter<SplashContract.View> createPresenter() {
        return new SplashPresenter<>();
    }

    @Override
    public void gotoMainActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        this.finish();
    }

    @Override
    public void gotoLoginActivity() {
//        animSet.cancel();
        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
        this.finish();
    }
}
