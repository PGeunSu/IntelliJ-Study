package Hello.HelloSpring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect //Advice(부가 기능을 담고 있는 모듈) + Point cut(Advice 가 적용될 위치) = Aspect
public class TmeTraceAop {

    @Around("execution(* Hello.HelloSpring..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();

        System.out.println("START : " + joinPoint.toString());

        try{
            return joinPoint.proceed();
        }finally{
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;

            System.out.println("END : " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
