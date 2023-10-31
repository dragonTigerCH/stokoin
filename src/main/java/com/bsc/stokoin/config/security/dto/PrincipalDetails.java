package com.bsc.stokoin.config.security.dto;

import com.bsc.stokoin.user.domain.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Getter
public class PrincipalDetails implements UserDetails {

    private User user;

    private PrincipalDetails(User user) {
        this.user = user;
    }

    public static PrincipalDetails from(User user) {
        return new PrincipalDetails(user);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority(user.getRoleKey()));
        return auth;
    }

    @Override
    public String getUsername() {
        return user.getProviderId();
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
