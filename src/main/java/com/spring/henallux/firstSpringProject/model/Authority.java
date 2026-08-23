package com.spring.henallux.firstSpringProject.model;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {
    private Integer id;
    private String authority;
    private User user;

    public Authority() {}

    public Authority(Integer id, String authority, User user) {
        this.id = id;
        this.authority = authority;
        this.user = user;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
