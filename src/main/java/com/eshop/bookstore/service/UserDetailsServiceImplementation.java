package com.eshop.bookstore.service;

import com.eshop.bookstore.dataAccess.dao.UserDAO;
import com.eshop.bookstore.dataAccess.dao.UserDataAccess;
import com.eshop.bookstore.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    private UserDataAccess userDAO;

    @Autowired
    public UserDetailsServiceImplementation(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userDAO.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException(email + "not found");
        }

        return user;

    }
}
