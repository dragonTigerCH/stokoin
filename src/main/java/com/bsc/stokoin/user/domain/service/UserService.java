package com.bsc.stokoin.user.domain.service;

import com.bsc.stokoin.authentication.domain.service.TokenProvider;
import com.bsc.stokoin.common.exception.user.UserNotFoundException;
import com.bsc.stokoin.user.domain.User;
import com.bsc.stokoin.user.domain.repository.UserRepository;
import com.bsc.stokoin.user.presentation.dto.request.UserSimpleInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserCommandUseCase{

    private final UserRepository userRepository;

    private final TokenProvider tokenProvider;


//    @Override
//    public AuthResponseDto saveTempInfo(UserTempSaveRequest userTempSaveRequest) {
//        User savedUser = userRepository.save(userTempSaveRequest.toEntity());
//        String tempAccessToken = tokenProvider.createTempAccessToken(savedUser.getEmail());
//        String tempRefreshToken = tokenProvider.createTempRefreshToken(savedUser.getEmail());
//        return AuthResponseDto.of(tempAccessToken, tempRefreshToken);
//    }


    @Override
    public void updateUserSimpleInfo(UserSimpleInfoRequest userSimpleInfoRequest, Long id) {
        User currentUserOrThrow = getCurrentUserOrThrow(id);
        currentUserOrThrow.updateSimpleInfo(userSimpleInfoRequest);
    }
    private User getCurrentUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());
    }
}
