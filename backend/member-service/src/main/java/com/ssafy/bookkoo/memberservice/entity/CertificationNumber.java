package com.ssafy.bookkoo.memberservice.entity;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

/**
 * 이메일 초기화에 사용되는 Redis Entity
 */

@Builder
@Getter
@RedisHash(value = "cert_num")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CertificationNumber {

    @Id
    private String id;

    @Indexed
    private String certNum;

    @Indexed
    private String email;

    @TimeToLive
    private long ttl;

    @Builder
    public CertificationNumber(String id, String certNum, String email, long ttl) {
        this.id = id;
        this.certNum = certNum;
        this.email = email;
        this.ttl = ttl;
    }
}
