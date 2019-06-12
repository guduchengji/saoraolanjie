package com.example.bate23.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bate23.R;
import com.example.bate23.dao.DbManager;
import com.example.bate23.utils.SendEmailUtil;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import static com.example.bate23.utils.UrlUtils.addurl;
import static com.example.bate23.utils.UrlUtils.bugsub;


public class SettingActivity extends AppCompatActivity {

//    主窗口的构造方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
//        Switch smart_switch = findViewById(R.id.switch_smart);
//        smart_switch.setChecked(DbManager.getInstance().getSmartSetting());;
//        smart_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                DbManager.getInstance().setSmartSetting(b);
//                Log.i("message","smart:"+b);
//            }
//        });

        Switch global_switch = findViewById(R.id.switch_global);
        global_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //监听到设置全局监听之后会开启全局的拦截
                Toast.makeText(x.app(),"全局拦截开启\r\n您将不会受到任何来电\r\n短信将会记录在拦截短信列表中",Toast.LENGTH_LONG).show();
                DbManager.getInstance().setGlobalSetting(b);
                Log.i("message","global:"+b);
            }
        });
        global_switch.setChecked(DbManager.getInstance().getGlobalSetting());;
        RelativeLayout bug_layout = findViewById(R.id.layout_bug);
        //设置监听事件
        bug_layout.setOnClickListener(new View.OnClickListener() {
            //修改事件
            @Override
            public void onClick(View v) {
                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendEmail();
                    }
                });
                thread.start();
                Toast.makeText(x.app(),"邮件已发送给开发者\r\n感谢您的提交",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(SettingActivity.this,UserInterfaceActivity.class);
                startActivity(intent);
            }
        });
    }

    public void backOnClick(View view) {
        this.finish();
    }
    private void sendEmail(){
        try {
            SendEmailUtil.sendEmail();
            //Toast.makeText(x.app(),"邮件已发送给开发者，感谢您的提交",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
            //Toast.makeText(x.app(),"邮件发送失败，请检查您的网络",Toast.LENGTH_LONG).show();
    }
}
