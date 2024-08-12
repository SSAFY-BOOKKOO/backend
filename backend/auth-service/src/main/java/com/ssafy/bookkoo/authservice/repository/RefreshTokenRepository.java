package com.ssafy.bookkoo.authservice.repository;

import com.ssafy.bookkoo.authservice.entity.RefreshToken;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

    Optional<RefreshToken> findByMemberId(String memberId);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

}
