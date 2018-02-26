package com.javalec.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.javalec.domain.SampleVO;

@RestController //JSP와 같은 뷰가 아닌 단순 문자열, JSON, XML등을 반환함을 의미
@RequestMapping("/sample")
public class SampleController {

	//http://localhost:8181/sample/hello
  @RequestMapping("/hello")
  public String sayHello() {
    return "Hello World "; //단순 문자열 반환
  }

  //http://localhost:8181/sample/sendVO
  @RequestMapping("/sendVO")
  public SampleVO sendVO() {

    SampleVO vo = new SampleVO();
    vo.setFirstName("SeHee");
    vo.setLastName("Ahn");
    vo.setMno(123);

    return vo; //클래스 타입의 객체 반환  (jackson-databind 라이브러리 필요함)
  }

  //http://localhost:8181/sample/sendList
  @RequestMapping("/sendList")
  public List<SampleVO> sendList() {

    List<SampleVO> list = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      SampleVO vo = new SampleVO();
      vo.setFirstName("SeHee");
      vo.setLastName("Ahn");
      vo.setMno(i);
      list.add(vo);
    }
    
    return list; //컬렉션 타입의 객체인 List 반환 (JSON 문법 상 리스트는 배열로 표현됨)
  }

  //http://localhost:8181/sample/sendMap
  @RequestMapping("/sendMap")
  public Map<Integer, SampleVO> sendMap() {

    Map<Integer, SampleVO> map = new HashMap<>();

    for (int i = 0; i < 10; i++) {
      SampleVO vo = new SampleVO();
      vo.setFirstName("SeHee");
      vo.setLastName("Ahn");
      vo.setMno(i);
      map.put(i, vo); //키(숫자), 값(SampleVO타입의 객체)
    }
    return map;
  }

  //http://localhost:8181/sample/sendErrorAuth
  @RequestMapping("/sendErrorAuth")
  public ResponseEntity<Void> sendListAuth() {

    return new ResponseEntity<>(HttpStatus.BAD_REQUEST); //HTTP Status Code가 400
    //ResponseEntity 타입은 개발자가 데이터+HTTP 상태코드를 직접 제어 가능한 클래스임
  }

  //http://localhost:8181/sample/sendErrorNot
  @RequestMapping("/sendErrorNot")
  public ResponseEntity<List<SampleVO>> sendListNot() {

    List<SampleVO> list = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      SampleVO vo = new SampleVO();
      vo.setFirstName("SeHee");
      vo.setLastName("Ahn");
      vo.setMno(i);
      list.add(vo);
    }

    return new ResponseEntity<>(list, HttpStatus.NOT_FOUND); //list 데이터, 404 HTTP Status Code
    //일반적인 404 메시지와는 달리 전송한 결과는 그대로 보여주면서 상태 코드를 전달함 (호출한 쪽으로 에러 전송)
    //ResponseEntity 타입은 개발자가 데이터+HTTP 상태코드를 직접 제어 가능한 클래스임
  }

}
