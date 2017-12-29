package com.igniva.qwer.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
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
import com.igniva.qwer.controller.ApiControllerClass;
import com.igniva.qwer.model.OtherUserProfilePojo;
import com.igniva.qwer.model.UsersResponsePojo;
import com.igniva.qwer.ui.fragments.HomeFragment;
import com.igniva.qwer.utils.Global;
import com.igniva.qwer.utils.PreferenceHandler;
import com.igniva.qwer.utils.Utility;

import java.util.List;

import retrofit2.Retrofit;

public class CardsDataAdapter extends ArrayAdapter<String> {

    ViewPager mViewPager;
    Context context;
    // FragmentViewPagerAdapter pagerAdapter;
    FragmentManager fragmentManagerChild;
    List<UsersResponsePojo.UsersPojo.UserDataPojo> users;
    int NUM_PAGES = 4;
    boolean isOpaque = true;
    Retrofit retrofit;
    HomeFragment fragment;
    /* int[] mResources = {
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
     };*/
    private TabLayout tabLayoutLangage;
    private FloatingActionButton fab;
    private FloatingActionButton fab2;
    private FloatingActionButton fab1;
    private Boolean isFabOpen = false;
    private int size;

    public CardsDataAdapter(Context context, FragmentManager childFragmentManager, List<UsersResponsePojo.UsersPojo.UserDataPojo> users, Retrofit retrofit, HomeFragment homeFragment) {
        super(context, R.layout.card_content);
        this.context = context;
        this.fragmentManagerChild = childFragmentManager;
        this.users = users;
        this.retrofit = retrofit;
        this.fragment = homeFragment;
    }

    @Override
    public int getCount() {
        return this.users.size();
    }

    public void updateOnline(int pos, Boolean isOnline, final String view_id) {
        if (users.size()>pos &&String.valueOf(users.get(pos).getId()).equals(view_id) && Global.homeFrag != null && Global.homeFrag.mPos == pos) {
            Log.e("", pos + "updateOnline----" + users.get(pos).getId() + "----" + view_id);
             if (isOnline == null) {
                if (users.get(pos).isOnline)
                    Global.homeFrag.mCardStack.discardTop(1);
            } else if (isOnline) {
                users.get(pos).view_id = PreferenceHandler.readString(context, PreferenceHandler.PREF_KEY_USER_ID, "");
                 notifyDataSetChanged();
                 Global.homeFrag.callHandler();
             } else {
                Global.homeFrag.mCardStack.discardTop(1);
            }
        }
    }

    @Override
    public View getView(final int position, final View contentView, ViewGroup parent) {
        TextView mtvName = (TextView) (contentView.findViewById(R.id.tvName));
        TextView mTvAge = (TextView) (contentView.findViewById(R.id.tv_age));
        TextView mTvRadius = (TextView) (contentView.findViewById(R.id.tv_radius));

        mtvName.setText(users.get(position).name);
        mTvAge.setText(users.get(position).getAge() + " Years");
        String beforeFirstDot = users.get(position).getDistance().split("\\.")[0];
        mTvRadius.setText(beforeFirstDot + " km away");
        final RecyclerView mrvLanguage = (RecyclerView) (contentView.findViewById(R.id.rvLanguages));
        final TextView mtvAboutDetails = (TextView) (contentView.findViewById(R.id.tvAboutDetails));
        final LinearLayout mdotsLayout = (LinearLayout) (contentView.findViewById(R.id.dotsLayout));

        tabLayoutLangage = (TabLayout) contentView.findViewById(R.id.tab_layout_language);
        mViewPager = (ViewPager) contentView.findViewById(R.id.view_pager);
        final ImageButton iv_request = (ImageButton) contentView.findViewById(R.id.iv_request);
        final ImageView iv_online = (ImageView) contentView.findViewById(R.id.iv_online);
        final ImageButton iv_videoCall = (ImageButton) contentView.findViewById(R.id.iv_videoCall);
        final LinearLayout ll_contact = (LinearLayout) contentView.findViewById(R.id.ll_contact);
        final ImageButton iv_voiceCall = (ImageButton) contentView.findViewById(R.id.iv_voiceCall);
        final ImageButton iv_message = (ImageButton) contentView.findViewById(R.id.iv_message);
        final Animation animation1 =
                AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.right_in);
        ll_contact.startAnimation(animation1);

