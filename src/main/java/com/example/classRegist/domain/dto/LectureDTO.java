package com.example.classRegist.domain.dto;

import com.example.classRegist.domain.entity.Lecture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LectureDTO {

    private String lectureId;
    private String lectureNm;
    private LocalDateTime lectureDt;
    private Long capacity;
    private Long leftOverCnt;
    private LocalDateTime applyDt;

    public LectureDTO(Lecture lecture){
        this.lectureId = lecture.getLectureId();
        this.lectureNm = lecture.getLectureNm();
        this.lectureDt = lecture.getLectureDt();
        this.capacity = lecture.getCapacity();
        this.leftOverCnt = lecture.getLeftOverCnt();
        this.applyDt = lecture.getApplyDt();
    }


}
