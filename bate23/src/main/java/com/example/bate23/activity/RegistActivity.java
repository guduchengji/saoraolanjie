package com.example.bate23.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bate23.R;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import static com.example.bate23.utils.UrlUtils.loginurl;
import static com.example.bate23.utils.UrlUtils.registurl;

public class RegistActivity extends AppCompatActivity {
    private Button regist;
    private Button back;
    private TextView usernameText;
    private TextView passwordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist);
        regist=findViewById(R.id.registregist);
        back=findViewById(R.id.registback);
        usernameText=findViewById(R.id.registusername);
        passwordText=findViewById(R.id.registpassword);

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = registurl;
                RequestParams params = new RequestParams(url);
                //params.setSslSocketFactory(...); // 设置ssl
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();

                if (username.isEmpty()||username==null||username==""){

                    Toast.makeText(x.app(),"用户不符合要求 请核对用户名",Toast.LENGTH_LONG).show();
                    return;
                }
                if (password.isEmpty()||password==null||password==""){
                    Toast.makeText(x.app(),"密码不能为空",Toast.LENGTH_LONG).show();
                    return;
                }
                if (password.length()<6){
                    Toast.makeText(x.app(),"密码长度不合适",Toast.LENGTH_LONG).show();
                    return;
                }
                params.addQueryStringParameter("name",username);
                params.addQueryStringParameter("password",password);
                x.http().get(params, new Callback.CommonCallback<String>() {

                    public void onSuccess(String result) {
                        Integer status = Integer.valueOf(result);
                        switch (status){
                            case 1:
                                Toast.makeText(x.app(), "注册成功", Toast.LENGTH_LONG).show();
                                Log.i("注册信息返回", "onSuccess result:" + result);
                                Intent intent=new Intent(RegistActivity.this,LoginActivity.class);
                                startActivity(intent);
                                break;
                            case 0:
                                Toast.makeText(x.app(), "注册失败  请该换用户名", Toast.LENGTH_LONG).show();
                                Log.i("注册信息返回", "onSuccess result:" + result);
                                passwordText.setText(null);
                                break;
                            case 2:
                                Toast.makeText(x.app(), "注册失败  请更换合适的用户名和密码", Toast.LENGTH_LONG).show();
                                Log.i("注册信息返回", "onSuccess result:" + result);
                                passwordText.setText(null);
                                break;
                        }
//
//                        Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
//                        Log.i("注册信息返回", "onSuccess result:" + result);
//                        Intent intent=new Intent(RegistActivity.this,UserInterfaceActivity.class);
//                        startActivity(intent);
                    }
                    //请求异常后的回调方法
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Toast.makeText(x.app(), "网络异常 请检查您的网络", Toast.LENGTH_LONG).show();
                        usernameText.setText(null);
                        passwordText.setText(null);
                    }
                    //主动调用取消请求的回调方法
                    @Override
                    public void onCancelled(CancelledException cex) {
                    }
                    @Override
                    public void onFinished() {

                    }
                });
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegistActivity.this,LoginActivity.class);
                startActivity(intent);
                usernameText.setText(null);
                passwordText.setText(null);
            }
        });


    }

}
