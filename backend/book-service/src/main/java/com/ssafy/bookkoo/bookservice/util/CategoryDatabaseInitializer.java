package com.ssafy.bookkoo.bookservice.util;

import com.opencsv.CSVReader;
import com.ssafy.bookkoo.bookservice.entity.AladinCategoryMapper;
import com.ssafy.bookkoo.bookservice.entity.Category;
import com.ssafy.bookkoo.bookservice.repository.AladinCategoryMapperRepository;
import com.ssafy.bookkoo.bookservice.repository.CategoryRepository;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryDatabaseInitializer {

    private final CategoryRepository categoryRepository;
    private final AladinCategoryMapperRepository aladinCategoryMapperRepository;
    private final ResourceLoader resourceLoader;
    private Map<String, String> genreMappingRules;

    public void init() {
        List<String> categoryNames = new ArrayList<>();
        categoryNames.add("추리/스릴러");
        categoryNames.add("판타지");
        categoryNames.add("로맨스");
        categoryNames.add("인문학");
        categoryNames.add("철학");
        categoryNames.add("경제/경영");
        categoryNames.add("역사");
        categoryNames.add("시");
        categoryNames.add("소설");
        categoryNames.add("사회");
        categoryNames.add("과학/기술");
        categoryNames.add("교육");
        categoryNames.add("자기계발");
        categoryNames.add("에세이");
        categoryNames.add("기타");
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

        // category 초기화
        for (String genre : categoryNames) {
            Category category = Category.builder()
                                        .name(genre)
                                        .build();
            categoryRepository.save(category);
        }

        // Load the CSV file and parse the data
        List<String[]> categoryMappings = parseCsv(
            "classpath:aladin_category.csv");

        // Initialize category mappings
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

                Category serviceCategory = categoryRepository.findByName(
                    internalCategory);
                if (serviceCategory != null) {
                    AladinCategoryMapper aladinCategoryMapping = AladinCategoryMapper.builder()
                                                                                     .aladinCategoryId(
                                                                                         aladinCategoryId)
                                                                                     .category(
                                                                                         serviceCategory)
                                                                                     .name(
                                                                                         categoryName)
                                                                                     .build();
                    aladinCategoryMapperRepository.save(aladinCategoryMapping);
                }
            } catch (Exception e) {
                // 에러나면 컨티뉴
            }

        }
    }

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
