package com.example.classRegist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class LectureDTO {

    private String lectureId;
    private String lectureNm;
    private LocalDateTime lectureDt;
    private Long capacity;
    private Long leftOverCnt;
    private LocalDateTime applyDt;


}
