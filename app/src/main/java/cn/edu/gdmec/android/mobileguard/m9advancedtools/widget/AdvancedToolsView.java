package cn.edu.gdmec.android.mobileguard.m9advancedtools.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by student on 17/12/4.
 */

public class AdvancedToolsView extends RelativeLayout{
    private TextView mDescriptionTV;
    private String desc = "";
    private Drawable drawable;
    private ImageView mLeftImgv;

    public AdvancedToolsView(Context context){
        super(context);


    }
    public AdvancedToolsView(Context context, AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);

    }
}
