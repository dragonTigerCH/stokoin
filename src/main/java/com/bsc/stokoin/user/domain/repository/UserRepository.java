package com.bsc.stokoin.user.domain.repository;

import com.bsc.stokoin.user.domain.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long userId);

    boolean existsByEmail(String email);

    Optional<User> findByProviderId(String username);
}
