package com.bsc.stokoin.config.security.service;

import com.bsc.stokoin.common.exception.user.UserNotFoundException;
import com.bsc.stokoin.config.security.dto.PrincipalDetails;
import com.bsc.stokoin.user.domain.User;
import com.bsc.stokoin.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipleDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User currentUserOrThrow = getCurrentUserOrThrow(username);
        return PrincipalDetails.from(currentUserOrThrow);
    }

    private User getCurrentUserOrThrow(String username) {
        return userRepository.findByProviderId(username) // user.providerId = username
                .orElseThrow(() -> new UserNotFoundException());
    }
}
