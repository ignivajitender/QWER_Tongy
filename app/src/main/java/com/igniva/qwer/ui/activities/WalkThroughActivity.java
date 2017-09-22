package com.igniva.qwer.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.igniva.qwer.R;
import com.igniva.qwer.ui.views.ZoomOutPageTransformer;
import com.igniva.qwer.utils.PreferenceHandler;
import com.igniva.qwer.utils.Utility;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WalkThroughActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.layoutDots)
    LinearLayout mLayoutDots;


    private MyViewPagerAdapter myViewPagerAdapter;
    private ImageView[] dots;
    private int[] layouts;
    private PreferenceHandler prefs;
    private int NUM_PAGES = 0;
    private Handler handler;
    private Runnable update;
    private Timer swipeTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

//        // Checking for first time launch - before calling setContentView()
//        prefs = MyApplication.getApp().getPrefs();
//        // Making notification bar transparent
//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }
        setContentView(R.layout.activity_walk_through);
        ButterKnife.bind(this);


        setUpLayout();
        setDataInViewObjects();

    }

    private void addBottomDots(int currentPage) {
        dots = new ImageView[layouts.length];


        int[] colorsActive = getResources().getIntArray(R.array.array_pager_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_pager_inactive);

        mLayoutDots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
          /*  dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);*/

            dots[i].setImageResource((R.drawable.non_selected_item));
            dots[i].setPadding(8,8,8,8);
            mLayoutDots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setPadding(8,8,8,8);

        dots[currentPage].setImageResource((R.drawable.pagination_arrow));

//        dots[currentPage].setBackground(getResources().getDrawable(R.drawable.pagination_arrow));
    }

    private int getItem(int i) {
        return mViewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        // startActivity(new Intent(WalkThroughActivity.this, LoginActivity.class));
        // Utility.showToastMessageLong(WalkThroughActivity.this,"Login");
        startActivity(new Intent(WalkThroughActivity.this, LoginActivity.class));

    }


    private void launchSignUpScreen() {
        //startActivity(new Intent(WalkThroughActivity.this, SignUpActivity.class));
        Utility.showToastMessageLong(WalkThroughActivity.this, "SignUp");
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            if (position == 3) {
                launchHomeScreen();
//              handler.removeCallbacks(update);
                swipeTimer.cancel();
                swipeTimer.purge();
                handler = null;
            }


            // }
        }


        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    /**
     * Making notification bar transparent
     */

    int currentPages = 0;

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void setUpLayout() {


        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.welcome_screen1,
                R.layout.welcome_screen2,
                R.layout.welcome_screen3,
                R.layout.welcome_screen4};

        // adding bottom dots
        addBottomDots(0);
        // Auto start of viewpager
        NUM_PAGES = layouts.length;
        handler = new Handler();
        //                if(currentPages==2){
//                    handler.removeCallbacks(this);
//                    handler.removeCallbacks(Update);
//                }
        update = new Runnable() {
            public void run() {
                if (currentPages == NUM_PAGES) {
                    currentPages = 0;
                }
                mViewPager.setCurrentItem(currentPages++, true);

            }
        };
        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 2000, 2000);
    }

    /*  @Override
      public void initToolbar() {

      }
  */
    @Override
    public void setDataInViewObjects() {
        myViewPagerAdapter = new MyViewPagerAdapter();
        mViewPager.setAdapter(myViewPagerAdapter);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mViewPager.addOnPageChangeListener(viewPagerPageChangeListener);
    }

    @Override
    protected void setUpToolbar() {

    }

    @Override
    public void onClick(View v) {

    }


    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
