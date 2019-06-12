package com.example.bate23.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bate23.R;
import com.example.bate23.dao.DbManager;
import com.example.bate23.service.PhoneService;


public class UserInterfaceActivity extends AppCompatActivity implements View.OnClickListener {
    //声明布局
    //短信Layout
    //电话Layout
    //设置Layout
    //黑名单Layout
    private RelativeLayout messageLayout, phoneLayout, settingLayout,darkLayout;
    //本例的构造相关
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_interface);
        startObserver();
        DbManager.createInstance(getBaseContext());

        initView();
    }

    public void startObserver(){
        Intent intent = new Intent(this, PhoneService.class);
        startService(intent);
}
    //初始化View
    private void initView() {
        messageLayout = (RelativeLayout) findViewById(R.id.message_layout);
        phoneLayout = (RelativeLayout) findViewById(R.id.phone_layout);
        settingLayout = (RelativeLayout) findViewById(R.id.setting_setting);
        darkLayout = (RelativeLayout) findViewById(R.id.dark_layout);

        messageLayout.setOnClickListener(this);
        phoneLayout.setOnClickListener(this);
        settingLayout.setOnClickListener(this);
        darkLayout.setOnClickListener(this);
    }
//    为点击窗口监听一个切换Acticity的点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dark_layout:
                startActivity(new Intent(this, DarkFromActivity.class));
                break;
            case R.id.message_layout:
                startActivity(new Intent(this, MessageActivity.class));
//                startActivity(new Intent(this, PhoneCallActivity.class));
                break;
            case R.id.phone_layout:
                startActivity(new Intent(this, PhoneCallActivity.class));
                break;
            case R.id.setting_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }
}

