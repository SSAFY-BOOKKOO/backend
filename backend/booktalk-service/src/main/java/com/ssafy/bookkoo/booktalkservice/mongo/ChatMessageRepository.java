package com.ssafy.bookkoo.booktalkservice.mongo;

import com.ssafy.bookkoo.booktalkservice.document.ChatMessage;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    /**
     * 해당 독서록의 채팅을 기준시간 이전 10개 반환한다.
     *
     * @param bookTalkId 독서록 번호
     * @param time       기준 시간
     * @return 최근 채팅 기록
     */
    List<ChatMessage> findTop10ByBookTalkIdAndCreatedAtBeforeOrderByCreatedAtDesc(Long bookTalkId,
        LocalDateTime time);


    /**
     * 각 BookTalk ID 별로 가장 최근 만들어진 채팅이 특정 시간 이전인 경우 해당 BookTalk ID 리스트 반환
     *
     * @param time 기준 시간
     * @return 특정 시간 이전의 가장 최근 채팅을 가진 BookTalk ID 리스트
     */
    @Aggregation(pipeline = {
        "{ '$match': { 'createdAt': { '$lt': ?0 } } }",
        "{ '$sort': { 'bookTalkId': 1, 'createdAt': -1 } }",
        "{ '$group': { '_id': '$bookTalkId', 'latestChat': { '$first': '$$ROOT' } } }",
        "{ '$match': { 'latestChat.createdAt': { '$lt': ?0 } } }",
        "{ '$project': { 'bookTalkId': '$_id' } }"
    })
    List<Long> findBookTalkIdsWithLatestChatBefore(LocalDateTime time);

    void deleteAllByBookTalkIdIn(List<Long> bookTalkIds);
}

