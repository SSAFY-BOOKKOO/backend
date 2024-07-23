package com.ssafy.bookkoo.authservice.service.Impl;

import com.ssafy.bookkoo.authservice.exception.MemberNotFoundException;
import com.ssafy.bookkoo.authservice.repository.MemberRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    /**
     * memberId를 통해 Member 객체를 가져오며, UserDetails 상위 타입 객체로 반환
     *
     * @param memberId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        return memberRepository.findByMemberId(UUID.fromString(memberId)
                                                   .toString())
                               .orElseThrow(MemberNotFoundException::new);
    }
}
