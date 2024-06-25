package com.example.classRegist.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="Lecture", uniqueConstraints = {@UniqueConstraint(name="NAME_DATE_UNIQUE,",columnNames = {"CLASS_NM","LECTURE_DT"})}) // 강의 이름, 강의 진행날짜를 unique key로 생성하여 중복 방지
public class Lecture extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String lectureId;


    @Column(name = "LECTURE_NM")
    private String lectureNm;


    @Column(name="LECTURE_DT")
    private LocalDateTime lectureDt;

    @Column(name="CAPACITY")
    @ColumnDefault("30")
    private Long capacity;

    @Column(name="LEFTOVER_CNT")
    @ColumnDefault("30")
    private Long leftOverCnt;

    @Column(name="APPLY_DT")
    private LocalDateTime applydt;


}
