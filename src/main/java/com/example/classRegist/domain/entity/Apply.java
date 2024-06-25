package com.example.classRegist.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Apply extends BaseEntity {

    @EmbeddedId
    private ApplyPk applyId;

    public Apply() {

    }
}
