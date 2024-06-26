package com.example.classRegist.presenter.controller;

import com.example.classRegist.domain.dto.LectureDTO;
import com.example.classRegist.domain.dto.RequestDto;
import com.example.classRegist.application.service.LectureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lectures")
public class LectureController {

    private final LectureService lectureService;

    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    // 특강 신청
    @PostMapping("/apply")
    public ResponseEntity<Object> lectureApply(@RequestBody RequestDto request) {
        lectureService.applyLecture(request);
        return ResponseEntity.ok().body("Lecture applied");
    }

    @GetMapping("/application/{memberId}/{lectureId}")
    public ResponseEntity<Object> getApplyResult(@PathVariable String memberId
                                                , @PathVariable String lectureId) {
        boolean result = lectureService.isApplied(memberId,lectureId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("")
    public ResponseEntity<Object> getLectureList(){
        List<LectureDTO> lectures = lectureService.getLectureList();
        return ResponseEntity.ok().body(lectures);
    }
}
