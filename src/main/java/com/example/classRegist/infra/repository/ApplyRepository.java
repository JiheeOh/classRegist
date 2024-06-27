package com.example.classRegist.infra.repository;

import com.example.classRegist.domain.entity.Apply;
import com.example.classRegist.domain.entity.ApplyPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, ApplyPk> {
}
