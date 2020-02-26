package com.example.familybook.database;
/**
 * 这是User对象的实体类数据库帮助类
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import androidx.annotation.Nullable;

import com.example.familybook.utils.Constants;


public class FamilyBookDatabaseHelper extends SQLiteOpenHelper {


    private String TAG="FamilyBookDatabaseHelper";

    public FamilyBookDatabaseHelper(@Nullable Context context) {
        super(context, Constants.FAMILYBOOK_DB_NAME,null,Constants.FAMILYBOOK_DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * 创建数据库
         */
        Log.e(TAG,"数据库创建");
        String usersql=" create table  " +Constants.USER_TABLE_NAME +"("+Constants.USER_TABLE_FIELD_ID + " integer primary key autoincrement," +Constants.USER_TABLE_FIELD_UNAME +" varchar(30)," +Constants.USER_TABLE_FIELD_UPWD+ " varchar(32)," +Constants.USER_TABLE_FIELD_SEX +" varchar(5)  )";
        String billsql="  create  table  "
                +Constants.BILL_TABLE_NAME+"("+Constants.BILL_TABLE_FIELD_ID+" integer primary key autoincrement,"
                +Constants.BILL_TABLE_FIELD_UNAME+"  varchar(30),"+Constants.BILL_TABLE_FIELD_TYPE+" varchar(20),"
                +Constants.BILL_TABLE_FIELD_TYPEP_OSITION+"  integer,"
                +Constants.BILL_TABLE_FIELD_MONEY+" integer(255),"+Constants.BILL_TABLE_FIELD_DATE+"  date,"
                +Constants.BILL_TABLE_FIELD_REMARK+"  varchar(255)"+")";
        db.execSQL(billsql);
        db.execSQL(usersql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        /**
         * 数据库更新
         */
    }
}
