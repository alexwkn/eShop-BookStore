package com.eshop.bookstore.dataAccess.dao;

import com.eshop.bookstore.dataAccess.entity.UserEntity;
import com.eshop.bookstore.dataAccess.repository.UserRepository;
import com.eshop.bookstore.dataAccess.util.ProviderConverter;
import com.eshop.bookstore.model.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserDAO implements UserDataAccess {

    private final UserRepository userRepository;
    private final ProviderConverter providerConverter;

    public UserDAO(UserRepository userRepository, ProviderConverter providerConverter) {
        this.userRepository = userRepository;
        this.providerConverter = providerConverter;
    }


    public User findByEmail(String email) {
        UserEntity entity = userRepository.findByEmail(email);
        return providerConverter.userEntityToUserModel(entity);
    }

    public void save(User user) {
        UserEntity userEntity = providerConverter.userModelToUserEntity(user);
        userRepository.save(userEntity);
    }
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
