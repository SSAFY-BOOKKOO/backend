package com.ssafy.bookkoo.booktalkservice.repository;

import java.util.List;

public interface BookTalkMapperCustomRepository {

    List<Long> findAllBookIds();
}
