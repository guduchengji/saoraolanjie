package com.example.bate23;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.bate23.activity.LoginActivity;
import com.example.bate23.activity.RegistActivity;
import com.example.bate23.activity.UserInterfaceActivity;

public class MainActivity extends AppCompatActivity {
    private Button login;
    private Button regist;
    private Button skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        login=findViewById(R.id.login);
        regist=findViewById(R.id.regist);
        skip=findViewById(R.id.skip);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, RegistActivity.class);
                startActivity(intent);
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, UserInterfaceActivity.class);
                startActivity(intent);
            }
        });
    }
    //用来获取当前线程
    private long getThreadId() {


        long threadId = 0;


        String SMS_READ_COLUMN = "read";


        String WHERE_CONDITION = SMS_READ_COLUMN + " = 0";


        String SORT_ORDER = "date DESC";


        int count = 0;


        Cursor cursor = this.getContentResolver().query(


                Uri.parse("content://sms/inbox"),new String[]{ "_id", "thread_id", "address", "person", "date", "body" },

                WHERE_CONDITION,null,SORT_ORDER);


        if (cursor != null) {


            try {


                count = cursor.getCount();


                if (count > 0) {


                    cursor.moveToFirst();


                    threadId = cursor.getLong(1);


                }


            } finally {


                cursor.close();


            }


        }


        Log.i("threadId", String.valueOf(threadId));


        return threadId;


    }
}
