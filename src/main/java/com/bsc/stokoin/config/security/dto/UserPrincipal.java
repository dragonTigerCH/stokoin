package com.bsc.stokoin.config.security.dto;

import com.bsc.stokoin.user.domain.User;
import com.bsc.stokoin.user.domain.enums.AuthProvider;
import lombok.Getter;

import java.util.Map;

@Getter
public class UserPrincipal  {
    private User user;
    private Map<String, Object> attributes;

    private UserPrincipal(User member) {
        this.user = member;
    }

    public static UserPrincipal from(User user) {
        return new UserPrincipal(user);
    }

    public static UserPrincipal of(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.from(user);
        userPrincipal.attributes = attributes;
        return userPrincipal;
    }

//    @Override
//    public Map<String, Object> getAttributes() {
//        return attributes;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.singletonList(new SimpleGrantedAuthority(user.getRoleKey()));
//    }
//
//    @Override
//    public String getName() {
//        return user.getName();
//    }


    public String getUsername() {
        return user.getEmail();
    }


    public AuthProvider getAuthProvider(){
        return user.getAuthProvider();
    }




}
