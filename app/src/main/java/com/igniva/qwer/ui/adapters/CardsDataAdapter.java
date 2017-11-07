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
    Context context;
    FragmentViewPagerAdapter pagerAdapter;
    FragmentManager fragmentManagerChild;
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
    private TabLayout tabLayoutLangage;
    private FloatingActionButton fab;
    private FloatingActionButton fab2;
    private FloatingActionButton fab1;
    private Boolean isFabOpen = false;

    public CardsDataAdapter(Context context, FragmentManager childFragmentManager) {

        super(context, R.layout.card_content);
        this.context = context;
        this.fragmentManagerChild = childFragmentManager;
    }

    @Override
    public View getView(final int position, final View contentView, ViewGroup parent) {
        TextView v = (TextView) (contentView.findViewById(R.id.content));
        v.setText(getItem(position));
        tabLayoutLangage = (TabLayout) contentView.findViewById(R.id.tab_layout_language);
        mViewPager = (ViewPager) contentView.findViewById(R.id.view_pager);
        final ImageButton iv_request = (ImageButton) contentView.findViewById(R.id.iv_request);
        final ImageButton iv_videoCall = (ImageButton) contentView.findViewById(R.id.iv_videoCall);
        final LinearLayout ll_contact = (LinearLayout) contentView.findViewById(R.id.ll_contact);
        final ImageButton iv_voiceCall = (ImageButton) contentView.findViewById(R.id.iv_voiceCall);
        final ImageButton iv_message = (ImageButton) contentView.findViewById(R.id.iv_message);
        final Animation animation1 =
                AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.right_in);
        ll_contact.startAnimation(animation1);
         iv_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 ll_contact.startAnimation(animation1);
                 ll_contact.setVisibility(View.VISIBLE);
             }
        });


        tabLayoutLangage.addTab(tabLayoutLangage.newTab().setText(context.getResources().getString(R.string.speaks)));
        tabLayoutLangage.addTab(tabLayoutLangage.newTab().setText(context.getResources().getString(R.string.learn)));
        tabLayoutLangage.addTab(tabLayoutLangage.newTab().setText(context.getResources().getString(R.string.about)));

        tabLayoutLangage.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.select();
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

