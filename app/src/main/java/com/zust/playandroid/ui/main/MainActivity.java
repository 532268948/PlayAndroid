package com.zust.playandroid.ui.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zust.playandroid.R;
import com.zust.playandroid.custom.ProgressButton.ProgressButton;

public class MainActivity extends AppCompatActivity {

    private ProgressButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("MainActivity","onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (ProgressButton) findViewById(R.id.btn1);

        button.setLoadDataListener(new ProgressButton.LoadDataListener() {
            @Override
            public void startLoadListener() {
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       for(int i=0;i<50000;i++){
//                           Log.e("Main"," "+1);
                       }
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               button.loadDataFalse();

                           }
                       });
                   }
               }).start();

            }
        });
    }

}
