package com.example.classRegist.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
public class ApplyPk implements Serializable {

    private String memberId;
    private String lectureId;

    public ApplyPk() {

    }
}
