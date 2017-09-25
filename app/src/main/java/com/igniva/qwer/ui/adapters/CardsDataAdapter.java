package com.igniva.qwer.ui.adapters;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
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

    ViewPager mViewPager;
    ViewPager mView_pager_language;
    Context context;
    FragmentViewPagerAdapter pagerAdapter;
    private FragmentHomePagerAdapter mSectionsPagerAdapter;
    FragmentManager fragmentManagerChild;
    private TabLayout tabLayoutLangage;
    private LinearLayout mLayout_speaks;
    private LinearLayout mLayout_about;

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
        mView_pager_language = (ViewPager) contentView.findViewById(R.id.view_pager_language_card);

        mSectionsPagerAdapter = new FragmentHomePagerAdapter(fragmentManagerChild);
        tabLayoutLangage.setupWithViewPager(mView_pager_language);

        mView_pager_language.setAdapter(mSectionsPagerAdapter);


        mLayout_speaks = (LinearLayout) contentView.findViewById(R.id.ll_Speaks);

        mLayout_about = (LinearLayout) contentView.findViewById(R.id.ll_About);

        tabLayoutLangage.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {


            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()){
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

