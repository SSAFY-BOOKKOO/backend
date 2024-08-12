package com.ssafy.bookkoo.curationservice.repository;

import com.ssafy.bookkoo.curationservice.entity.Curation;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurationRepository extends JpaRepository<Curation, Long> {

    List<Curation> findCurationsByWriterOrderByCreatedAtDesc(Long writer, Pageable pageable);

    Long countByWriter(Long writer);
}
