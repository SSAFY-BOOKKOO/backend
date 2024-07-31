package com.ssafy.bookkoo.curationservice.repository;


import com.ssafy.bookkoo.curationservice.entity.Curation;
import com.ssafy.bookkoo.curationservice.entity.CurationSend;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurationSendRepository extends JpaRepository<CurationSend, Long> {

    List<CurationSend> findCurationSendsByReceiver(Long receiverId);

    Optional<CurationSend> findCurationSendsByCurationAndReceiver(Curation curation,
        Long receiver);

    List<CurationSend> findCurationSendsByIsStoredAndReceiverOrderByCreatedAtDesc(Boolean isStored,
        Long receiver, Pageable pageable);

    List<CurationSend> findByCuration(Curation curation);

    Optional<CurationSend> findCurationSendById(Long id);
}