        // if(users.get(position).getUser_recieve()!=null && users.get(position).getUser_recieve().get(0).getStatus()==0)
        if (users.get(position).getUser_recieve().size() != 0 && users.get(position).getUser_recieve().get(0).getStatus() == 0) {
            iv_request.setImageResource(R.drawable.requested);
        } else {
            iv_request.setImageResource(R.drawable.request);
        }

        if (users.get(position).view_id.equals(PreferenceHandler.readString(context, PreferenceHandler.PREF_KEY_USER_ID, ""))) {
            if (System.currentTimeMillis() / 1000 - Integer.valueOf(users.get(position).getUpdated_at()) < 200)
                users.get(position).isOnline = true;
            else
                users.get(position).isOnline = false;
        } else
            users.get(position).isOnline = false;


        if (users.get(position).isOnline) {
            Log.e("", position + "isOnline----" + users.get(position).getId());
            ll_contact.startAnimation(animation1);
            ll_contact.setVisibility(View.VISIBLE);
            iv_online.setVisibility(View.VISIBLE);

        } else {
            ll_contact.setVisibility(View.GONE);
            iv_online.setVisibility(View.GONE);
        }

        iv_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (users.get(position).getUser_recieve().size() == 0 && users.get(position).getUser_send().size() == 0) {
                        ApiControllerClass.callAddContactApi(users.get(position).id, retrofit, context, users, position, fragment);
                    } else if (users.get(position).getUser_send().size() != 0) {
                        Utility.showToastMessageLong((Activity) context, "Request already sent.");
                    } else if (users.get(position).getUser_recieve().get(0).getStatus() == 0)
                        ApiControllerClass.callUserAction(context, retrofit, "accept", null, users.get(position).getUser_recieve().get(position).getRequest_from(), fragment);
                } catch (Exception e) {
                    Log.e("cardsadapter", e.getMessage());
                }
            }
        });

        iv_videoCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ApiControllerClass.getVideoToken(context, retrofit, PreferenceHandler.readString(context, PreferenceHandler.PREF_KEY_USER_ID, ""), users.get(position).id + "", users.get(position).name, users.get(position).getUser_image().get(0).getImage() + "");
                } catch (Exception e) {
                    Log.e("cardsadapter", e.getMessage());
                }
            }
        });
        iv_voiceCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ApiControllerClass.sendTwilioVoiceNotification(context, retrofit, PreferenceHandler.readString(context, PreferenceHandler.PREF_KEY_USER_ID, ""), users.get(position).id + "", users.get(position).name, users.get(position).getUser_image().get(0).getImage());
                } catch (Exception e) {
                    Log.e("cardsadapter", e.getMessage());
                }
            }
        });
        iv_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Utility.goToChatActivity(context, users.get(position).id, users.get(position).name, users.get(position).getUser_image().get(0).getImage());
                } catch (Exception e) {
                    Log.e("cardsadapter", e.getMessage());
                }
            }
        });
        tabLayoutLangage.addTab(tabLayoutLangage.newTab().setText(context.getResources().getString(R.string.speaks)));
        tabLayoutLangage.addTab(tabLayoutLangage.newTab().setText(context.getResources().getString(R.string.learn)));
        tabLayoutLangage.addTab(tabLayoutLangage.newTab().setText(context.getResources().getString(R.string.aboutTab)));
        showRecyclerViewList(users.get(position).getUser_speak(), mrvLanguage, mtvAboutDetails, "speak");
        tabLayoutLangage.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.select();
                if (tab.getPosition() == 0) {
                    showRecyclerViewList(users.get(position).getUser_speak(), mrvLanguage, mtvAboutDetails, "speak");
                } else if (tab.getPosition() == 1) {
                    showRecyclerViewList(users.get(position).getUser_learn(), mrvLanguage, mtvAboutDetails, "learn");
                } else if (tab.getPosition() == 2) {
                    //replaceFragment(new PostsListFragment());
                    showAbout(users.get(position).getAbout(), mtvAboutDetails, mrvLanguage, users.get(position));
                }

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



       /* CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(context);

        mViewPager.setAdapter(mCustomPagerAdapter);
        mViewPager.setCurrentItem(0);
*/
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == NUM_PAGES - 1 && positionOffset > 0) {
                    if (isOpaque) {
                        mViewPager.setBackgroundColor(Color.TRANSPARENT);
                        isOpaque = false;
                    }
                } else {
                    if (!isOpaque) {
                        mViewPager.setBackgroundColor(context.getResources().getColor(R.color.primary_material_light));
                        isOpaque = true;
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                setIndicator(position, mdotsLayout);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (users.get(position).getUser_image() != null && users.get(position).getUser_image().size() > 0) {
            MultiImages multiImages = new MultiImages(context, users.get(position).getUser_image());
            mViewPager.setAdapter(multiImages);
            buildCircles(users.get(position).getUser_image().size(), mdotsLayout);
            size = users.get(position).getUser_image().size();
            setIndicator(size, mdotsLayout);
        } else {
            mViewPager.setBackgroundResource(R.drawable.imgpsh_dummy);
        }

        return contentView;
    }

    private void showAbout(String about, TextView mtvAboutDetails, RecyclerView mrvLanguage, UsersResponsePojo.UsersPojo.UserDataPojo userDataPojo) {
        mrvLanguage.setVisibility(View.GONE);
        mtvAboutDetails.setVisibility(View.VISIBLE);
        if (userDataPojo.getUser_country() != null)
            mtvAboutDetails.setText(Html.fromHtml("<b>" + Utility.getColoredSpanned(context.getResources().getString(R.string.i_am_from).toUpperCase(), "#767676") + " " + Utility.getColoredSpanned(userDataPojo.getUser_country().getCountry(), "#90cbf6") + "</b>" + "<br>" + Utility.getColoredSpanned(about, "#000000")));
        else
            mtvAboutDetails.setText(Html.fromHtml(Utility.getColoredSpanned(about, "#000000")));
    }

    private void showRecyclerViewList(List<OtherUserProfilePojo.UsersPojo.UserSpeakPojo> user_speak, RecyclerView mrvLanguage, TextView mtvAboutDetails, String type) {
        mtvAboutDetails.setVisibility(View.GONE);
        mrvLanguage.setVisibility(View.VISIBLE);
        GridLayoutManager linearLayoutManager1 = new GridLayoutManager(context, 3);
        mrvLanguage.setLayoutManager(linearLayoutManager1);
        mrvLanguage.setNestedScrollingEnabled(false);

//        mrvLanguage.addItemDecoration(new GridSpacingItemDecoration(3, 10, false));
        if (type.equalsIgnoreCase("speak")) {
            LanguageAdapter adapter1 = new LanguageAdapter(context, user_speak);
            mrvLanguage.setAdapter(adapter1);

        } else {
            LanguageSpeakAdapter adapter = new LanguageSpeakAdapter(context, user_speak, "learn");
            mrvLanguage.setAdapter(adapter);
        }

    }

    /*class CustomPagerAdapter extends PagerAdapter {

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
    }*/

    public void buildCircles(int size, LinearLayout mdotsLayout) {
        try {
            float scale = context.getResources().getDisplayMetrics().density;
            int padding = (int) (5 * scale + 0.5f);


            for (int i = 0; i < size; i++) {
                ImageView circle = new ImageView(context);
                circle.setImageResource(R.drawable.login_checkbox);
                circle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                circle.setAdjustViewBounds(true);
                circle.setPadding(padding, 0, padding, 0);
                mdotsLayout.addView(circle);
            }

            setIndicator(0, mdotsLayout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setIndicator(int index, LinearLayout mdotsLayout) {
        if (index < size) {
            for (int i = 0; i < size; i++) {
                ImageView circle = (ImageView) mdotsLayout.getChildAt(i);
                if (circle != null)
                    if (i == index) {
                        circle.setColorFilter(context.getResources().getColor(R.color.bg_white));
                    } else {
                        circle.setColorFilter(context.getResources().getColor(R.color.text_dark_grey));
                    }
            }
        }
    }

}

