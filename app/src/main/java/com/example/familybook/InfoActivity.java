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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.familybook.dao.BillDaoImpl;
import com.example.familybook.dao.IBillDao;
import com.example.familybook.entity.Bill;

import java.util.Calendar;

public class InfoActivity extends Activity {
    private Bill mBill;
    private String mUsername;
    private int mBillID;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mPosition;
    private Spinner mTypeSpin;
    private EditText mMoney;
    private TextView mDate;
    private EditText mRemark;
    private Button mUpdateBillBtn;
    private Button mDeleteBillBtn;
    private IBillDao mIBillDao;
    private String mTypeText;
    private String TAG="InfoActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //获取登录者的用户名
        Intent intent =getIntent();
        mUsername =intent.getStringExtra("username");
        mBillID =intent.getIntExtra("bill_id",mBillID);


        Log.e(TAG,"使用者："+mUsername);
        Log.e(TAG,"账单号："+mBillID);

        //获取时间
        initDate();

        //初始化控件
        initView();

        //从数据库获取信息，并完成信息初始化
        mBill=mIBillDao.QueryBill(mBillID);
        initInfo();
        //添加监听事件
        initListener();
    }

    private void initInfo() {
        mTypeSpin.setSelection(mBill.getTypeposition());
        mMoney.setText(mBill.getMoney());
        mDate.setText(mBill.getDate());
        mRemark.setText(mBill.getRemark());
    }

    private void initDate() {
        //获取当前时间
        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
    }

    private void initListener() {
        mTypeSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPosition=position;
                mTypeText=parent.getItemAtPosition(position).toString();
                Toast.makeText(InfoActivity.this,mTypeText,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用时间选择器
                DatePickerDialog datePickerDialog = new DatePickerDialog(InfoActivity.this, R.style.MyDatePickerDialogTheme, onDateSetListener, mYear, mMonth, mDay);
                //获取时间戳
                long timeStamp = System.currentTimeMillis();
                //设置可以显示的最晚的时间
                datePickerDialog.getDatePicker().setMaxDate(timeStamp);
                //弹框
                datePickerDialog.show();
            }
        });
        mDeleteBillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBill();
            }
        });
        mUpdateBillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBill();
            }
        });

    }

    /**
     * 进行修改
     */
    private void updateBill() {
        Bill bill=null;
        //获取账目类型

        //获取账目金额
        String moneyText=mMoney.getText().toString().trim();
        //获取日期
        String  date= mDate.getText().toString().trim();
        //获取备注
        String remarkText=mRemark.getText().toString().trim();
        if(TextUtils.isEmpty(moneyText)){
            //账目金额为空
            Toast.makeText(this,"账目金额不可以为空",Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty((CharSequence) date)){
            //账目日期为空
            Toast.makeText(this,"账目日期不可以为空",Toast.LENGTH_SHORT).show();
            return;
        }else {
            Log.e(TAG, "账户：" + mUsername);
            Log.e(TAG, "类型：" + mTypeText);
            Log.e(TAG, "类型：" + mPosition);
            Log.e(TAG, "金额：" + moneyText);
            Log.e(TAG, "日期：" + date);
            Log.e(TAG, "备注：" + remarkText);
            bill=new Bill(mUsername,mTypeText,mPosition, moneyText,date,remarkText);
            boolean rs=mIBillDao.Update(mBillID,bill);
            Log.e(TAG,"rs："+rs);
            if (rs ) {
                //修改成功，跳回账页
                Intent intent = new Intent();
                intent.putExtra("username",mUsername);
                intent.setClass(this, QueryShowActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    /**
     * 进行删除
     */
    private void deleteBill() {
        boolean rs=mIBillDao.DeleteBill(mBillID);
        if(rs){
            //删除成功，跳回账页
            Intent intent = new Intent();
            intent.putExtra("username",mUsername);
            intent.setClass(this, QueryShowActivity.class);
            startActivity(intent);
            finish();
        }

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
        mTypeSpin =(Spinner) this.findViewById(R.id.type_spin);
        mMoney =(EditText)this.findViewById(R.id.bill_money);
        mDate =(TextView) this.findViewById(R.id.bill_date);
        mRemark=(EditText)this.findViewById(R.id.bill_remark);
        mUpdateBillBtn=(Button)this.findViewById(R.id.update_btn);
        mDeleteBillBtn=(Button)this.findViewById(R.id.delete_btn);
        mIBillDao  =new BillDaoImpl(InfoActivity.this);
    }
}
