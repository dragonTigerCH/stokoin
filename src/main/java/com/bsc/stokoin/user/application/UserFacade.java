package com.bsc.stokoin.user.application;

import com.bsc.stokoin.user.domain.service.UserCommandUseCase;
import com.bsc.stokoin.user.presentation.dto.request.UserSimpleInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// 왜 facade 패턴을 사용하는가?
// 1. 서브시스템을 사용하기 쉽게 만들기 위해서

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserCommandUseCase userCommandUseCase;


    public void updateUserSimpleInfo(UserSimpleInfoRequest userSimpleInfoRequest, Long id) {
        userCommandUseCase.updateUserSimpleInfo(userSimpleInfoRequest,id);

    }
}
