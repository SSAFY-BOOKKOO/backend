package com.ssafy.bookkoo.libraryservice.service;

import com.ssafy.bookkoo.libraryservice.repository.LibraryBookMapperRepository;
import com.ssafy.bookkoo.libraryservice.repository.LibraryRepository;
import com.ssafy.bookkoo.libraryservice.repository.LibraryStyleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final LibraryRepository libraryRepository;
    private final LibraryStyleRepository libraryStyleRepository;
    private final LibraryBookMapperRepository libraryBookMapperRepository;
    
}
