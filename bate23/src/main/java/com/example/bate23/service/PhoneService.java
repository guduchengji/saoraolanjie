package com.example.bate23.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.internal.telephony.ITelephony;
import com.example.bate23.dao.DbManager;
import com.example.bate23.pojo.PhoneCall;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class PhoneService extends Service {

    TelephonyManager tManager;
    CustomPhone custonPhne;

    public class CustomPhone extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String number) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    if(DbManager.getInstance().getGlobalSetting()) {
                        //换用另一种方式
                        endCall();
                        //rejectCall();
                        break;
                    }
                    if (DbManager.getInstance().phonenumberisExist("+86" + number)) {
                        try {

                        //换用另一种方式
                            endCall();
                            //rejectCall();
                            //把来电记录存入拦截记录表中
                            PhoneCall phoneCall = new PhoneCall();
                            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String time = sDateFormat.format(new Date());
                            phoneCall.setTime(time);
                            phoneCall.setPhonenumber("+86" + number);
                            DbManager.getInstance().addPhoneCall(phoneCall);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    break;
            }

        }
    }

    public void endCall(){
        try {
        Method method = Class.forName("android.os.ServiceManager")
                .getMethod("getService", String.class);
        IBinder invoke  = (IBinder) method.invoke(null, Context.TELEPHONY_SERVICE);

        ITelephony telephony = ITelephony.Stub
                .asInterface(invoke);
        // 挂断电话
        Log.d("PhoneService", "挂断");
        telephony.endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //拦截电话
        tManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        custonPhne = new CustomPhone();
        tManager.listen(custonPhne, PhoneStateListener.LISTEN_CALL_STATE);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public  void rejectCall() {
        try {
            Method method = Class.forName("android.os.ServiceManager")
                    .getMethod("getService", String.class);
            IBinder binder = (IBinder) method.invoke(null, new Object[]{Context.TELEPHONY_SERVICE});
            ITelephony telephony = ITelephony.Stub.asInterface(binder);
            telephony.endCall();
        } catch (NoSuchMethodException e) {
            Log.d(TAG, "", e);
        } catch (ClassNotFoundException e) {
            Log.d(TAG, "", e);
        } catch (Exception e) {
        }
    }
}

