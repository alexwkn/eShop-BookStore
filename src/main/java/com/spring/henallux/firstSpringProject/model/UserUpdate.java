package com.spring.henallux.firstSpringProject.model;

import javax.validation.constraints.*;

public class UserUpdate {

    @NotBlank
    @Size(min = 3, max = 10)
    @Pattern(regexp = "^[A-Za-z0-9._-]+$")
    private String username;

    @Size(max = 255)
    private String address;

    @Size(max = 20)
    @Pattern(regexp = "^$|^[+0-9 ]{6,20}$")
    private String phoneNumber;

    @Pattern(
            regexp = "^$|.{8,255}$",
            message = "Le mot de passe doit être vide ou contenir entre 8 et 255 caractères"
    )    private String password;

    private String confirmPassword;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
}
