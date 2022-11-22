package com.example.First.Project.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {

    //특정 어노테이션을 대상지정
    @Pointcut("@annotation(com.example.First.Project.annotation.RunningTime")
    private void enableRunningTime(){

    }
    //기본 패키지의 모든 메소드
    @Pointcut("execution(* com.example.First.Project..*.*(..))")
    private void cut(){

    }

    //실행 시점을 설정 : 두 조건을 모두 만족하는 대상을 전 후로 부가 기능을 삽입
    @Around("cut() && enableRunningTime()")
    public void loggingRunningTime(ProceedingJoinPoint joinPoint) throws Throwable {
        //메소드 수행 전 측정 시작
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        //메소드를 수행
        Object returnObj = joinPoint.proceed(); //타켓팅 된 대상 수행 후 반환
        //메소드를 수행 후, 측정 종료 및 로깅
        String methodName = joinPoint.getSignature().getName();
        stopWatch.stop();
        log.info("{}의 총 수행시간 => {}sec", methodName, stopWatch.getTotalTimeSeconds(), returnObj);

    }

}
