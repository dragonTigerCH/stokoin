package com.bsc.stokoin.user.domain;

import com.bsc.stokoin.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }

//    @Override
//    public void validate(Object target, Errors errors) {
//
//        UserTempSaveRequest userTempSaveRequest = (UserTempSaveRequest) target;
//
//        if (userRepository.existsByEmail(userTempSaveRequest.getEmail())){
//            errors.rejectValue("email", "invalid.email", "이미 존재하는 이메일입니다.");
//        }
//    }
}
