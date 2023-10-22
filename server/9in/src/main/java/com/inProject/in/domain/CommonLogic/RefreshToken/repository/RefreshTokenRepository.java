package com.inProject.in.domain.CommonLogic.RefreshToken.repository;

import com.inProject.in.domain.CommonLogic.RefreshToken.entity.RefreshToken;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

import java.util.Optional;

public interface RefreshTokenRepository {
    void save(RefreshToken refreshToken);
    Optional<RefreshToken> findByUsername(String username);

}
