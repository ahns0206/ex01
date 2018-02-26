package com.javalec.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint; //전달되는 파라미터 파악 위한 라이브러리
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component //스프링의 빈으로 인식되기 위한 설정
@Aspect //AOP 기능을 하는 클래스의 선언에 추가해야 함
public class SampleAdvice {

  private static final Logger logger = LoggerFactory.getLogger(SampleAdvice.class);

  /*
   * @Before : 해당 메소드 실행 후 target메소드가 실행
   * JointPoint : 실행시 전달되는 파라미터 파악 시 사용되는 자료형
   * execution : Pointcut을 지정하는 AspectJ 문법
   */
//  @Before("execution(* com.javalec.service.MessageService*.*(..))")
  public void startLog(JoinPoint jp) { //왼쪽에 STS상 AOP와 관련된 아이콘 뜬것이 확인됨
	  
	  logger.info("----------------------------");
	  logger.info("----------------------------");
	  logger.info(Arrays.toString(jp.getArgs())); //jp.getArgs : 전달되는 모든 파라미터들을 Object 배열로 가져옴
  }
  
  /*
   * @Around : 해당 메소드는 target메소드 전체를 앞, 뒤로 감싸 실행
   * ProcedingJoinPoint : Around에서 사용 가능한 파라미터 타입으로 JoinPoint 기능 + 다음 Advice 실행 기능 + target객체의 메소드 실행 가능
   * 메소드 선언에 일반적인 Exception이 아닌, 상위의 Throwable 사용함
   */
//  @Around("execution(* com.javalec.service.MessageService*.*(..))")
  public Object timeLog(ProceedingJoinPoint pjp)throws Throwable{

	  long startTime = System.currentTimeMillis();
	  logger.info(Arrays.toString(pjp.getArgs()));
      
	  Object result = pjp.proceed(); //proceed : 실제 메소드 호출

	  long endTime = System.currentTimeMillis();
	  logger.info( pjp.getSignature().getName()+ " : " + (endTime - startTime) + "ms" );
	  logger.info("=============================================");
	  
	  return result; //@Around의 경우 반드시 Object를 반환해야 함
  }
}
