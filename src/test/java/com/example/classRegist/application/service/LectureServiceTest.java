package com.example.classRegist.application.service;

import com.example.classRegist.application.service.LectureService;
import com.example.classRegist.common.exception.MemberNotFoundException;
import com.example.classRegist.presenter.BaseRequest;
import com.example.classRegist.infra.repository.ApplyRepository;
import com.example.classRegist.infra.repository.LectureRepository;
import com.example.classRegist.infra.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LectureServiceTest {

    @InjectMocks
    LectureService lectureService;

    @Mock
    MemberRepository memberRepository;

    @Mock
    LectureRepository lectureRepo;

    @Mock
    ApplyRepository applyRepo;

    @DisplayName("예외 1: 등록되지 않은 사용자일 경우 : MemberNotFoundException ")
    @Test
    void memberNotFoundException_whenApply() {
        // given
        String memberId = "testId";
        String letureId = "lecture";
        BaseRequest baseRequest = new BaseRequest(memberId,letureId);

        // when : LectureService의 checkMember() return null로 설정
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        // Then : 미등록 사용자 신청 시도 시 MemberNotFoundException
        assertThrows(MemberNotFoundException.class, ()->lectureService.applyLecture(baseRequest));

    }







}