package com.eshop.bookstore.service;

import com.eshop.bookstore.dataAccess.dao.UserDataAccess;
import com.eshop.bookstore.dataAccess.entity.UserEntity;
import com.eshop.bookstore.dataAccess.repository.UserRepository;
import com.eshop.bookstore.dataAccess.util.ProviderConverter;
import com.eshop.bookstore.model.Authority;
import com.eshop.bookstore.model.User;
import com.eshop.bookstore.model.UserUpdate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class  UserService {

    private final UserDataAccess userDataAccess;
    private final UserRepository userRepository;
    private final ProviderConverter providerConverter;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserDataAccess userDataAccess, UserRepository userRepository, ProviderConverter providerConverter, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDataAccess = userDataAccess;
        this.providerConverter = providerConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerNewUser(User user) {

        user.setEmail(user.getEmail().trim().toLowerCase());

        if (user.getAddress() != null && user.getAddress().trim().isEmpty()) {
            user.setAddress(null);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        Authority userAuthority = new Authority();

        userAuthority.setAuthority("ROLE_USER");
        userAuthority.setUser(user);

        Set<Authority> authorities = new HashSet<>();
        authorities.add(userAuthority);

        user.setAuthorities(authorities);

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

        if (userUpdate.getAddress() != null && userUpdate.getAddress().trim().isEmpty()) {
            existingUser.setAddress(null);
        } else {
            existingUser.setAddress(userUpdate.getAddress());
        }

        existingUser.setPhoneNumber(userUpdate.getPhoneNumber());

        if (userUpdate.getPassword() != null && !userUpdate.getPassword().trim().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
        }
        UserEntity savedUser = userRepository.save(existingUser);
        return providerConverter.userEntityToUserModel(savedUser);
    }
}
