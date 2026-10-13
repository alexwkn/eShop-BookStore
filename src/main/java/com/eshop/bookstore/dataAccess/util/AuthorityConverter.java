package com.eshop.bookstore.dataAccess.util;

import com.eshop.bookstore.dataAccess.entity.AuthorityEntity;
import com.eshop.bookstore.model.Authority;
import com.eshop.bookstore.dataAccess.entity.UserEntity;
import com.eshop.bookstore.model.Authority;
import com.eshop.bookstore.model.User;

import org.springframework.stereotype.Component;

@Component
public class AuthorityConverter {

    public Authority authorityEntityToAuthorityModel(AuthorityEntity authorityEntity, User user)
    {
        if (authorityEntity == null) {
            return null;
        }

        Authority authority = new Authority();

        authority.setId(authorityEntity.getId());
        authority.setAuthority(authorityEntity.getAuthority());

        authority.setUser(user);

        return authority;
    }

    public AuthorityEntity authorityModelToAuthorityEntity(Authority authority, UserEntity userEntity)
    {
        if (authority == null) {
            return null;
        }

        AuthorityEntity authorityEntity = new AuthorityEntity();

        authorityEntity.setId(authority.getId());
        authorityEntity.setAuthority(authority.getAuthority());

        authorityEntity.setUserEntity(userEntity);

        return authorityEntity;
    }
}
