package com.ssafy.bookkoo.curationservice.service;

import com.ssafy.bookkoo.curationservice.dto.RequestCreateCurationDto;
import com.ssafy.bookkoo.curationservice.dto.ResponseCurationDetailDto;
import com.ssafy.bookkoo.curationservice.dto.ResponseCurationDto;
import com.ssafy.bookkoo.curationservice.entity.Curation;
import com.ssafy.bookkoo.curationservice.entity.CurationSend;
import com.ssafy.bookkoo.curationservice.exception.CurationNotFoundException;
import com.ssafy.bookkoo.curationservice.repository.CurationRepository;
import com.ssafy.bookkoo.curationservice.repository.CurationSendRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Curation Service
 *
 * @author dino9881
 */
@Service
@RequiredArgsConstructor
public class CurationServiceImpl implements CurationService {

    final CurationRepository curationRepository;
    final CurationSendRepository curationSendRepository;

    /**
     * Curation을 생성하고 전송하는 메서드 생성시 팔로워와 랜덤 멤버 3명에게 전송한다.
     *
     * @param writer            Curation을 작성한 작성자
     * @param createCurationDto Curation 생성 DTO 책 ISBN, Curation 제목, 내용
     */
    @Transactional
    @Override
    public void createCuration(Long writer, RequestCreateCurationDto createCurationDto) {
        Curation curation = Curation.builder()
                                    .writer(writer)
                                    .book(createCurationDto.bookId())
                                    .title(createCurationDto.title())
                                    .content(createCurationDto.content())
                                    .build();
        curationRepository.save(curation);
        //TODO 멤버 정보 받아오기 (돌면서 curation Send 생성)
    }

    /**
     * 큐레이션 상세정보를 받아온다.
     *
     * @param id CurationSend id
     * @return Curation 상세정보(String coverImgUrl, String curationTitle, String writer, String
     * content, String createdAt, String bookTitle, String author, String summary)
     */
    @Transactional
    @Override
    public ResponseCurationDetailDto getCurationDetail(Long id) {
        CurationSend curationSend = curationSendRepository.findById(id)
                                                          .orElseThrow(
                                                              () -> new CurationNotFoundException(
                                                                  id));
        //읽기 처리
        curationSend.read();
        //TODO MemberService 에게 member 정보 받아오기 (작성자 닉네임)
        //TODO BookService 에게 book 정보 받아오기 (책 커버 이미지, 제목, 작가, 줄거리)
        //TODO createdAt 넣기
        return ResponseCurationDetailDto.builder()
                                        .curationTitle(curationSend.getCuration()
                                                                   .getTitle())
                                        .content(curationSend.getCuration()
                                                             .getContent())
                                        .build();

    }

    /**
     * 내가 받은 큐레이션들을 가져온다.
     *
     * @param receiver 수신자 Id
     * @return ResponseCurationDto
     */
    @Override
    public List<ResponseCurationDto> getCurationList(Long receiver) {
        List<CurationSend> curationSendByReceiver = curationSendRepository.getCurationSendByReceiver(
            receiver);
        for (CurationSend curationSend : curationSendByReceiver) {
            //멤버 서비스에 닉네임 요청
            //책 서비스에 커버 이미지 요청
        }

        return null;
    }

    /**
     * CurationSend 를 저장한다.
     *
     * @param id
     */
    @Override
    public void storeCuration(Long id) {
        CurationSend curationSend = curationSendRepository.findById(id)
                                                          .orElseThrow(
                                                              () -> new CurationNotFoundException(
                                                                  id));
        curationSend.store();
    }

    /**
     * 큐레이션을 삭제한다.
     *
     * @param id CurationSend ID
     */
    @Override
    public void deleteCuration(Long id) {
        curationSendRepository.deleteById(id);
    }

    /**
     * 내가 작성한 큐레이션리스트를 가져온다.
     *
     * @param writer 작성자 ID
     * @return ResponseCurationDto
     */
    @Override
    public List<ResponseCurationDto> getSentCurations(Long writer) {
        List<CurationSend> curationSendByReceiver = curationSendRepository.getCurationSendByCurationWriter(
            writer);
        for (CurationSend curationSend : curationSendByReceiver) {
            //멤버 서비스에 닉네임 요청
            //책 서비스에 커버 이미지 요청
        }

        return null;
    }
}
