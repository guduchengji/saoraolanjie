package com.example.bate23.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bate23.MainActivity;
import com.example.bate23.R;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import static com.example.bate23.utils.UrlUtils.loginurl;

public class LoginActivity extends AppCompatActivity {
    private TextView usernameTextView;
    private TextView passwordTextView;
    private Button loginlogin;
    private Button loginback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        loginlogin = findViewById(R.id.loginlogin);
        loginback=findViewById(R.id.loginback);
        usernameTextView=findViewById(R.id.loginusername);
        passwordTextView=findViewById(R.id.passwordText);
        loginlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = loginurl;
                RequestParams params = new RequestParams(url);
                String username = usernameTextView.getText().toString();
                String password = passwordTextView.getText().toString();
                params.addParameter("name", username);
                params.addParameter("password", password);
                x.http().request(HttpMethod.PUT, params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Integer status=Integer.valueOf(result);
                        switch (status){
                            case 0:
                                Toast.makeText(x.app(),"用户不存在 请核对用户名",Toast.LENGTH_LONG).show();
                                break;
                            case 1:
                                Toast.makeText(x.app(),"密码不正确 请核对密码",Toast.LENGTH_LONG).show();
                                break;
                            case 2:
                                Toast.makeText(x.app(),"登陆成功~",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(LoginActivity.this,UserInterfaceActivity.class);
                                startActivity(intent);
                                break;
                        }
                    }
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Toast.makeText(x.app(),"请检查当前网络环境\r\n或者跳过登陆？",Toast.LENGTH_LONG).show();

                    }
                    @Override
                    public void onCancelled(CancelledException cex) {
                    }
                    @Override
                    public void onFinished() {
                    }
                });
            }
        });
        loginback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordTextView.setText(null);
                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
//                usernameTextView.setText(null);

            }
        });
    }

}
