package com.bsc.stokoin.config.security.service;

import com.bsc.stokoin.config.security.dto.OAuthAttributes;
import com.bsc.stokoin.user.domain.User;
import com.bsc.stokoin.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CustomOauth2Service  {

    private final UserRepository userRepository;

//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest){
//        log.info("loadUser");
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 현재 로그인 진행 중인 서비스를 구분하는 코드
//        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
//                .getUserInfoEndpoint().getUserNameAttributeName(); // OAuth2 로그인 진행 시 키가 되는 필드값을 이야기함. Primary Key와 같은 의미
//
//        OAuthAttributes attributes = OAuthAttributes.of(registrationId,userNameAttributeName,oAuth2User.getAttributes());
//
//        User user = saveOrUpdate(attributes);
//
//        return UserPrincipal.of(user,attributes.getAttributes());
//
//    }

    private User saveOrUpdate(OAuthAttributes attributes) {

        Optional<User> userOptional = userRepository.findByEmail(attributes.getEmail());
        User user;
        if (userOptional.isPresent()){
            user = userOptional.get();
            user.updateBySocialProfile(attributes.getName(),attributes.getPicture());
        }
        else{
            user = userRepository.save(attributes.toUserEntity());
        }

        return user;
    }
}
