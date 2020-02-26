package com.example.familybook;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.familybook.dao.BillDaoImpl;
import com.example.familybook.dao.IBillDao;
import com.example.familybook.entity.Bill;
import com.example.familybook.utils.ShowBillsAdapter;

import java.util.List;

public class QueryShowActivity extends Activity {
    private String mUsername;
    private String mTypeText;
    private String mDateText;
    private String mFrom;
    private String TAG="QueryShowActivity";
    private List <Bill> mShowBills;
    private IBillDao mIBillDao;
    private SQLiteOpenHelper mBillDatabaseHelper;
    private ListView mShowBillList;
    private int mInfoCode=1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        this.setTitle("总览账目信息");

        //获取登录者的用户名
        Intent intent =getIntent();
        mUsername =intent.getStringExtra("username");
        mFrom=intent.getStringExtra("from");
        Log.e(TAG,"使用者："+mUsername);

        //初始化控件
        initView();

        //获取表格布局
        mShowBillList=(ListView)findViewById(R.id.list_View);

        //获取从数据库获得的表单
        if(mFrom.equals("1")){
            //来自首页，直接查询全部
            mShowBills=mIBillDao.listAllBill(mUsername);
        }else if(mFrom.equals("2")){
            //来自按条件查询
            mTypeText=intent.getStringExtra("type");
            mDateText=intent.getStringExtra("date");
            mShowBills=mIBillDao.listConditionBill(mUsername,mTypeText,mDateText);
        }

        //新建并配置Show
        ShowBillsAdapter  ShowAdapter=new ShowBillsAdapter(mShowBills,this);
        mShowBillList.setAdapter(ShowAdapter);
        mShowBillList.deferNotifyDataSetChanged();
        //添加事件监听
        initListener();
    }




    private void initListener() {
        mShowBillList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bill bill =mShowBills.get(position);
                int bill_id=bill.get_id();
                Intent intent=new Intent();
                intent.putExtra("username",mUsername);
                intent.putExtra("bill_id",bill_id);
                intent.setClass(QueryShowActivity.this,InfoActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initView() {
        mIBillDao =new BillDaoImpl(QueryShowActivity.this);

    }

}
