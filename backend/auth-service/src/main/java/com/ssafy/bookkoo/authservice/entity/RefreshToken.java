package com.ssafy.bookkoo.authservice.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash("token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    private String id;

    @Indexed
    private String refreshToken;

    @Indexed
    private String memberId;

    @TimeToLive
    private long ttl;

    @Builder
    public RefreshToken(String id, String refreshToken, String memberId, long ttl) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.memberId = memberId;
        this.ttl = ttl;
    }
}
