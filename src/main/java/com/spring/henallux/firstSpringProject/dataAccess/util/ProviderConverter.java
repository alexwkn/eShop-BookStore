package com.spring.henallux.firstSpringProject.dataAccess.util;

import com.spring.henallux.firstSpringProject.dataAccess.entity.AuthorityEntity;
import com.spring.henallux.firstSpringProject.dataAccess.entity.UserEntity;
import com.spring.henallux.firstSpringProject.model.Authority;
import com.spring.henallux.firstSpringProject.model.User;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ProviderConverter {

    private final AuthorityConverter authorityConverter;

    public ProviderConverter(AuthorityConverter authorityConverter) {
        this.authorityConverter = authorityConverter;
    }

    public UserEntity userModelToUserEntity(User user) {

        if (user == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId(user.getId());
        userEntity.setUsername(user.getUsername());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setPassword(user.getPassword());
        userEntity.setEmail(user.getEmail());
        userEntity.setAddress(user.getAddress());
        userEntity.setEnabled(user.isEnabled());
        userEntity.setPhoneNumber(user.getPhoneNumber());
        Set<AuthorityEntity> authorityEntities = new HashSet<>();

        if (user.getAuthorities() != null) {
            for (Authority authority : user.getUserAuthorities()) {
                AuthorityEntity authorityEntity = authorityConverter.authorityModelToAuthorityEntity(authority, userEntity);
                authorityEntities.add(authorityEntity);

            }
        }
        userEntity.setAuthorities(authorityEntities);
        return userEntity;
    }

    public User userEntityToUserModel(UserEntity userEntity) {

        if (userEntity == null) {
            return null;
        }

        User user = new User();

        user.setId(userEntity.getId());
        user.setUsername(userEntity.getUsername());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setPassword(userEntity.getPassword());
        user.setEmail(userEntity.getEmail());
        user.setAddress(userEntity.getAddress());
        user.setEnabled(userEntity.getEnabled());
        user.setPhoneNumber(userEntity.getPhoneNumber());

        Set<Authority> authorities = new HashSet<>();

        if (userEntity.getAuthorities() != null) {
            for (AuthorityEntity authorityEntity : userEntity.getAuthorities()) {
                Authority authority = authorityConverter.authorityEntityToAuthorityModel(authorityEntity, user);
                authorities.add(authority);
            }
        }
        user.setAuthorities(authorities);
        return user;
    }
}
