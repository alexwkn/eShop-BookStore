package com.spring.henallux.firstSpringProject.dataAccess.dao;

import com.spring.henallux.firstSpringProject.model.User;

public interface UserDataAccess {
    void save(User user);
    User findByEmail(String email);
    boolean existsByEmail(String email);

}
