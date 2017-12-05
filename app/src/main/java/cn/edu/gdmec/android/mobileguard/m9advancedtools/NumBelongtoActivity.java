package cn.edu.gdmec.android.mobileguard.m9advancedtools;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import cn.edu.gdmec.android.mobileguard.R;

public class NumBelongtoActivity extends Activity {
    private EditText mNumET;
    private TextView mResultTV;
    private String dbName = "address.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_num_belongto);

    }
}
