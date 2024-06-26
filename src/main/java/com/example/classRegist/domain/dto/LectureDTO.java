package com.example.classRegist.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class LectureDTO {

    private String lectureId;
    private String lectureNm;
    private LocalDateTime lectureDt;
    private Long capacity;
    private Long leftOverCnt;
    private LocalDateTime applyDt;


}
