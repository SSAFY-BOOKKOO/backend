package com.ssafy.bookkoo.memberservice.repository.custom;

import java.util.List;

public interface MemberInfoCustomRepository {

    List<Long> findRandomMemberInfoIdByFollowers(List<Long> followers);

}