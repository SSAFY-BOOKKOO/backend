package com.ssafy.bookkoo.bookservice.util;

import com.opencsv.CSVReader;
import com.ssafy.bookkoo.bookservice.entity.AladinCategoryMapper;
import com.ssafy.bookkoo.bookservice.entity.Category;
import com.ssafy.bookkoo.bookservice.repository.category.AladinCategoryMapperRepository;
import com.ssafy.bookkoo.bookservice.repository.category.CategoryRepository;
import jakarta.transaction.Transactional;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

/**
 * 카테고리 데이터베이스 초기화 서비스 클래스입니다.
 */
@Service
@RequiredArgsConstructor
public class CategoryDatabaseInitializer {

    private final CategoryRepository categoryRepository;
    private final AladinCategoryMapperRepository aladinCategoryMapperRepository;
    private final ResourceLoader resourceLoader;
    private Map<String, String> genreMappingRules;

    /**
     * 카테고리와 알라딘 카테고리 매핑을 초기화합니다.
     */
    @Transactional
    public void init() {
        List<String> categoryNames = List.of("추리/스릴러", "판타지", "로맨스", "인문학", "철학", "경제/경영",
            "역사", "시", "소설", "사회", "과학/기술", "교육", "자기계발", "에세이", "기타");

        // Define genre mapping rules
        genreMappingRules = new TreeMap<>();
        genreMappingRules.put("추리", "추리/스릴러");
        genreMappingRules.put("스릴러", "추리/스릴러");
        genreMappingRules.put("미스터리", "추리/스릴러");
        genreMappingRules.put("판타지", "판타지");
        genreMappingRules.put("로맨스", "로맨스");
        genreMappingRules.put("경제/경영", "경제/경영");
        genreMappingRules.put("경제", "경제/경영");
        genreMappingRules.put("경영", "경제/경영");
        genreMappingRules.put("철학", "철학");
        genreMappingRules.put("참고서", "교육");
        genreMappingRules.put("교재", "교육");
        genreMappingRules.put("청소년", "교육");
        genreMappingRules.put("자격증", "교육");
        genreMappingRules.put("인문학", "인문학");
        genreMappingRules.put("인문", "인문학");
        genreMappingRules.put("문학", "인문학");
        genreMappingRules.put("역사", "역사");
        genreMappingRules.put("사회", "사회");
        genreMappingRules.put("시", "시");
        genreMappingRules.put("소설", "소설");
        genreMappingRules.put("과학", "과학/기술");
        genreMappingRules.put("기술", "과학/기술");
        genreMappingRules.put("공학", "과학/기술");
        genreMappingRules.put("컴퓨터", "과학/기술");
        genreMappingRules.put("자기계발", "자기계발");
        genreMappingRules.put("에세이", "에세이");
        genreMappingRules.put("기타", "기타");

        // Batch insert categories
        List<Category> categories = categoryNames.stream()
                                                 .map(name -> Category.builder()
                                                                      .name(name)
                                                                      .build())
                                                 .collect(Collectors.toList());
        categoryRepository.saveAll(categories);

        // Load the CSV file and parse the data
        List<String[]> categoryMappings = parseCsv("classpath:aladin_category.csv");

        // Initialize category mappings
        List<AladinCategoryMapper> aladinCategoryMappings = new ArrayList<>();
        for (String[] mapping : categoryMappings) {
            try {
                Integer aladinCategoryId = Integer.valueOf(mapping[0]);
                String categoryName = mapping[1]; // 카테고리 이름
                String depth1Category = mapping[3];  // 1Depth 열의 인덱스
                String depth2Category = mapping[4];  // 2Depth 열의 인덱스
                String externalCategory = categoryName + depth1Category + depth2Category;

                String internalCategory = "기타";
                for (Map.Entry<String, String> entry : genreMappingRules.entrySet()) {
                    if (externalCategory.contains(entry.getKey())) {
                        internalCategory = entry.getValue();
                        break;
                    }
                }

                Category serviceCategory = categoryRepository.findByName(internalCategory);
                if (serviceCategory != null) {
                    AladinCategoryMapper aladinCategoryMapping = AladinCategoryMapper.builder()
                                                                                     .aladinCategoryId(
                                                                                         aladinCategoryId)
                                                                                     .category(
                                                                                         serviceCategory)
                                                                                     .name(
                                                                                         categoryName)
                                                                                     .build();
                    aladinCategoryMappings.add(aladinCategoryMapping);
                }
            } catch (Exception e) {
                // 에러나면 컨티뉴
                continue;
            }
        }

        // Batch insert AladinCategoryMappings
        aladinCategoryMapperRepository.saveAll(aladinCategoryMappings);
    }

    /**
     * 주어진 경로의 CSV 파일을 파싱합니다.
     *
     * @param resourcePath 리소스 경로
     * @return 파싱된 CSV 데이터 리스트
     */
    private List<String[]> parseCsv(String resourcePath) {
        try {
            Resource resource = resourceLoader.getResource(resourcePath);
            try (CSVReader reader = new CSVReader(
                new InputStreamReader(resource.getInputStream(), "euc-kr"))) {
                // Skip the first three rows
                reader.readNext();
                reader.readNext();
                reader.readNext();

                return reader.readAll();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read CSV file", e);
        }
    }
}
