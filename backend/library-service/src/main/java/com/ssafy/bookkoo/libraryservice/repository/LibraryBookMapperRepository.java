package com.ssafy.bookkoo.libraryservice.repository;

import com.ssafy.bookkoo.libraryservice.entity.LibraryBookMapper;
import com.ssafy.bookkoo.libraryservice.entity.MapperKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryBookMapperRepository extends JpaRepository<LibraryBookMapper, MapperKey>,
    LibraryBookMapperCustomRepository {

    boolean existsById(MapperKey id);
}
