package com.zust.playandroid.ui.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zust.playandroid.R;
import com.zust.playandroid.base.activity.BaseActivity;
import com.zust.playandroid.contract.splash.SplashContract;
import com.zust.playandroid.presenter.splash.SplashPresenter;
import com.zust.playandroid.ui.login.LoginActivity;
import com.zust.playandroid.ui.main.MainActivity;

public class SplashActivity extends BaseActivity<SplashContract.View,SplashPresenter<SplashContract.View>> implements SplashContract.View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        presenter.init();
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
        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
        this.finish();
    }
}
