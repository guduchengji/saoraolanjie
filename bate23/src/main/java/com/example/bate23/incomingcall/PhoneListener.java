package com.example.bate23.incomingcall;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.internal.telephony.ITelephony;
import com.example.bate23.dao.DbManager;
import com.example.bate23.pojo.PhoneCall;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

import static android.content.ContentValues.TAG;

//添加了一个事件监听用来监听来电
//如果一次没有拦截到，使用Listener
public class PhoneListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.e("PhoneListener",action);
        if (action.equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
        } else {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Service.TELEPHONY_SERVICE);
            String incoming_number = "";
            switch (tm.getCallState()) {
                case TelephonyManager.CALL_STATE_RINGING:
                    incoming_number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    if(DbManager.getInstance().getGlobalSetting()) {
                        //换用另一种方式
                        //endCall();
                        rejectCall();
                        break;
                    }
                    if (DbManager.getInstance().phonenumberisExist("+86" + incoming_number)) {
                        try {

                            //换用另一种方式
                            //endCall();
                            rejectCall();
                            //把来电记录存入拦截记录表中
                            PhoneCall phoneCall = new PhoneCall();
                            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String time = sDateFormat.format(System.currentTimeMillis());
                            phoneCall.setTime(time);
                            phoneCall.setPhonenumber("+86" + incoming_number);
                            //DbManager.getInstance().addPhoneCall(phoneCall);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
            }
        }
    }
    public static void rejectCall() {
        try {
            Method method = Class.forName("android.os.ServiceManager")
                    .getMethod("getService", String.class);
            IBinder binder = (IBinder) method.invoke(null, new Object[]{Context.TELEPHONY_SERVICE});
            ITelephony telephony = ITelephony.Stub.asInterface(binder);
            Log.d(TAG, "快要挂电话了");
//            telephony.endCall();
        } catch (NoSuchMethodException e) {
            Log.d(TAG, "", e);
        } catch (ClassNotFoundException e) {
            Log.d(TAG, "", e);
        } catch (Exception e) {
        }
    }
}
