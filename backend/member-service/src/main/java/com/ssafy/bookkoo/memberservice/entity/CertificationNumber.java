package com.ssafy.bookkoo.memberservice.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

/**
 * 이메일 초기화에 사용되는 Redis Entity
 */

@Builder
@RedisHash(value = "cert_num")
public class CertificationNumber {

    @Id
    private String id;

    @Indexed
    private String certNum;

    @Indexed
    private String email;

    @TimeToLive
    private long ttl;

    protected CertificationNumber() {
    }

    @Builder
    public CertificationNumber(String id, String certNum, String email, long ttl) {
        this.id = id;
        this.certNum = certNum;
        this.email = email;
        this.ttl = ttl;
    }
}
