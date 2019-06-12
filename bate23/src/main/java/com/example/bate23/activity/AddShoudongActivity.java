package com.example.bate23.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bate23.MainActivity;
import com.example.bate23.R;
import com.example.bate23.dao.DbManager;
import com.example.bate23.pojo.Info;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import static com.example.bate23.utils.UrlUtils.addurl;
import static com.example.bate23.utils.UrlUtils.loginurl;

public class AddShoudongActivity extends AppCompatActivity {

    private EditText editText1;
    private EditText editText2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shoudong);
        editText1 = (EditText) findViewById(R.id.shoudongname);
        editText2 = (EditText) findViewById(R.id.shoudongnumber);
    }

    public void backOnClick(View view) {
        this.finish();
    }

    public void concelOnClick(View view) {
        editText1.setText("");
        editText2.setText("");

    }

    public void ensureOnClick(View view) {
        String name = editText1.getText().toString();
        String number = editText2.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "名字不能为空", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(number)) {
            Toast.makeText(this, "电话号码不能为空", Toast.LENGTH_LONG).show();
        } else {
            String result = number;
            boolean regular = false;
            if(number.length() < 11){
                result = number + ".\\S{0,}";
                regular = true;
            }
            Info info = new Info();
            info.setName(name);
            info.setPhonenumber(result);
            DbManager.getInstance().addInfo(info,regular);
            Toast.makeText(this, "黑名单添加成功", Toast.LENGTH_LONG).show();
            String url = addurl;
            RequestParams params = new RequestParams(url);
            params.addParameter("phone", number);
            x.http().request(HttpMethod.PUT, params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Integer status=Integer.valueOf(result);
                    if (status==0){
                        Toast.makeText(x.app(),"添加到云端成功，感谢您的助力",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(x.app(),"添加的云端失败，请检查您的网络",Toast.LENGTH_LONG).show();
                    }
                    Intent intent=new Intent(AddShoudongActivity.this,UserInterfaceActivity.class);
                    startActivity(intent);
                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                }
                @Override
                public void onCancelled(CancelledException cex) {
                }
                @Override
                public void onFinished() {
                }
            });
            //修复了添加后无法返回的BUG
            startActivity(new Intent(this, UserInterfaceActivity.class));
        }
    }

}
