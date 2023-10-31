package com.bsc.stokoin.authentication.infrastructure.repository;

import com.bsc.stokoin.authentication.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken,String> {
}
