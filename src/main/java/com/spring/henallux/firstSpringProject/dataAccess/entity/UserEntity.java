package com.spring.henallux.firstSpringProject.dataAccess.entity;

import javax.persistence.*;

@Entity
@Table(name = "app_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false,unique = false ,length= 255)
    private String address;

    @Column(length = 255)
    private String authorities;

    @Column(name = "non_expired", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean nonExpired = true;

    @Column(name = "non_locked", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean nonLocked = true;

    @Column(name = "credentials_non_expired", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean credentialsNonExpired = true;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean enabled = true;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    public UserEntity() {
    }

    public UserEntity(Integer id,
                      String username,
                      String password,
                      String email,
                      String address,
                      String authorities,
                      boolean nonExpired,
                      boolean nonLocked,
                      boolean credentialsNonExpired,
                      boolean enabled,
                      String phoneNumber) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.authorities = authorities;
        this.nonExpired = nonExpired;
        this.nonLocked = nonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.phoneNumber = phoneNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public boolean getAccountNonExpired() {
        return nonExpired;
    }

    public void setAccountNonExpired(boolean nonExpired) {
        this.nonExpired = nonExpired;
    }

    public boolean getAccountNonLocked() {
        return nonLocked;
    }

    public void setAccountNonLocked(boolean nonLocked) {
        this.nonLocked = nonLocked;
    }

    public boolean getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

