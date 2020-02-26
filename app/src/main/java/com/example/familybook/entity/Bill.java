package com.example.familybook.entity;

import java.sql.Date;

/**
 * 账单实体类
 */
public class Bill {
    private int  _id;
    private String username;
    private String type;
    private int type_position;
    private String money;
    private String date;
    private String remark;

    public Bill() {
    }


    public Bill(int _id, String username, String type, int type_position, String money, String date, String remark) {
        this._id = _id;
        this.username = username;
        this.type = type;
        this.type_position = type_position;
        this.money = money;
        this.date = date;
        this.remark = remark;
    }

    public Bill(String username, String type, int type_position, String money, String date, String remark) {
        this.username = username;
        this.type = type;
        this.type_position = type_position;
        this.money = money;
        this.date = date;
        this.remark = remark;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTypeposition() {
        return type_position;
    }

    public void setTypeposition(int typeposition) {
        this.type_position = typeposition;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "_id=" + _id +
                ", username='" + username + '\'' +
                ", type='" + type + '\'' +
                ", type_position=" + type_position +
                ", money='" + money + '\'' +
                ", date='" + date + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
