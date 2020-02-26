package com.example.familybook.entity;

/**
 * 用户账号实体类
 */
public class User {
    private int _id;
    private String username;
    private String password;
    private String sex;

    public User( ) {

    }

    public User( String username, String password, String sex) {
        this.username = username;
        this.password = password;
        this.sex = sex;
    }


    public User(int id, String username, String password, String sex) {
        _id = id;
        this.username = username;
        this.password = password;
        this.sex = sex;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "_id=" + _id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}