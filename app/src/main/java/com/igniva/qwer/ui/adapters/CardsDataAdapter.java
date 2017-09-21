package com.igniva.qwer.ui.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.igniva.qwer.R;

public class CardsDataAdapter extends ArrayAdapter<String> {

    ViewPager viewPager;
    Context context;
    public CardsDataAdapter(Context context) {

        super(context, R.layout.card_content);
        this.context=context;
    }
    int[] mResources = {
            R.drawable.d,
            R.drawable.e,
            R.drawable.busesgallery03,
            R.drawable.busesgallery03,
            R.drawable.busesgallery03,
            R.drawable.f,
            R.drawable.g,
            R.drawable.f,
            R.drawable.g,
            R.drawable.f
    };

    @Override
    public View getView(int position, final View contentView, ViewGroup parent){
        TextView v = (TextView)(contentView.findViewById(R.id.content));
        v.setText(getItem(position));

        viewPager=(ViewPager)contentView.findViewById(R.id.view_pager);

     CustomPagerAdapter   mCustomPagerAdapter = new CustomPagerAdapter(context);

//        mViewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(mCustomPagerAdapter);
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

