package com.lck.tools.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.lck.tools.R;

/**
 * 小工具合辑
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView_search;
    private LinearLayout LinearLayout_lssdjt;
    private LinearLayout LinearLayout_yqsssj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBar();
        initView();
    }

    private void initView() {
        imageView_search = findViewById(R.id.imageView_search);
        imageView_search.setOnClickListener(this);
        LinearLayout_lssdjt = findViewById(R.id.LinearLayout_lssdjt);
        LinearLayout_lssdjt.setOnClickListener(this);
        LinearLayout_yqsssj = findViewById(R.id.LinearLayout_yqsssj);
        LinearLayout_yqsssj.setOnClickListener(this);
    }

    private void initBar() {
        ImmersionBar.with(this).init();
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    /**
     * 提示信息
     * @param context
     * @param msg
     */
    public static void showToast(Context context, String msg){
        Toast toast=Toast.makeText(context,msg,Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView_search:
                //搜索小工具
                showToast(this,"搜索小工具");
                break;
            case R.id.LinearLayout_lssdjt:
//                showToast(this,"历史上的今天");
                Intent intent_TodayOfHistory = new Intent(this,TodayOfHistoryActivity.class);
                startActivity(intent_TodayOfHistory);
                break;
            case R.id.LinearLayout_yqsssj:
//                showToast(this,"疫情实时数据");
                Intent intent_PneumoniaData = new Intent(this,PneumoniaDataActivity.class);
                startActivity(intent_PneumoniaData);
                break;
        }
    }
}
