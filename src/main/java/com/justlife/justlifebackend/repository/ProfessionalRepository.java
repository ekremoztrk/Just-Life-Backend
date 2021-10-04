package com.justlife.justlifebackend.repository;

import com.justlife.justlifebackend.model.Professional;
import com.justlife.justlifebackend.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessionalRepository extends BaseRepository<Professional, Long> {

    Optional<Professional> findById(Long id);

}
