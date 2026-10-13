package com.eshop.bookstore.dataAccess.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="authorities")
public class AuthorityEntity implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="authority", nullable = false, length = 100)
    private String authority;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id", nullable= false)
    private UserEntity userEntity;

    public AuthorityEntity() {}

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }


}
