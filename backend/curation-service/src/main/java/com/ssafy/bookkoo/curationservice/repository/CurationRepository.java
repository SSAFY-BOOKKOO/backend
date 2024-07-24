package com.ssafy.bookkoo.curationservice.repository;

import com.ssafy.bookkoo.curationservice.entity.Curation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurationRepository extends JpaRepository<Curation, Long> {

    List<Curation> findCurationsByWriter(Long writer);
}
