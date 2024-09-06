package com.ssafy.bookkoo.memberservice.repository.custom;

import static com.ssafy.bookkoo.memberservice.entity.QMember.member;
import static com.ssafy.bookkoo.memberservice.entity.QMemberInfo.memberInfo;
import static com.ssafy.bookkoo.memberservice.entity.QMemberSetting.memberSetting;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bookkoo.memberservice.dto.response.QResponseRecipientDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseRecipientDto;
import java.util.List;

import com.ssafy.bookkoo.memberservice.entity.MemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberInfoCustomRepositoryImpl implements MemberInfoCustomRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * follwers에 속한 Member를 제외하고 랜덤으로 3명의 Member ID(Long)을 반환합니다.
     *
     * @param followers
     * @return
     */
    @Override
    public List<Long> findRandomMemberInfoIdByFollowers(List<Long> followers) {
        return queryFactory.select(memberInfo.id)
                           .from(memberInfo)
                           .where(memberInfo.id.notIn(followers))
                           .orderBy(Expressions.numberTemplate(Double.class, "function('RANDOM')")
                                               .asc())
                           .limit(3)
                           .fetch();
    }

    /**
     * 멤버, 멤버 정보, 멤버 세팅 조인을 통해 
     * 멤버 Long id
     * 이메일
     * 이메일 수신 여부를 반환
     * @param recipientIds
     * @return
     */
    @Override
    public List<ResponseRecipientDto> findByRecipientsInfoByIds(List<Long> recipientIds) {
        return queryFactory.select(
                               new QResponseRecipientDto(
                                   memberInfo.id,
                                   member.email,
                                   memberSetting.isLetterReceive
                               ))
                           .from(memberInfo)
                           .innerJoin(memberInfo.member, member)
                           .innerJoin(memberInfo.memberSetting, memberSetting)
                           .where(memberInfo.id.in(recipientIds))
                           .fetch();
    }

    /**
     * 닉네임 검색 시 자신을 제외한
     * 멤버 정보 10개 반환
     *
     * @param id
     * @param nickName
     * @return
     */
    @Override
    public List<MemberInfo> findTOP10ByNickNameContainsAndIdNe(Long id, String nickName) {
        return queryFactory.selectFrom(memberInfo)
                           .where(
                               memberInfo.nickName.contains(nickName),
                               memberInfo.id.ne(id)
                           )
                           .limit(10)
                           .fetch();
    }
}
