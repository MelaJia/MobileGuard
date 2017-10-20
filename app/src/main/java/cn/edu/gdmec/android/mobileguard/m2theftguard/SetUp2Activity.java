package cn.edu.gdmec.android.mobileguard.m2theftguard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import cn.edu.gdmec.android.mobileguard.R;

/**
 * Created by HP on 2017/10/11.
 */

public class SetUp2Activity extends BaseSetUpActivity implements View.OnClickListener{
    private TelephonyManager mTelephonyManager;
    private Button mBindSTMBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up2);
        ((RadioButton) findViewById(R.id.rb_second)).setChecked(true);
        mTelephonyManager=(TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        ((RadioButton)findViewById(R.id.rb_second)).setChecked(true);
        mBindSTMBtn = (Button)findViewById(R.id.btn_bind_sim);
        mBindSTMBtn.setOnClickListener(this);
        if(isBind()){
            mBindSTMBtn.setEnabled(false);
        }else {
            mBindSTMBtn.setEnabled(true);
        }
    }



    /*
    * initView（）方法初始化控件
    * */
 /* private void initView(){
//        设置第二个小圆点颜色
((RadioButton)findViewById(R.id.rb_second)).setChecked(true);
        mBindSTMBtn = (Button)findViewById(R.id.btn_bind_sim);
        mBindSTMBtn.setOnClickListener(this);
        if(isBind()){
            mBindSTMBtn.setEnabled(false);
       }else {
           mBindSTMBtn.setEnabled(true);
        }
   }*/

    private boolean isBind(){
        String simString = sp.getString("sim", null);
        if (TextUtils.isEmpty(simString)){
            return false;
        }
        return true;
    }
    @Override
    public void showNext() {
        if (!isBind()){
            Toast.makeText(this,"您还没有绑定sim卡！",Toast.LENGTH_LONG).show();
            return;
        }
        startActivityAndFinishSelf(SetUp3Activity.class);
    }

    @Override
    public void showPre() {
        startActivityAndFinishSelf(SetUp1Activity.class);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_bind_sim:
                //绑定sim卡
                bindSIM();
                break;
        }
    }
    //
//    /**
//     * 绑定sim卡
//     * */
    private void bindSIM(){
        if (!isBind()){
            String simSerialNumber = mTelephonyManager.getSimSerialNumber();
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("sim",simSerialNumber);
            edit.commit();
            Toast.makeText(this,"SIM卡绑定成功！",Toast.LENGTH_LONG).show();
            mBindSTMBtn.setEnabled(false);
        }else {
            //已经绑定，提醒用户
            Toast.makeText(this,"SIM卡已经绑定！",Toast.LENGTH_LONG).show();
            mBindSTMBtn.setEnabled(false);
        }
    }

}

