package com.zust.playandroid.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zust.playandroid.R;
import com.zust.playandroid.base.activity.BaseActivity;
import com.zust.playandroid.contract.login.LoginContract;
import com.zust.playandroid.custom.ProgressButton.ProgressButton;
import com.zust.playandroid.presenter.login.LoginPresenter;
import com.zust.playandroid.ui.guide.GuideActivity;
import com.zust.playandroid.ui.main.MainActivity;
import com.zust.playandroid.ui.register.RegisterActivity;
import com.zust.playandroid.utils.ActivityManager;
import com.zust.playandroid.utils.ToastUtil;

public class LoginActivity extends BaseActivity<LoginContract.View,LoginPresenter<LoginContract.View>> implements LoginContract.View {

    private EditText mAccountEditText;
    private EditText mPasswordEditText;
    private ProgressButton mLoginButton;
    private ProgressButton mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        mAccountEditText=(EditText)findViewById(R.id.account);
        mPasswordEditText=(EditText)findViewById(R.id.password);
        mLoginButton=(ProgressButton)findViewById(R.id.login);
        mRegisterButton=(ProgressButton)findViewById(R.id.register);

        mLoginButton.setLoadDataListener(new ProgressButton.LoadDataListener() {
            @Override
            public void startLoadListener() {
                presenter.login();
            }
        });
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.gotoRegisterActivity();
            }
        });
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public String getUserName() {
        return mAccountEditText.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPasswordEditText.getText().toString();
    }

    @Override
    public void gotoMainActivity() {
        startActivity(new Intent(this,MainActivity.class));
        ActivityManager.getInstance().removeWhenLogin();
    }

    @Override
    public void gotoGuideActivity() {
        startActivity(new Intent(this, GuideActivity.class));
        ActivityManager.getInstance().removeWhenLogin();
    }

    @Override
    public void gotoRegisterActivity() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    public void loginSuccess() {
        mLoginButton.loadDataSuccess();
    }

    @Override
    public void loginFalse() {
        mLoginButton.loadDataFalse();
    }

}
