package com.bsc.stokoin.authentication.infrastructure.repository;

import com.bsc.stokoin.authentication.domain.LogoutRefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface LogoutRefreshTokenRedisRepository extends CrudRepository<LogoutRefreshToken,String> {
}
