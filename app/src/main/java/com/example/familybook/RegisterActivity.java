package com.example.familybook;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.familybook.dao.IUserDao;
import com.example.familybook.dao.UserDaoImpl;
import com.example.familybook.entity.User;
public class RegisterActivity extends Activity{


        private RadioGroup mSex;
        private EditText mUsername;
        private EditText mPassword;
        private Button mRegisterBtn;
        private RadioButton mR;
        private IUserDao mIUserDao;
        private String TAG="RegisterActivity";

    @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);
            //初始化控件
            initView();
            //设置监听，处理点击事件
            initListener();
        }

        private void initListener() {
            mSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    mR =(RadioButton) findViewById(checkedId);
                }
            });
            mRegisterBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击了注册按钮
                    Registerhandler();
                }
            });
        }

        private void Registerhandler() {
            User user =null;
            //账号
            String usernameText =mUsername.getText().toString().trim();
            //密码
            String passwordText =mPassword.getText().toString().trim();
            //性别
            String sexText;
            if(mR.getText().toString()!=null) {
                 sexText = mR.getText().toString();
            }else{
                sexText="男";
            }
            Log.e(TAG,"账户："+usernameText);
            Log.e(TAG,"密码："+passwordText);
            Log.e(TAG,"性别："+sexText);
            //对账号和密码进行检测
            if(TextUtils.isEmpty(usernameText)){
                //账号为空
                Toast.makeText(this,"用户名不可以为空",Toast.LENGTH_SHORT).show();
                return;
            }else if(TextUtils.isEmpty(passwordText)){
                //密码为空
                Toast.makeText(this,"密码不可以为空",Toast.LENGTH_SHORT).show();
            }else {
                boolean result = mIUserDao.isExist(usernameText);
                if (result) {
                    //用户名已被使用
                    Toast.makeText(this, "此用户名已被注册", Toast.LENGTH_SHORT).show();
                } else {
                    user = new User(usernameText, passwordText, sexText);
                    int rs = (int) mIUserDao.Register(user);
                    Log.e(TAG,"rs："+rs);
                    if (rs > -1) {
                        //注册成功，跳回登录页面
                        Intent intent = new Intent();
                        setResult(2, intent);
                        finish();
                    }
                }
            }
        }

        private void initView() {
            mUsername = (EditText)this.findViewById(R.id.r_username);
            mPassword = (EditText)this.findViewById(R.id.r_password);
            mSex = (RadioGroup)this.findViewById(R.id.radioGroup);
            mRegisterBtn =(Button)this.findViewById(R.id.register_btn);
            mIUserDao =new UserDaoImpl(RegisterActivity.this);
            mR=(RadioButton)this.findViewById(R.id.man_radio);
        }



}
