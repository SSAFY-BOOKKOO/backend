package com.ssafy.bookkoo.curationservice.repository;


import com.ssafy.bookkoo.curationservice.entity.CurationSend;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurationSendRepository extends JpaRepository<CurationSend, Long> {

    List<CurationSend> getCurationSendByReceiver(Long receiverId);

    List<CurationSend> getCurationSendByCurationWriter(Long writerId);
}
