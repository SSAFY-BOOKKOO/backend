package com.ssafy.bookkoo.curationservice.repository;


import com.ssafy.bookkoo.curationservice.entity.CurationSend;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurationSendRepository extends JpaRepository<CurationSend, Long> {

    List<CurationSend> findCurationSendsByReceiver(Long receiverId);

    List<CurationSend> findCurationSendsByCurationWriter(Long writerId);
}
