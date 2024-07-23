package com.ssafy.bookkoo.libraryservice.repository;

import com.ssafy.bookkoo.libraryservice.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library, Long> {

}
