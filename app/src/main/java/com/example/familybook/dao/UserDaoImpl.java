package com.example.familybook.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.familybook.database.FamilyBookDatabaseHelper;
import com.example.familybook.entity.User;
import com.example.familybook.utils.Constants;

public class UserDaoImpl implements IUserDao {
    private  final FamilyBookDatabaseHelper mFamilyBookDatabaseHelper;

    public UserDaoImpl(Context context){
        mFamilyBookDatabaseHelper =new FamilyBookDatabaseHelper(context);
    }



    @Override
    public boolean isExist(String username) {

        return Query(username)==null? false:true;
    }

    /**
     * 该方法用于登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public boolean Login(String username,String password) {
        return Query(username,password)==null? false:true;
    }

    /**
     * 该方法用于注册账户
     * @param user
     * @return
     */
    @Override
    public long Register(User user) {
        SQLiteDatabase db = mFamilyBookDatabaseHelper.getWritableDatabase();
        long result =-1;
//        db.beginTransaction();
        try {
            ContentValues values  =new ContentValues();
            values.put(Constants.USER_TABLE_FIELD_UNAME,user.getUsername());
            values.put(Constants.USER_TABLE_FIELD_UPWD,user.getPassword());
            values.put(Constants.USER_TABLE_FIELD_SEX,user.getSex());
            result=db.insert(Constants.USER_TABLE_NAME,null,values);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.close();
//            db.endTransaction();
        }

        return result;
    }

    /**
     * 该方法是通过检查用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    @Override
    public User Query(String username, String password) {
        User user= null;
        SQLiteDatabase db = mFamilyBookDatabaseHelper.getWritableDatabase();

            /**
             * 如果数据库不为空，进行查询
             */

//            db.beginTransaction();
            try {

                String sql = "select * from " + Constants.USER_TABLE_NAME + " where " + Constants.USER_TABLE_FIELD_UNAME + "=? and " + Constants.USER_TABLE_FIELD_UPWD + "=?";
                String[] str = new String[]{username, password};
                Cursor cursor = db.rawQuery(sql, str);
                /**
                 * 下面在库中进行具体查询,并将
                 */
                if (cursor.moveToNext()) {
                    /**
                     * 将查到的数据逐一封装到user对象中
                     */

                    user = new User();
                    //set id
                    int userID = cursor.getInt(cursor.getColumnIndex(Constants.USER_TABLE_FIELD_ID));
                    user.set_id(userID);
                    //set username
                    String uname = cursor.getString(cursor.getColumnIndex(Constants.USER_TABLE_FIELD_UNAME));
                    user.setUsername(uname);
                    //set password
                    String upwd = cursor.getString(cursor.getColumnIndex(Constants.USER_TABLE_FIELD_UPWD));
                    user.setPassword(upwd);
                    //set sex
                    String usex = cursor.getString(cursor.getColumnIndex(Constants.USER_TABLE_FIELD_SEX));
                    user.setSex(usex);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.close();
//                db.endTransaction();
            }

        return user;
    }

    /**
     * 该方法是通过检查用户名来查找用户
     * @param username
     * @return
     */
    @Override
    public User Query(String username) {
        User user= null;
        SQLiteDatabase db = mFamilyBookDatabaseHelper.getWritableDatabase();
//        db.beginTransaction();
        try {

            String sql ="select * from "+Constants.USER_TABLE_NAME+" where "+Constants.USER_TABLE_FIELD_UNAME+"=? ";
            String[] str  =new  String[]{username};
            Cursor cursor =db.rawQuery(sql,str);
            /**
             * 下面在库中进行具体查询,并将
             */
            if(cursor.moveToNext()){
                /**
                 * 将查到的数据逐一封装到user对象中
                 */

                user  =new User();
                //set id
                int userID = cursor.getInt(cursor.getColumnIndex(Constants.USER_TABLE_FIELD_ID));
                user.set_id(userID);
                //set username
                String uname=cursor.getString(cursor.getColumnIndex(Constants.USER_TABLE_FIELD_UNAME));
                user.setUsername(uname);
                //set password
                String upwd=cursor.getString(cursor.getColumnIndex(Constants.USER_TABLE_FIELD_UPWD));
                user.setPassword(upwd);
                //set sex
                String usex=cursor.getString(cursor.getColumnIndex(Constants.USER_TABLE_FIELD_SEX));
                user.setSex(usex);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.close();
//            db.endTransaction();
        }

        return user;
    }
}