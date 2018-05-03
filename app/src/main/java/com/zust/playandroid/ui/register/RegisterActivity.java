package com.zust.playandroid.ui.register;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zust.playandroid.R;
import com.zust.playandroid.base.activity.BaseActivity;
import com.zust.playandroid.contract.register.RegisterContract;
import com.zust.playandroid.custom.ProgressButton.ProgressButton;
import com.zust.playandroid.presenter.register.RegisterPresenter;
import com.zust.playandroid.ui.guide.GuideActivity;
import com.zust.playandroid.ui.main.MainActivity;
import com.zust.playandroid.utils.ActivityManager;

public class RegisterActivity extends BaseActivity<RegisterContract.View, RegisterPresenter<RegisterContract.View>> implements RegisterContract.View {

    private EditText mAccountEditText;
    private EditText mPasswordEditText;
    private EditText mRePasswordEditText;

    private ProgressButton mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        mAccountEditText=(EditText)findViewById(R.id.account);
        mPasswordEditText=(EditText)findViewById(R.id.password);
        mRePasswordEditText=(EditText)findViewById(R.id.repassword);
        mRegisterButton=(ProgressButton)findViewById(R.id.register);

        mAccountEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (v==mAccountEditText&&actionId== EditorInfo.IME_ACTION_NEXT){
                    mPasswordEditText.findFocus();
                }
                return false;
            }
        });
        mPasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (v==mPasswordEditText&&actionId==EditorInfo.IME_ACTION_NEXT){
                    mRePasswordEditText.findFocus();
                }
                return false;
            }
        });
        mRegisterButton.setLoadDataListener(new ProgressButton.LoadDataListener() {
            @Override
            public void startLoadListener() {
                presenter.getRegisterData();
            }
        });
    }

    @Override
    protected RegisterPresenter<RegisterContract.View> createPresenter() {
        return new RegisterPresenter<>();
    }

    @Override
    public String getName() {
        return mAccountEditText.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPasswordEditText.getText().toString();
    }

    @Override
    public String getRePassword() {
        return mRePasswordEditText.getText().toString();
    }

    @Override
    public void SuccessAnimation() {
        mRegisterButton.loadDataSuccess();
    }

    @Override
    public void FalseAnimation() {
        mRegisterButton.loadDataFalse();
    }

    @Override
    public void gotoGuideActivity() {
        startActivity(new Intent(RegisterActivity.this, GuideActivity.class));
        ActivityManager.getInstance().removeWhenLogin();
    }

    @Override
    public void gotoMainActivity() {
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        ActivityManager.getInstance().removeWhenLogin();
    }


}
