package com.example.bate23.activity;


import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bate23.R;
import com.example.bate23.adapter.ContactsAdapter;
import com.example.bate23.dao.DbManager;
import com.example.bate23.pojo.Info;

import java.util.ArrayList;
import java.util.List;

public class AddFromComtactsActivity extends AppCompatActivity {

    private ListView listView;
    private ContactsAdapter adapter;
    private List<Info> infoList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_from_comtacts);
        infoList = getInfos();
        listView = (ListView) findViewById(R.id.contacts);
        adapter = new ContactsAdapter(this, infoList);
        listView.setAdapter(adapter);
    }

    //使用contentprovider获取通讯录中的所有联系人的姓名和电话
    private List<Info> getInfos() {
        ContentResolver cr = getContentResolver();
        //得到的是所有的联系人信息
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null);
        List<Info> infoList = new ArrayList<>();
        while (cursor.moveToNext()) {
            //Info info = new Info();
            // 取得联系人名字
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            String name = cursor.getString(nameFieldColumnIndex);

            // 取得联系人ID
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            //这里的URI是一个字段
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
                    + contactId, null, null);

            //List<String> list = new ArrayList<>();
            // 取得电话号码(可能存在多个号码),这里我就默认一个人
            while (phone.moveToNext()) {
                String strPhoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                //这里把同一人不同电话分为不同行处理了
                Info info = new Info();
                info.setName(name);
                info.setPhonenumber(strPhoneNumber);
                //判断这个info是否在黑名单中，如果在就不加入进去
                if (!DbManager.getInstance().phonenumberisExist(strPhoneNumber))
                    infoList.add(info);
            }
            phone.close();
        }
        cursor.close();
        return infoList;
    }


    public void backOnClick(View view) {
        this.finish();
    }

    //取消按钮的点击事件
    public void concelOnClick(View view) {
        // 遍历listview的长度，将已选的按钮设为未选
        for (int i = 0; i < infoList.size(); i++) {

            if ((adapter.getmCBFlag()).get(i)) {
                adapter.getmCBFlag().put(i, false);
            }
            adapter.notifyDataSetChanged();
        }
        startActivity(new Intent(AddFromComtactsActivity.this,UserInterfaceActivity.class));
    }

    //确定按钮的点击事件
    public void ensureOnClick(View view) {
        // 遍历listview的长度，找到选中的按钮，然后把其中的的姓名和电话取出来，存到新建的数据库中
        for (int i = 0; i < infoList.size(); i++) {
            if (adapter.getmCBFlag().get(i)) {
                //取出这条数据，然后存放到数据库中
                Info info = new Info();
                info.setName(infoList.get(i).getName());

                String result = infoList.get(i).getPhonenumber();
                boolean regular = false;
                if(result.length() < 11){
                    result = result + ".\\S{0,}";
                    regular = true;
                }
                info.setPhonenumber(result);
                DbManager.getInstance().addInfo(info,regular);
            }
        }
        this.finish();
    }
}
