package com.example.classRegist.application.service;

import com.example.classRegist.common.exception.DupliApplyException;
import com.example.classRegist.common.exception.LectureNotFoundException;
import com.example.classRegist.common.exception.MemberNotFoundException;
import com.example.classRegist.common.exception.RegiFailException;
import com.example.classRegist.domain.dto.LectureDTO;
import com.example.classRegist.presenter.BaseRequest;
import com.example.classRegist.domain.entity.Apply;
import com.example.classRegist.domain.entity.ApplyPk;
import com.example.classRegist.domain.entity.Lecture;
import com.example.classRegist.infra.repository.ApplyRepository;
import com.example.classRegist.infra.repository.LectureRepository;
import com.example.classRegist.infra.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public void applyLecture(BaseRequest request) {
        String memberId = request.getMemberId();
        String lectureId = request.getLectureId();

        // 등록할 수 있는 유효한 사용자/강의인지 확인
        checkValidate(memberId, lectureId);

        // 등록 시도
        tryApply(memberId,lectureId);
    }




    /**
     * 수업을 등록할 수 있는 사용자인지 확인
     * - 미등록 사용자 ID일 경우 : MemberNotFoundException
     * - 중복 수업 신청 시 : DupliApplyException
     *
     * @param memberId  : 찾으려는 사용자 ID
     * @param lectureId : 등록하려는 강의 ID
     * @return Member : 등록된 사용자
     */
    private boolean checkValidate(String memberId, String lectureId) {
        memberRepo.findById(memberId).orElseThrow(() -> new MemberNotFoundException("You are not member", 500));
        if (applyRepo.existsById(new ApplyPk(memberId, lectureId))) {
            throw new DupliApplyException(String.format("You already registered same class : %s", lectureId), 500);
        }
        return true;
    }

    /**
     * 수업 등록 메소드
     * 배타적 락을 사용하여
     * 1. 등록할 수 있는 lecture인지 확인
     * 2. 등록 테이블에 데이터 적재
     * 3. 잔여인원 감소시키기
     *  위 3가지 step을 한 transaction에서 관리
     *
     * @param memberId : 사용자 Id
     * @param lectureId : 강의 Id
     */
    @Transactional
    protected void tryApply(String memberId, String lectureId) {

        // step1 : 등록할 수 있는 Lecture인지 확인
        Lecture lecture = getAvailableLecture(lectureId);

        // step2 : 등록 진행
        Apply apply = new Apply(new ApplyPk(memberId, lectureId));
        applyRepo.save(apply);

        // step3 : 잔여인원 감소시키기
        reduceLeftOver(lecture);
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
        return applyRepo.existsById(new ApplyPk(memberId, lectureId));
    }

    /**
     * 강의 목록 조회
     * @return 등록되어있는 강의 목록
     */
    public List<LectureDTO> getLectureList() {
        return lectureRepo.findAll(Sort.by(Sort.Direction.ASC,"applyDt" )).stream().map(LectureDTO::new).collect(Collectors.toList());
    }
}
