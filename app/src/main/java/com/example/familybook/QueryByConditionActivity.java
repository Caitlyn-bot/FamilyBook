package com.example.familybook;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class QueryByConditionActivity extends Activity {
    private Spinner mSpinner;
    private int mPosition;
    private int mYear;
    private int mMonth;
    private int mDay;
    private TextView mDate;
    private String mTypeText;
    private String mDateText;
    private Button mGotoQueryByCondition;
    private String TAG="QueryByConditionActivity";
    private String mUsername;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_querybycondition);
        //获取登录者的用户名
        Intent intent =getIntent();
        mUsername =intent.getStringExtra("username");


        Log.e(TAG,"使用者："+mUsername);

        //获取时间
        initDate();

        //初始化控件
        initView();
        //添加监听事件
        initListener();
    }
    private void initDate() {
        //获取当前时间
        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
    }

    private void initListener() {
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPosition=position;
                mTypeText=parent.getItemAtPosition(position).toString();
                Toast.makeText(QueryByConditionActivity.this,mTypeText,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用时间选择器
                DatePickerDialog datePickerDialog = new DatePickerDialog(QueryByConditionActivity.this, R.style.MyDatePickerDialogTheme, onDateSetListener, mYear, mMonth, mDay);
                //获取时间戳
                long timeStamp = System.currentTimeMillis();
                //设置可以显示的最晚的时间
                datePickerDialog.getDatePicker().setMaxDate(timeStamp);
                //弹框
                datePickerDialog.show();
            }
        });
        mGotoQueryByCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showlist();
            }
        });
    }

    private void showlist() {
        mDateText=mDate.getText().toString().trim();
        //获取账目类型
        if(mTypeText==null){
            //说明用户未点击按钮，选择默认首项：饮食
            mTypeText="饮食";
            mPosition=0;
        }else if(TextUtils.isEmpty((mDateText) )){
            //账目日期为空
            Toast.makeText(this,"账目日期不可以为空",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent=new Intent();
        intent.putExtra("username",mUsername);
        intent.putExtra("from","2");
        intent.putExtra("type",mTypeText);
        intent.putExtra("date",mDateText);
        intent.setClass(this, QueryShowActivity.class);
        startActivity(intent);
    }

    /**
     * 日期选择器对话监听
     */
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;

            String days;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("-").append("0").
                            append(mMonth + 1).append("-").append("0").append(mDay).append("").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("-").append("0").
                            append(mMonth + 1).append("-").append(mDay).append("").toString();
                }

            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("-").
                            append(mMonth + 1).append("-").append("0").append(mDay).append("").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("-").
                            append(mMonth + 1).append("-").append(mDay).append("").toString();
                }

            }

            mDate.setText(days);
        }
    };

    private void initView() {
        mSpinner=(Spinner)findViewById(R.id.type_condition);
        mDate=(TextView)findViewById(R.id.condition_bill_date);
        mGotoQueryByCondition=(Button)findViewById(R.id.query_by_condition);
    }
}
