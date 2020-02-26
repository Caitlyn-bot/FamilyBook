package com.example.familybook.dao;

import com.example.familybook.entity.Bill;

import java.util.List;

/**
 * 这是Bill数据库接口层
 */
public interface IBillDao {

    long AddBill(Bill bill);
    List<Bill> listAllBill(String username);
    List<Bill> listConditionBill(String username,String type,String date);
    Bill QueryBill(int id);
    boolean DeleteBill(int id);
    boolean Update(int id,Bill bill);
}
