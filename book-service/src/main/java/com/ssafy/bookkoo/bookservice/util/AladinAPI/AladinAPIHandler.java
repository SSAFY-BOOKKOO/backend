package com.ssafy.bookkoo.bookservice.util.AladinAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.bookkoo.bookservice.client.LibraryServiceClient;
import com.ssafy.bookkoo.bookservice.dto.aladin.AladinAPISearchParams;
import com.ssafy.bookkoo.bookservice.dto.aladin.AladinBookItem;
import com.ssafy.bookkoo.bookservice.dto.aladin.ResponseAladinAPI;
import com.ssafy.bookkoo.bookservice.dto.aladin.ResponseAladinDetail;
import com.ssafy.bookkoo.bookservice.dto.aladin.ResponseAladinSearchDetail;
import com.ssafy.bookkoo.bookservice.dto.aladin.ResponseOriginAladinAPI;
import com.ssafy.bookkoo.bookservice.dto.aladin.ResponseOriginAladinDetail;
import com.ssafy.bookkoo.bookservice.dto.book.CheckExistBookByIsbnDto;
import com.ssafy.bookkoo.bookservice.exception.AladdinAPIException;
import com.ssafy.bookkoo.bookservice.mapper.BookMapper;
import com.ssafy.bookkoo.bookservice.repository.book.BookRepository;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AladinAPIHandler {

    final private AladinCategoryService aladinCategoryService;

    final private BookMapper bookMapper;
    private static String apiKey;
    private final BookRepository bookRepository;

    private final LibraryServiceClient libraryServiceClient;

    @Value("ttbwintiger981754003")
    public void setApiKey(String apiKey) {
        AladinAPIHandler.apiKey = apiKey;
    }

    private static final String BASE_URL = "http://www.aladin.co.kr/ttb/api/";

    /**
     * 알라딘 API에서 책 데이터를 검색해 가져오는 메서드
     *
     * @param params : 검색어(AladinAPISearchParams)
     * @return JSONObject : API 응답 데이터
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     */
    public ResponseAladinAPI searchBooks(Long memberId, AladinAPISearchParams params)
        throws IOException, InterruptedException, URISyntaxException {
        URI uri = new URIBuilder(BASE_URL + "itemSearch.aspx")
            .addParameter("ttbkey", apiKey)
            .addParameter("Query", params.getQuery())
            .addParameter("QueryType", String.valueOf(params.getQueryType()))
            .addParameter("MaxResults", String.valueOf(params.getMaxResult()))
            .addParameter("start", String.valueOf(params.getStart()))
            .addParameter("SearchTarget", "Book")
            .addParameter("output", "JS")
            .addParameter("Version", "20131101")
            .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(uri)
                                         .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseOriginAladinAPI originResponse = objectMapper.readValue(response.body(),
                ResponseOriginAladinAPI.class);
            ResponseAladinAPI apiResponse = bookMapper.toResponseAladinAPI(originResponse);

            // null 체크 추가
            if (apiResponse.getItem() == null) {
                throw new AladdinAPIException();
            }

            // coverImgUrl 값 변환(기본에서 큰 사이즈로)
            apiResponse.getItem()
                       .forEach(item -> item.setCoverImgUrl(
                           replaceCoverImgUrlCoverSum(item.getCoverImgUrl(), "cover200")));

            // 카테고리 매핑 적용
            aladinCategoryService.processApiResponse(apiResponse);

            // isbn 리스트 생성
            List<String> isbnList = apiResponse.getItem()
                                               .stream()
                                               .map(AladinBookItem::getIsbn)
                                               .toList();
            // ISBN 리스트로 내부 DB에서 책 데이터 확인
            List<CheckExistBookByIsbnDto> booksInDb = bookRepository.checkExistBookByIsbn(
                isbnList);

            // ISBN과 책 ID 맵핑
            Map<String, Long> isbnToBookIdMap = booksInDb.stream()
                                                         .collect(Collectors.toMap(
                                                             CheckExistBookByIsbnDto::isbn,
                                                             CheckExistBookByIsbnDto::id));

            List<Long> bookIds = isbnToBookIdMap.values()
                                                .stream()
                                                .toList();

            // 서재 서비스에서 여러 bookId와 memberId로 한 번에 조회
            Map<Long, Boolean> booksInLibrary = libraryServiceClient.areBooksInLibrary(memberId,
                bookIds);

            // 알라딘 검색 결과와 서재에 있는 책 비교하여 등록 여부 설정
            apiResponse.getItem()
                       .forEach(item -> {
                           Long bookId = isbnToBookIdMap.get(item.getIsbn());
                           if (bookId != null) {
                               item.setInLibrary(booksInLibrary.getOrDefault(bookId, false));
                           } else {
                               item.setInLibrary(false);
                           }
                       });

            return apiResponse;
        } else {
            throw new IOException("Failed to fetch data from Aladin API: " + response.body());
        }
    }

    public ResponseAladinSearchDetail searchBookDetail(String isbn)
        throws IOException, InterruptedException, URISyntaxException {
        URI uri = new URIBuilder(BASE_URL + "itemLookUp.aspx")
            .addParameter("ttbkey", apiKey)
            .addParameter("itemIdType", "ISBN")
            .addParameter("itemId", isbn)
            .addParameter("output", "JS")
            .addParameter("OptResult", "packing")
            .addParameter("Version", "20131101")
            .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(uri)
                                         .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            // response의 item 부분만 빼ㅐ네서 그거의 첫번쨰 아이템을 ResponseAladinSearchDetial로 변환
            ResponseOriginAladinDetail originResponse = objectMapper.readValue(response.body(),
                ResponseOriginAladinDetail.class);

            ResponseAladinDetail apiResponse = bookMapper.toResopnseAladinDetail(originResponse);

            if (!apiResponse.getItem()
                            .isEmpty()) {
                // 카테고리 매핑 적용
                ResponseAladinSearchDetail searchDetail = apiResponse.getItem()
                                                                     .get(0);

                // cover img url 의 기본 이미지 -> 500 사이즈로
                searchDetail.setCoverImgUrl(
                    replaceCoverImgUrlCoverSum(searchDetail.getCoverImgUrl(), "cover500"));

                aladinCategoryService.processApiResponse(searchDetail);
                return searchDetail; // 첫 번째 아이템 반환
            } else {
                throw new IOException("No book found with the given ISBN: " + isbn);
            }
        } else {
            throw new IOException("Failed to fetch data from Aladin API: " + response.body());
        }
    }

    /**
     * coverImgUrl 값 중 "coversum"을 지정된 문자열로 바꾸는 메서드
     *
     * @param coverImgUrl 원본 coverImgUrl
     * @param replacement 바꿀 문자열
     * @return 변환된 coverImgUrl
     */
    private String replaceCoverImgUrlCoverSum(String coverImgUrl, String replacement) {
        if (coverImgUrl != null && coverImgUrl.contains("coversum")) {
            return coverImgUrl.replace("coversum", replacement);
        }
        return coverImgUrl;
    }
}
