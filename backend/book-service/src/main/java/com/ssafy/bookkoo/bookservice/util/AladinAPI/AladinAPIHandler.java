package com.ssafy.bookkoo.bookservice.util.AladinAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.bookkoo.bookservice.dto.aladin.AladinAPISearchParams;
import com.ssafy.bookkoo.bookservice.dto.aladin.ResponseAladinAPI;
import com.ssafy.bookkoo.bookservice.dto.aladin.ResponseAladinDetail;
import com.ssafy.bookkoo.bookservice.dto.aladin.ResponseAladinSearchDetail;
import com.ssafy.bookkoo.bookservice.dto.aladin.ResponseOriginAladinAPI;
import com.ssafy.bookkoo.bookservice.dto.aladin.ResponseOriginAladinDetail;
import com.ssafy.bookkoo.bookservice.mapper.BookMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
    public ResponseAladinAPI searchBooks(AladinAPISearchParams params)
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
            // 카테고리 매핑 적용
            aladinCategoryService.processApiResponse(apiResponse);

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
                aladinCategoryService.processApiResponse(searchDetail);
                return searchDetail; // 첫 번째 아이템 반환
            } else {
                throw new IOException("No book found with the given ISBN: " + isbn);
            }
        } else {
            throw new IOException("Failed to fetch data from Aladin API: " + response.body());
        }
    }
}
