package com.bsc.stokoin.authentication.infrastructure.repository;

import com.bsc.stokoin.authentication.domain.LogoutAccessToken;
import org.springframework.data.repository.CrudRepository;

public interface LogoutAccessTokenRedisRepository extends CrudRepository<LogoutAccessToken,String> {
}
