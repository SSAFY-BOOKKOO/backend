package com.ssafy.bookkoo.bookservice.util.AladinAPI;

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

    public Integer convertToServiceCategoryId(Integer aladinCategoryId) {

        AladinCategoryMapper mapper = categoryMapperRepository.findByAladinCategoryId(
            aladinCategoryId);
        if (mapper != null) {
            return mapper.getCategory()
                         .getId();
        } else {
            // If no mapping found, return ID for "기타" category or null
            Category otherCategory = categoryRepository.findByName("기타");
            return otherCategory != null ? otherCategory.getId() : null;
        }
    }

    public void processApiResponse(ResponseAladinAPI response) {
        for (AladinBookItem item : response.getItem()) {
            Integer aladinCategoryId = item.getCategoryId();
            Integer serviceCategoryId = convertToServiceCategoryId(aladinCategoryId);
            item.setCategoryId(serviceCategoryId);
        }
    }

    public void processApiResponse(ResponseAladinSearchDetail item) {
        Integer aladinCategoryId = item.getCategoryId();
        Integer serviceCategoryId = convertToServiceCategoryId(aladinCategoryId);
        item.setCategoryId(serviceCategoryId);
    }
}
