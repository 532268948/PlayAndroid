package com.zust.playandroid.ui.guide;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.zust.playandroid.R;
import com.zust.playandroid.base.activity.BaseActivity;
import com.zust.playandroid.contract.guide.GuideContract;
import com.zust.playandroid.presenter.guide.GuidePresenter;
import com.zust.playandroid.ui.main.MainActivity;

import java.util.ArrayList;

public class GuideActivity extends BaseActivity<GuideContract.View, GuidePresenter<GuideContract.View>> implements
        GuideContract.View,
        ViewPager.OnPageChangeListener,
        ViewTreeObserver.OnGlobalLayoutListener{

    private ViewPager viewPager;
    private Button btnStartMain;
    private LinearLayout llPointGroup;
    private ImageView ivRedPoint;

    private int marginLeft;
    private int widthDpi;
    private ArrayList<ImageView> imageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        btnStartMain = (Button) findViewById(R.id.btn_start_main);
        llPointGroup = (LinearLayout) findViewById(R.id.ll_point_group);
        ivRedPoint = (ImageView) findViewById(R.id.iv_red_point);

        ivRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(this);
        btnStartMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("GuideActivity","onClick()");
                presenter.init();
            }
        });
        widthDpi = DensityUtil.dp2px(10f);
        int [] ids=new int[]{R.drawable.guide3,R.drawable.guide3,R.drawable.guide3};
        imageViews=new ArrayList<>();
        for(int i=0;i<ids.length;i++){
            ImageView imageview =new ImageView(this);
            imageview.setBackgroundResource(ids[i]);
            imageViews.add(imageview);
            ImageView point=new ImageView(this);
            point.setBackgroundResource(R.drawable.guide_point_normal);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(widthDpi,widthDpi);
            if(i!=0){
                params.leftMargin=widthDpi;
            }
            point.setLayoutParams(params);
            llPointGroup.addView(point);
        }
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    protected GuidePresenter<GuideContract.View> createPresenter() {
        return new GuidePresenter<>();
    }

    @Override
    public void gotoMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        float leftMargin=(position+positionOffset)*marginLeft;
        RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams)ivRedPoint.getLayoutParams();
        params.leftMargin=(int)leftMargin;
        ivRedPoint.setLayoutParams(params);
    }

    @Override
    public void onPageSelected(int position) {
        if(position==imageViews.size()-1){
            btnStartMain.setVisibility(View.VISIBLE);
        }else{
            btnStartMain.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onGlobalLayout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ivRedPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
        marginLeft=llPointGroup.getChildAt(1).getLeft()-llPointGroup.getChildAt(0).getLeft();
    }

    class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView=imageViews.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }
}
