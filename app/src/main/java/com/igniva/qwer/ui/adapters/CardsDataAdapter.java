package com.igniva.qwer.ui.adapters;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.igniva.qwer.R;

public class CardsDataAdapter extends ArrayAdapter<String> {

    ViewPager mViewPager;
    ViewPager mView_pager_language;
    Context context;
    FragmentViewPagerAdapter pagerAdapter;
    private FragmentHomePagerAdapter mSectionsPagerAdapter;
    FragmentManager fragmentManagerChild;
    private TabLayout tabLayoutLangage;
    private LinearLayout mLayout_speaks;
    private LinearLayout mLayout_about;
    private FloatingActionButton fab;
    private FloatingActionButton fab2;
    private FloatingActionButton fab1;
    private Boolean isFabOpen = false;

    public CardsDataAdapter(Context context, FragmentManager childFragmentManager) {

        super(context, R.layout.card_content);
        this.context = context;
        this.fragmentManagerChild = childFragmentManager;
    }

    int[] mResources = {
            R.drawable.busesgallery03,
            R.drawable.e,
            R.drawable.busesgallery03,
            R.drawable.busesgallery03,
            R.drawable.busesgallery03,
            R.drawable.f,
            R.drawable.g,
            R.drawable.busesgallery03,
            R.drawable.g,
            R.drawable.f
    };


    @Override
    public View getView(int position, final View contentView, ViewGroup parent) {
        TextView v = (TextView) (contentView.findViewById(R.id.content));
        v.setText(getItem(position));
        tabLayoutLangage = (TabLayout) contentView.findViewById(R.id.tab_layout_language);
        mViewPager = (ViewPager) contentView.findViewById(R.id.view_pager);
        ImageButton iv_request = (ImageButton) contentView.findViewById(R.id.iv_request);
        final ImageButton iv_menu1 = (ImageButton) contentView.findViewById(R.id.iv_menu1);
        final ImageButton iv_menu2 = (ImageButton) contentView.findViewById(R.id.iv_menu2);
        final ImageButton iv_menu3 = (ImageButton) contentView.findViewById(R.id.iv_menu3);
        Animation animation1 =
                AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.right_in);
        iv_request.startAnimation(animation1);
        iv_menu1.startAnimation(animation1);
        iv_menu2.startAnimation(animation1);
        iv_menu3.startAnimation(animation1);
        iv_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_menu2.setVisibility(View.VISIBLE);
                iv_menu1.setVisibility(View.VISIBLE);
                iv_menu3.setVisibility(View.VISIBLE);
            }
        });

        mView_pager_language = (ViewPager) contentView.findViewById(R.id.view_pager_language_card);


        mSectionsPagerAdapter = new FragmentHomePagerAdapter(fragmentManagerChild);

        mView_pager_language.setAdapter(mSectionsPagerAdapter);
        tabLayoutLangage.setupWithViewPager(mView_pager_language);

        mLayout_speaks = (LinearLayout) contentView.findViewById(R.id.ll_Speaks);

        mLayout_about = (LinearLayout) contentView.findViewById(R.id.ll_About);


        tabLayoutLangage.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("CardsDataAdapter", "onTabSelected: ");
                switch (tab.getPosition()) {
                    case 0:
                        mLayout_speaks.setVisibility(View.VISIBLE);
                        mLayout_about.setVisibility(View.GONE);
                        break;
                    case 1:
                        break;
                    case 2:
                        mLayout_speaks.setVisibility(View.GONE);
                        mLayout_about.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
                mView_pager_language.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d("CardsDataAdapter", "onTabUnselected: ");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d("CardsDataAdapter", "onTabReselected: ");
            }
        });

        mView_pager_language.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("CardsDataAdapter", "onPageScrolled: ");
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("CardsDataAdapter", "onPageSelected: "+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("CardsDataAdapter", "onPageScrollStateChanged: ");
            }
        });
/*
        tabLayoutLangage.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {


            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mLayout_speaks.setVisibility(View.VISIBLE);
                        mLayout_about.setVisibility(View.GONE);
                        break;
                    case 1:
                        break;
                    case 2:
                        mLayout_speaks.setVisibility(View.GONE);
                        mLayout_about.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
                mView_pager_language.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/

        CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(context);

        mViewPager.setAdapter(mCustomPagerAdapter);
        mViewPager.setCurrentItem(0);

        return contentView;
    }

    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mResources.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imageView.setImageResource(mResources[position]);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }


}

