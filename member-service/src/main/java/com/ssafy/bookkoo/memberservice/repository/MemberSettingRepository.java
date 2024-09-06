package com.ssafy.bookkoo.memberservice.repository;

import com.ssafy.bookkoo.memberservice.entity.MemberSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberSettingRepository extends JpaRepository<MemberSetting, Long> {

}
