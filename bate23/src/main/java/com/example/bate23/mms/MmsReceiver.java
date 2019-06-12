package com.example.bate23.mms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;
import com.example.bate23.dao.DbManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Thread.sleep;

public class MmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        //接收由sms传递过来的数据
        Bundle extras = intent.getExtras();
        //通过pdus可以获得接收到的所有短信信息
        Object[] array = (Object[]) extras.get("pdus");
        //因为可能同时获得多个信息
        for (Object o1 : array) {
            SmsMessage message = SmsMessage.createFromPdu((byte[]) o1);
            //获得接收短信的电话号码
            String adress = message.getOriginatingAddress();
            //获得短信的内容
            String content = message.getDisplayMessageBody();
            //设置跳出循环的时长
            Integer time=4000;
            //获取当前系统时间
            Date date = new Date();
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String strdate=dateFormat.format(date);
            try {
                //当前线程等待
                sleep(1000);
                while(true){
                    //计数器减少
                    time-=500;
                    //显示信息
                    if(DbManager.getInstance().getGlobalSetting()) {
                        if (DbManager.getInstance().phonenumberisExist(adress) || (DbManager.getInstance().phonenumberisExist("+86" +adress))) {

                        }else{
                            Toast.makeText(context, "来自"+adress+"的一条短信\r\n"+content+"\r\n"+strdate, Toast.LENGTH_LONG)
                                    .show();
                        }
                    }

                    if (time==0)break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
