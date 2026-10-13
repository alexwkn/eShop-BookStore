package com.eshop.bookstore.dataAccess.dao;

import com.eshop.bookstore.model.User;

public interface UserDataAccess {
    void save(User user);
    User findByEmail(String email);
    boolean existsByEmail(String email);

}
