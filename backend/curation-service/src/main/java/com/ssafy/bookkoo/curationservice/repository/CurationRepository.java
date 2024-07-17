package com.ssafy.bookkoo.curationservice.repository;

import com.ssafy.bookkoo.curationservice.entity.Curation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurationRepository extends JpaRepository<Curation, Integer> {

}
