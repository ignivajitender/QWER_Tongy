package com.igniva.qwer.ui.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.igniva.qwer.R;
import com.igniva.qwer.model.ConnectionPojo;

import java.util.List;

/**
 * Created by igniva-android13 on 5/5/17.
 */

public class MultiImages extends PagerAdapter {
    int id;
    private static final String LOG_TAG = "MultiImages";
    Context context;
    List<ConnectionPojo.ConnectionDataPojo.UserImagePojo>  mImagesSlidingArray;
    String path;
    LayoutInflater mLayoutInflater;
    private int[] imagesList= new int[]{R.drawable.login_bg,R.drawable.tongy_logo,R.drawable.login_bg,R.drawable.tongy_logo};



    public MultiImages(Context context, List<ConnectionPojo.ConnectionDataPojo.UserImagePojo> arr, String pathUrl) {
        try {
            this.context = context;
            mImagesSlidingArray = arr;
            path = pathUrl;
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }


//      @Override
//      public void onBindViewHolder(ItemRowHolder viewHolder, final int position) {
//          try {
//              viewHolder.photo.setId(position);
//              Glide.with(context).load(path +imagesList[position]).asBitmap().fitCenter().placeholder(R.drawable.images).into(viewHolder.photo);
//              //Utility.loadPic(context, path + mImagesSlidingArray.get(position), viewHolder.photo, viewHolder.adapterProgressBar);
//          } catch (Exception e) {
//              e.printStackTrace();
//          }
  //  }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.sliding_pics_custom, container, false);


        ImageView photo = (ImageView) itemView.findViewById(R.id.imageView);

       // if (!mImagesSlidingArray.get(position).toString().equalsIgnoreCase("demo")) {
         //   Log.e(LOG_TAG, "onClick: "+"photopath " + mImagesSlidingArray.get(position)+ "" );
            Glide.with(context)
                    .load(mImagesSlidingArray.get(position).getImage()).asBitmap()
                    .into(photo);
//
       // }
        /*photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(LOG_TAG, "onClick: "+"setPHOTO" + position+ "" );
                ((Pr) context).addPhoto(position);
//                   // mProductImageClickListener.onImageClicked(context, position);
//                    //((CreateItemActivity) context).getItemView(viewHolder.photo);
            }
        });*/

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    @Override
    public int getCount() {
        return mImagesSlidingArray.size();
    }



}

