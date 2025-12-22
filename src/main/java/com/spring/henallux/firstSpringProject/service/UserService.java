package com.spring.henallux.firstSpringProject.service;

import com.spring.henallux.firstSpringProject.dataAccess.dao.UserDataAccess;
import com.spring.henallux.firstSpringProject.dataAccess.entity.UserEntity;
import com.spring.henallux.firstSpringProject.dataAccess.repository.UserRepository;
import com.spring.henallux.firstSpringProject.dataAccess.util.ProviderConverter;
import com.spring.henallux.firstSpringProject.model.User;
import com.spring.henallux.firstSpringProject.model.UserUpdate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class  UserService {

    private final UserDataAccess userDataAccess;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;
    private final ProviderConverter providerConverter;

    public UserService(UserDataAccess userDataAccess, UserRepository userRepository, ProviderConverter providerConverter) {
        this.userRepository = userRepository;
        this.userDataAccess = userDataAccess;
        this.providerConverter = providerConverter;
    }

    @Transactional
    public void registerNewUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthorities("ROLE_USER");
        userDataAccess.save(user);
    }

    public boolean emailExists(String email) {
        return userDataAccess.existsByEmail(email.trim().toLowerCase());
    }
    @Transactional
    public User updateUser(String email, UserUpdate userUpdate) {
        UserEntity existingUser = userRepository.findByEmail(email);
        if (existingUser == null) {
            throw new RuntimeException("Utilisateur non trouvé");
        }
        existingUser.setUsername(userUpdate.getUsername());
        existingUser.setAddress(userUpdate.getAddress());
        existingUser.setPhoneNumber(userUpdate.getPhoneNumber());

        if (userUpdate.getPassword() != null && !userUpdate.getPassword().trim().isEmpty()) {
            existingUser.setPassword(encoder.encode(userUpdate.getPassword()));
        }
        UserEntity userEntity = userRepository.save(existingUser);
        User user = providerConverter.userEntityToUserModel(userEntity);
        return user;
    }
}
