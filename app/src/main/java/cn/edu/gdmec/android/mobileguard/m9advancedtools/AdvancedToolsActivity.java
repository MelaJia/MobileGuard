package cn.edu.gdmec.android.mobileguard.m9advancedtools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.gdmec.android.mobileguard.R;

public class AdvancedToolsActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_advanced_tools);
        initView();
    }
    private void initView(){
        findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.bright_red));
        ImageView mLeftImgv = (ImageView) findViewById(R.id.imgv_leftbtn);
        ((TextView)findViewById(R.id.tv_title)).setText("高级工具");
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.drawable.back);
        findViewById(R.id.advancevieiw_applock).setOnClickListener(this);
        findViewById(R.id.advancevieiw_nubelongs).setOnClickListener(this);
        findViewById(R.id.advancevieiw_smsbackup).setOnClickListener(this);
        findViewById(R.id.advancevieiw_smsreducition).setOnClickListener(this);

    }
//    public AdvancedToolsActivity(Context context, AttributeSet paramAttributeSet)
//    {
//        super(context, paramAttributeSet);
//
//    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgv_leftbtn:
                finish();
                break;
            case R.id.advancevieiw_nubelongs:
                startActivity(NumBelongtoActivity.class);
                break;
        }
    }
    /**
     * 开启新的activity不关闭自己
     * @param cls 新的activity的字节码
     * */

    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this,cls);
        startActivity(intent);

    }
}
