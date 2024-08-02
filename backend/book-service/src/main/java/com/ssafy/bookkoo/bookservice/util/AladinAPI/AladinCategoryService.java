package com.ssafy.bookkoo.bookservice.util.AladinAPI;

import com.ssafy.bookkoo.bookservice.dto.aladin.AladinBookItem;
import com.ssafy.bookkoo.bookservice.dto.aladin.ResponseAladinAPI;
import com.ssafy.bookkoo.bookservice.dto.aladin.ResponseAladinSearchDetail;
import com.ssafy.bookkoo.bookservice.entity.AladinCategoryMapper;
import com.ssafy.bookkoo.bookservice.entity.Category;
import com.ssafy.bookkoo.bookservice.repository.category.AladinCategoryMapperRepository;
import com.ssafy.bookkoo.bookservice.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AladinCategoryService {

    final private AladinCategoryMapperRepository categoryMapperRepository;
    final private CategoryRepository categoryRepository;

    public Category convertToServiceCategoryId(Integer aladinCategoryId) {

        AladinCategoryMapper mapper = categoryMapperRepository.findByAladinCategoryId(
            aladinCategoryId);
        if (mapper != null) {
            return mapper.getCategory();
        } else {
            // If no mapping found, return ID for "기타" category or null
            return categoryRepository.findByName("기타");
        }
    }

    public void processApiResponse(ResponseAladinAPI response) {
        for (AladinBookItem item : response.getItem()) {
            Integer aladinCategoryId = item.getCategoryId();
            Category category = convertToServiceCategoryId(aladinCategoryId);
            item.setCategory(category);
        }
    }

    public void processApiResponse(ResponseAladinSearchDetail item) {
        Integer aladinCategoryId = item.getCategoryId();
        Category category = convertToServiceCategoryId(aladinCategoryId);
        item.setCategory(category);
    }
}
