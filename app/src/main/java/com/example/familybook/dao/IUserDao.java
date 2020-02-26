package com.example.familybook.dao;

        import com.example.familybook.entity.User;

/**
 * 这是User数据库接口层
 */
public interface IUserDao {
    boolean isExist(String username);
    boolean Login(String username,String password);
    long Register(User user);
    User  Query(String username,String password);
    User Query(String username);
}
