package cn.edu.gdmec.android.mobileguard.m2theftguard;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import cn.edu.gdmec.android.mobileguard.R;

/**
 * Created by HP on 2017/10/11.
 */

public class SetUp3Activity extends BaseSetUpActivity implements View.OnClickListener{
    private EditText mInputPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up3);
        //设置第3个小圆点的颜色
        ((RadioButton) findViewById(R.id.rb_third)).setChecked(true);
        findViewById(R.id.btn_addcontact).setOnClickListener(this);
        mInputPhone = (EditText) findViewById(R.id.et_inputphone);
        String safephone = sp.getString("safephone", null);
        if (!TextUtils.isEmpty(safephone)) {
            mInputPhone.setText(safephone);
        }
    }

    @Override
    public void showNext(){
        //判断文本输入框中是否有电话号码
        String safePhone = mInputPhone.getText().toString().trim();
        if(TextUtils.isEmpty(safePhone)){
            Toast.makeText(this,"请输入安全号码",Toast.LENGTH_LONG).show();
            return;
        }
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("safephone",safePhone);
        edit.commit();
        startActivityAndFinishSelf(SetUp4Activity.class);
    }

    @Override
    public void showPre(){
        startActivityAndFinishSelf(SetUp2Activity.class);
    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.btn_addcontact:
                startActivityForResult(new Intent(this,ContactSelectActivity.class),0);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(data!=null){
            String phone = data.getStringExtra("phone");
            mInputPhone.setText(phone);
        }
    }
}


