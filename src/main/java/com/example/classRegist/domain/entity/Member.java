package com.example.classRegist.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="MEMBER_ID")
    private String memberId;

    @Column(name ="MEMBER_NM")
    private String memberNm;






}
