package com.ssafy.bookkoo.memberservice.repository;

import com.ssafy.bookkoo.memberservice.entity.CertificationNumber;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CertificationRepository extends CrudRepository<CertificationNumber, String> {
    Optional<CertificationNumber> findByEmailAndCertNum(String email, String certNum);

    List<CertificationNumber> findByEmail(String email);
}
