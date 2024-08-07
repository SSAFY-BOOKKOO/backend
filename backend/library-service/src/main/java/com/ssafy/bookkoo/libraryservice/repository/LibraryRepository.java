package com.ssafy.bookkoo.libraryservice.repository;

import com.ssafy.bookkoo.libraryservice.entity.Library;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LibraryRepository extends JpaRepository<Library, Long> {

    // libraryOrder 로 sorting
    @Transactional(readOnly = true)
    @Query("SELECT l FROM Library l WHERE l.memberId = :memberId ORDER BY l.libraryOrder ASC")
    List<Library> findByMemberId(@Param("memberId") Long memberId);

    int countByMemberId(Long memberId);

    void deleteByMemberId(Long memberId);
}
