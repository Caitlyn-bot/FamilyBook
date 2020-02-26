package com.example.familybook.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.familybook.database.FamilyBookDatabaseHelper;
import com.example.familybook.entity.Bill;
import com.example.familybook.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class BillDaoImpl implements IBillDao {
    private  final FamilyBookDatabaseHelper mBillDatabaseHelper;
    private IBillDao mIBillDao;

    public BillDaoImpl(Context context){
        mBillDatabaseHelper=new FamilyBookDatabaseHelper(context);
    }

    /**
     * 添加账目
     * @param bill
     * @return
     */
    @Override
    public long AddBill(Bill bill) {
        SQLiteDatabase db =mBillDatabaseHelper.getWritableDatabase();
        long result =-1;
        try {
            ContentValues values  =new ContentValues();
            values.put(Constants.BILL_TABLE_FIELD_UNAME,bill.getUsername());
            values.put(Constants.BILL_TABLE_FIELD_TYPE,bill.getType());
            values.put(Constants.BILL_TABLE_FIELD_TYPEP_OSITION,bill.getTypeposition());
            values.put(Constants.BILL_TABLE_FIELD_MONEY,bill.getMoney());
            values.put(Constants.BILL_TABLE_FIELD_DATE,bill.getDate());
            values.put(Constants.BILL_TABLE_FIELD_REMARK,bill.getRemark());
            result=db.insert(Constants.BILL_TABLE_NAME,null,values);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }

        return result;
    }

    @Override
    public List<Bill> listAllBill(String username) {
        List<Bill> bills= new ArrayList<>();
        Bill bill=null;
        SQLiteDatabase db = mBillDatabaseHelper.getWritableDatabase();

        try {

            String sql ="select * from "+Constants.BILL_TABLE_NAME+" where "+Constants.BILL_TABLE_FIELD_UNAME+"=? ";
            String[] str  =new  String[]{username};
            Cursor cursor =db.rawQuery(sql,str);
            /**
             * 下面在库中进行具体查询,并将
             */
            while (cursor.moveToNext()){
                /**
                 * 将查到的数据逐一获取
                 */


                //set id
                int billID = cursor.getInt(cursor.getColumnIndex(Constants.BILL_TABLE_FIELD_ID));
                //set type
                String type=cursor.getString(cursor.getColumnIndex(Constants.BILL_TABLE_FIELD_TYPE));
                //set type_position
                int type_position=cursor.getInt(cursor.getColumnIndex(Constants.BILL_TABLE_FIELD_TYPEP_OSITION));
                //set money
                String money=cursor.getString(cursor.getColumnIndex(Constants.BILL_TABLE_FIELD_MONEY));
                //set date
                String date=cursor.getString(cursor.getColumnIndex(Constants.BILL_TABLE_FIELD_DATE));
                //set remark
                String remark=cursor.getString(cursor.getColumnIndex(Constants.BILL_TABLE_FIELD_REMARK));

                /**
                 * 将获取的数据封装到bill中
                 */
                bill=new Bill(billID,username,type,type_position,money,date,remark);
                /**
                 * 将封装好的数据bill封装到bills中
                 */
                bills.add(bill);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.close();

        }
        return bills;
    }

    /**
     * 按需求查看账目
     * @param username
     * @param type
     * @param date
     * @return
     */
    @Override
    public List<Bill> listConditionBill(String username, String type, String date) {
        List<Bill> bills= new ArrayList<>();
        Bill bill=null;
        SQLiteDatabase db = mBillDatabaseHelper.getWritableDatabase();

        try {

            String sql ="select * from "+Constants.BILL_TABLE_NAME+" where "
                    +Constants.BILL_TABLE_FIELD_UNAME+"=? and "
                    +Constants.BILL_TABLE_FIELD_TYPE+"=?and "
                    +Constants.BILL_TABLE_FIELD_DATE+"=?";
            String[] str  =new  String[]{username,type,date};
            Cursor cursor =db.rawQuery(sql,str);
            /**
             * 下面在库中进行具体查询,并将
             */
            while (cursor.moveToNext()){
                /**
                 * 将查到的数据逐一获取
                 */


                //set id
                int billID = cursor.getInt(cursor.getColumnIndex(Constants.BILL_TABLE_FIELD_ID));
                //set type_position
                int type_position=cursor.getInt(cursor.getColumnIndex(Constants.BILL_TABLE_FIELD_TYPEP_OSITION));
                //set money
                String money=cursor.getString(cursor.getColumnIndex(Constants.BILL_TABLE_FIELD_MONEY));

                //set remark
                String remark=cursor.getString(cursor.getColumnIndex(Constants.BILL_TABLE_FIELD_REMARK));

                /**
                 * 将获取的数据封装到bill中
                 */
                bill=new Bill(billID,username,type,type_position,money,date,remark);
                /**
                 * 将封装好的数据bill封装到bills中
                 */
                bills.add(bill);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.close();

        }
        return bills;
    }

    @Override
    public Bill QueryBill(int id) {
        Bill bill=null;

        SQLiteDatabase db = mBillDatabaseHelper.getWritableDatabase();

        try {

            String sql ="select * from "+Constants.BILL_TABLE_NAME+" where "+Constants.BILL_TABLE_FIELD_ID+"=? ";
            String[] str  =new  String[]{String.valueOf(id)};
            Cursor cursor=db.rawQuery(sql,str);
            /**
             * 下面在库中进行具体查询,并将
             */
            if(cursor.moveToNext()){
                /**
                 * 将查到的数据逐一获取
                 */


                //set username
                String username=cursor.getString(cursor.getColumnIndex(Constants.BILL_TABLE_FIELD_UNAME));
                //set type
                String type=cursor.getString(cursor.getColumnIndex(Constants.BILL_TABLE_FIELD_TYPE));
                //set type_position
                int type_position=cursor.getInt(cursor.getColumnIndex(Constants.BILL_TABLE_FIELD_TYPEP_OSITION));
                //set money
                String money=cursor.getString(cursor.getColumnIndex(Constants.BILL_TABLE_FIELD_MONEY));
                //set date
                String date=cursor.getString(cursor.getColumnIndex(Constants.BILL_TABLE_FIELD_DATE));
                //set remark
                String remark=cursor.getString(cursor.getColumnIndex(Constants.BILL_TABLE_FIELD_REMARK));

                /**
                 * 将获取的数据封装到bill中
                 */
                bill=new Bill(id,username,type,type_position,money,date,remark);

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.close();

        }
        return bill;
    }

    /**
     * 对Bill表单进行删除
     * @param id
     * @return
     */
    @Override
    public boolean DeleteBill(int id) {
        boolean flag=false;

        SQLiteDatabase db = mBillDatabaseHelper.getWritableDatabase();


            String sql ="delete  from "+Constants.BILL_TABLE_NAME+" where "+Constants.BILL_TABLE_FIELD_ID+"=? ";
            Object[] str  =new  Object[]{id};
            db.execSQL(sql,str);
            db.close();
        /**
         * 在删除结束后，查找该id对应的Bill是否存在，来判断删除是否成功
         */
        flag=true;

        return flag;
    }

    /**
     * 对Bill表单进行修改
     * @param id
     * @param bill
     * @return
     */
    @Override
    public boolean Update(int id,Bill bill) {
        boolean flag=false;

        SQLiteDatabase db = mBillDatabaseHelper.getWritableDatabase();


        String sql ="update "+Constants.BILL_TABLE_NAME
                +" set "+Constants.BILL_TABLE_FIELD_UNAME+"=? ,"
                +Constants.BILL_TABLE_FIELD_TYPE+"= ?,"
                +Constants.BILL_TABLE_FIELD_TYPEP_OSITION+"=?,"
                +Constants.BILL_TABLE_FIELD_MONEY+"=?,"
                +Constants.BILL_TABLE_FIELD_DATE+"=?,"
                +Constants.BILL_TABLE_FIELD_REMARK+"=?"
                +" where "+Constants.BILL_TABLE_FIELD_ID+"=? ";
        Object[] obj  =new  Object[]{bill.getUsername(),bill.getType(),bill.getTypeposition(),bill.getMoney(),bill.getDate(),bill.getRemark(),id};
        db.execSQL(sql,obj);
        db.close();
        /**
         * 在修改结束后，查找该id对应的Bill是否相同，来判断修改是否成功
         */
            flag=true;

        return flag;
    }
}
