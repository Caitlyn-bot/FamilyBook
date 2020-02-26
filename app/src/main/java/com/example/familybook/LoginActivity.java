package com.example.familybook;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.familybook.dao.IUserDao;
import com.example.familybook.dao.UserDaoImpl;
import com.example.familybook.database.FamilyBookDatabaseHelper;

public class LoginActivity  extends AppCompatActivity {

    private EditText mUsername;
    private EditText mPassword;
    private Button mLogin;
    private Button mGotoregister;
    private  static final int mRegisterCode=1;
    private IUserDao mIUserDao;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //创建数据库
        FamilyBookDatabaseHelper helper =new FamilyBookDatabaseHelper(LoginActivity.this);
        helper.getWritableDatabase();

        //初始化控件
        initView();
        //设置事件监听
        initListener();
    }

    private void initListener() {
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        mGotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        Intent intent =new Intent(LoginActivity.this, RegisterActivity.class);
        startActivityForResult(intent,mRegisterCode);
    }

    private void login() {
        //账号
        String usernameText =mUsername.getText().toString();
        //密码
        String passwordText =mPassword.getText().toString();

        //对账号和密码进行检测
        if(TextUtils.isEmpty(usernameText)){
            //账号为空
            Toast.makeText(this,"用户名不可以为空",Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(passwordText)){
            //密码为空
            Toast.makeText(this,"密码不可以为空",Toast.LENGTH_SHORT).show();
        }else {
            boolean result = mIUserDao.Login(usernameText, passwordText);
            if (result) {

                Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
                intent.putExtra("username",usernameText);
                startActivity(intent);
            } else {
                //登录失败
                Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initView() {
        mUsername=(EditText)this.findViewById(R.id.username);
        mPassword=(EditText)this.findViewById(R.id.password);
        mLogin=(Button) this.findViewById(R.id.login_btn);
        mGotoregister=(Button)this.findViewById(R.id.goto_register_btn);
        mIUserDao=new UserDaoImpl(LoginActivity.this);
    }
    /**
     * 返回的结果在这里回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String resultContent = null;
        //当我们判断这个请求码的时候，就知道，这是注册界面返回的结果
        if (requestCode==mRegisterCode) {
            if(resultCode==2){
                //注册成功
                Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
