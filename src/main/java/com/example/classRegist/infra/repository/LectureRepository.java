package com.example.classRegist.infra.repository;

import com.example.classRegist.domain.entity.Lecture;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LectureRepository extends JpaRepository<Lecture,String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Lecture> findById(String lectureId);
}
