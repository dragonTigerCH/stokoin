package com.bsc.stokoin.user.domain.service;

import com.bsc.stokoin.user.presentation.dto.request.UserSimpleInfoRequest;

public interface UserCommandUseCase {

    void updateUserSimpleInfo(UserSimpleInfoRequest userSimpleInfoRequest, Long id);

}
