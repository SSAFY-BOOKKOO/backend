package com.ssafy.bookkoo.memberservice.repository;

import com.ssafy.bookkoo.memberservice.entity.FollowShip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowShipRepository extends JpaRepository<FollowShip, Long>, FollowShipCustomRepository {



}
