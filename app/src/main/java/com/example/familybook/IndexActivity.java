package com.example.familybook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class IndexActivity extends Activity {
    private String mUsername;
    private Button mAddBillBtn;
    private Button mQueryAllBtn;
    private Button mQueryByCondition;
    private String TAG="IndexActivity";
    private static final int mAddBillCode =1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);



        //获取登录者的用户名
        Intent intent =getIntent();
        mUsername =intent.getStringExtra("username");
        Log.e(TAG,"使用者："+mUsername);
        //初始化控件
        initView();

        //添加事件监听
        initListener();
    }

    private void initListener() {
        mAddBillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addbillhandler();
            }
        });
        mQueryAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QueryAllBillhandler();
            }
        });
        mQueryByCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryByCondition();
            }
        });
    }

    private void queryByCondition() {
        //跳转到按需查看账目的页面
        Intent intent=new Intent(IndexActivity.this,QueryByConditionActivity.class);
        intent.putExtra("username",mUsername);
        startActivity(intent);
    }

    private void QueryAllBillhandler() {
        //跳转到查看账目的页面
        Intent intent =new Intent(IndexActivity.this, QueryShowActivity.class);
        intent.putExtra("username",mUsername);
        intent.putExtra("from","1");
        startActivity(intent);
    }

    private void addbillhandler() {
      //跳转到添加账目的页面
        Intent intent =new Intent(IndexActivity.this,AddBillActivity.class);
        intent.putExtra("username",mUsername);
        startActivityForResult(intent,mAddBillCode);

    }

    private void initView() {
        mAddBillBtn=(Button) this.findViewById(R.id.goto_add_bill_btn);
        mQueryAllBtn=(Button)this.findViewById(R.id.goto_QueryAllBill);
        mQueryByCondition=(Button)this.findViewById(R.id.goto_query_by_condition);
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
        //当我们判断这个请求码的时候，就知道，这是哪个界面返回的结果
        if (requestCode==mAddBillCode) {
            if(resultCode==2){
                //注册成功
                Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
