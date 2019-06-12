package com.example.bate23.broadcastreceive;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
import com.example.bate23.MainActivity;
import com.example.bate23.dao.DbManager;
import com.example.bate23.pojo.Message;


import java.text.SimpleDateFormat;
import java.util.Date;

public class SmsBroadcastReceiver extends BroadcastReceiver {

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


            if(DbManager.getInstance().getGlobalSetting()) {
                //换用另一种方式
                //endCall();
                Message receive = new Message();
                Log.i("message","-----------------拦截短信");
                if (DbManager.getInstance().phonenumberisExist(adress) || (DbManager.getInstance().phonenumberisExist("+86" +adress))) {
                    Toast.makeText(context, "您有一条来自\r\n"+adress+"的一条短信被拦截", Toast.LENGTH_LONG)
                            .show();
                }else{
                    Toast.makeText(context, "来自"+adress+"的一条短信被拦截", Toast.LENGTH_LONG)
                            .show();
                }

                receive.setPhonenumber(adress);
                receive.setContent(content);
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = sDateFormat.format(System.currentTimeMillis());
                receive.setTime(time);
                DbManager.getInstance().addMessage(receive);

                break;
            }


            if (DbManager.getInstance().phonenumberisExist(adress) || (DbManager.getInstance().phonenumberisExist("+86" +adress))) {
                //把短信加入短信拦截表
                //修改了短信变量
                Message receive = new Message();
                receive.setPhonenumber(adress);
                receive.setContent(content);
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = sDateFormat.format(System.currentTimeMillis());
                receive.setTime(time);
                Log.i("message","-----------------拦截短信");
                Toast.makeText(context, "来自"+adress+"的一条短信被拦截", Toast.LENGTH_LONG)
                        .show();
                DbManager.getInstance().addMessage(receive);
                int status = message.getStatus();
                Toast.makeText(context, "状态"+status, Toast.LENGTH_LONG)
                        .show();
                Log.i("收到的短信",message.toString());
                abortBroadcast();
            }else{
                //把短信加入短信拦截表
                //修改了短信变量
                Message receive = new Message();
                receive.setPhonenumber(adress);
                receive.setContent(content);
                Date date = new Date();
                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String strdate=dateFormat.format(date);
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = sDateFormat.format(System.currentTimeMillis());
                receive.setTime(time);

                Toast.makeText(context, "来自"+adress+"的一条短信\r\n"+content+"\r\n"+strdate, Toast.LENGTH_LONG)
                        .show();
                DbManager.getInstance().addMessage(receive);
                int status = message.getStatus();
                Toast.makeText(context, "状态"+status, Toast.LENGTH_LONG)
                        .show();
                Log.i("收到的短信",message.toString());
                abortBroadcast();
            }
        }
    }




}
