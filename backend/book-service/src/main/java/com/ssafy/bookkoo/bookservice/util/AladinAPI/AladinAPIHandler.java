package com.ssafy.bookkoo.bookservice.util.AladinAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AladinAPIHandler {

    private static String apiKey;

    @Value("${aladin.api.key}")
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
    public static ResponseAladinAPI searchBooksFromAladin(AladinAPISearchParams params)
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
//            JSONParser parser = new JSONParser();
//            return (JSONObject) parser.parse(response.body());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.body(), ResponseAladinAPI.class);
        } else {
            throw new IOException("Failed to fetch data from Aladin API: " + response.body());
        }
    }
}
