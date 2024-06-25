package com.example.classRegist.application.service;

import com.example.classRegist.domain.entity.Apply;
import com.example.classRegist.domain.entity.ApplyPk;
import com.example.classRegist.domain.entity.Lecture;
import com.example.classRegist.dto.RequestDto;
import com.example.classRegist.common.exception.DupliApplyException;
import com.example.classRegist.common.exception.LectureNotFoundException;
import com.example.classRegist.common.exception.MemberNotFoundException;
import com.example.classRegist.common.exception.RegiFailException;
import com.example.classRegist.infra.repository.ApplyRepository;
import com.example.classRegist.infra.repository.LectureRepository;
import com.example.classRegist.infra.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class LectureService {

    private final MemberRepository memberRepo;

    private final LectureRepository lectureRepo;

    private final ApplyRepository applyRepo;

    public LectureService(MemberRepository memberRepo, LectureRepository lectureRepo, ApplyRepository applyRepo) {
        this.memberRepo = memberRepo;
        this.lectureRepo = lectureRepo;
        this.applyRepo = applyRepo;
    }

    /**
     * 특강 신청하는 서비스
     *
     * @param request : 사용자 Id, 강의 Id
     */
    public void applyLecture(RequestDto request) {

        String memberId = request.getMemberId();
        String lectureId = request.getLectureId();

        // 등록할 수 있는 유효한 사용자/강의인지 확인
        checkMember(memberId, lectureId);
        Lecture lecture = getAvailableLecture(lectureId);

        // 등록 진행
        Apply apply = new Apply(new ApplyPk(memberId, lectureId));
        applyRepo.save(apply);

        // 잔여인원 감소시키기
        reduceLeftOver(lecture);
    }


    /**
     * 수업을 등록할 수 있는 사용자인지 확인
     * - 미등록 사용자 ID일 경우 : MemberNotFoundException
     *
     * @param memberId  : 찾으려는 사용자 ID
     * @param lectureId
     * @return Member : 등록된 사용자
     */
    private boolean checkMember(String memberId, String lectureId) {
        memberRepo.findById(memberId).orElseThrow(() -> new MemberNotFoundException("You are not member", 500));
        if (applyRepo.existsById(new ApplyPk(memberId, lectureId))) {
            throw new DupliApplyException(String.format("You already registered same class : %s", lectureId), 500);
        }
        return true;
    }

    /**
     * 등록할 수 있는 강의만 return
     * - 등록된 강의가 아니면 LectureNotFoundException
     * - 등록된 강의이지만 정원 초과일 경우 RegiFailException
     *
     * @param lectureId : 등록하려는 강의 ID
     * @return Lecture : 등록가능한 강의
     */
    private Lecture getAvailableLecture(String lectureId) {
        // 해당 강의 search
        Lecture lecture = lectureRepo.findById(lectureId).orElseThrow(() -> new LectureNotFoundException(String.format("There are no lecture like %s", lectureId), 500));

        // 해당 강의의 정원이 꽉 찼을 경우 등록 실패
        if (lecture.getLeftOverCnt() <= 0) {
            throw new RegiFailException(String.format("The Lecture you choose closed : %s  ", lectureId), 500);
        }
        return lecture;
    }

    /**
     * 신청 완료한 강의의 잔여인원 줄이기
     * 한명의 사용자는 하나의 자리만 신청할 수 있다.
     *
     * @param lecture 등록하려는 강의
     */
    private void reduceLeftOver(Lecture lecture) {
        lecture.setLeftOverCnt(lecture.getLeftOverCnt() - 1);
        lectureRepo.saveAndFlush(lecture);
    }

    /**
     * 특강 신청 완료 여부를 조회
     *
     * @param memberId  사용자 ID
     * @param lectureId 신청한 특강 ID
     * @return 특강 신청 완료 여부
     */
    public boolean isApplied(String memberId, String lectureId) {
        return applyRepo.existsById(new ApplyPk(memberId,lectureId));
    }
}
