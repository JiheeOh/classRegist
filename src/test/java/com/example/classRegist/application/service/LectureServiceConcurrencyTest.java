package com.example.classRegist.application.service;

import com.example.classRegist.common.exception.RegiFailException;
import com.example.classRegist.presenter.BaseRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LectureServiceConcurrencyTest {

   @Autowired
   LectureService lectureService;

   /**
    * 정원이 2명인 강의에 3명이 신청을 시도할 경우 regiFailException 발생
    */
   @DisplayName("정원초과할 경우 RegiFailException 발생")
   @Test
   void RegiFailException(){
      // given :
      BaseRequest successRequest1 = new BaseRequest("testMember1","testLecture4");
      BaseRequest successRequest2 = new BaseRequest("testMember2","testLecture4");
      BaseRequest failRequest = new BaseRequest("testMember3","testLecture4");


      // when : 정원 2명인 강의에 3명이 신청
      CompletableFuture<Void> resultActions1 = CompletableFuture.runAsync(() ->
                                                lectureService.applyLecture(successRequest1));
      CompletableFuture<Void> resultActions2 = CompletableFuture.runAsync(() ->
                                                lectureService.applyLecture(successRequest2));
      CompletableFuture<Void> resultActions3 = CompletableFuture.runAsync(() ->
                                                lectureService.applyLecture(failRequest));


      List<CompletableFuture<Void>> futures = List.of(resultActions1, resultActions2, resultActions3);

      // Then : RegiFailException이 발생하는 지
      CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).exceptionally(e ->{
         assertThat(e).isEqualTo(RegiFailException.class);
         return null;
      });

   }

   /**
    * 특강을 신청한 순서대로 신청완료가 잘되었는지 확인
    */
   @DisplayName("정원초과되기전 신청한 사용자는 저장이 잘되었는지 확인")
   @Test
   void RegiSuccess_WhenOtherFailed(){
      // given :
      BaseRequest successRequest1 = new BaseRequest("testMember1","testLecture4");
      BaseRequest successRequest2 = new BaseRequest("testMember2","testLecture4");
      BaseRequest failRequest = new BaseRequest("testMember3","testLecture4");


      // when : 정원 2명인 강의에 3명이 신청
      CompletableFuture<Void> resultActions1 = CompletableFuture.runAsync(() ->
                                                lectureService.applyLecture(successRequest1));

      CompletableFuture<Void> resultActions2 = CompletableFuture.runAsync(() ->
                                                lectureService.applyLecture(successRequest2));

      CompletableFuture<Void> resultActions3 = CompletableFuture.runAsync(() ->
                                                lectureService.applyLecture(failRequest));


      List<CompletableFuture<Void>> futures = List.of(resultActions1, resultActions2, resultActions3);

      // Then : 특강 신청이 순차적으로 진행되었는지 확인
      CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).thenRun(()->{
         assertThat(lectureService.isApplied(successRequest1.getMemberId(),successRequest1.getLectureId()))
                 .as("successRequest1").isEqualTo(true);
         assertThat(lectureService.isApplied(successRequest2.getMemberId(),successRequest2.getLectureId()))
                 .as("successRequest2").isEqualTo(true);
         assertThat(lectureService.isApplied(failRequest.getMemberId(),failRequest.getLectureId()))
                 .as("failRequest").isEqualTo(false);
              }
      );
   }



}