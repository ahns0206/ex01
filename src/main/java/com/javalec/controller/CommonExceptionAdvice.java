package com.javalec.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice //해당 클래스 객체가 컨트롤러에서 발생하는 Exception을 처리하는 클래스라는 것을 명시
public class CommonExceptionAdvice {
  private static final Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);

  //@ExceptionHandler(Exception.class) //Exception 타입으로 처리되는 모든 예외를 처리
  public String common(Exception e) {

    logger.info(e.toString());

    return "error_common";
  }

  @ExceptionHandler(Exception.class) //Exception 타입으로 처리되는 모든 예외를 처리
  private ModelAndView errorModelAndView(Exception ex) { //파라미터로 Exception타입의 객체만 사용 가능

    ModelAndView modelAndView = new ModelAndView(); //Model데이터와 View처리가 동시에 가능한 객체
    modelAndView.setViewName("/error_common");
    modelAndView.addObject("exception", ex);

    return modelAndView; //error_common.jsp에서 BoardController의 실행 중 발생하는 Exception을 상세하게 볼 수 있음
  }

}


