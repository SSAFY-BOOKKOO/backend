package com.ssafy.bookkoo.curationservice.repository;


import com.ssafy.bookkoo.curationservice.entity.Curation;
import com.ssafy.bookkoo.curationservice.entity.CurationSend;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurationSendRepository extends JpaRepository<CurationSend, Long> {

    List<CurationSend> findCurationSendsByReceiverOrderByCreatedAtDesc(Long receiverId,
        Pageable pageable);

    Optional<CurationSend> findCurationSendsByCurationAndReceiver(Curation curation,
        Long receiver);

    List<CurationSend> findCurationSendsByIsStoredAndReceiverOrderByCreatedAtDesc(Boolean isStored,
        Long receiver, Pageable pageable);

    Long countByReceiver(Long receiver);

    Long countByReceiverAndIsStoredIsTrue(Long receiver);

    void deleteCurationSendByIsStoredFalseAndCreatedAtBefore(LocalDateTime time);

    List<CurationSend> findByCuration(Curation curation);

    Optional<CurationSend> findCurationSendById(Long id);
}
