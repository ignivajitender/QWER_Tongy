

package com.igniva.qwer.ui.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.igniva.qwer.R;


public class TextViewLight extends TextView {

    public TextViewLight(Context context) {
        super(context);
        init();

    }

    private void init() {
        if (!isInEditMode()) {
            Typeface face = Typeface.createFromAsset(getContext().getAssets(),
                    "SourceSansProLight.ttf");

            setTypeface(face);
        }
    }

    public TextViewLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextViewLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
}
