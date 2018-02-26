package com.javalec.controller;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.javalec.domain.MessageVO;
import com.javalec.service.MessageService;

@RestController //MessageController를 @RestController로 지정하여 Ajax 등을 이용해서 메시지 보낼 수 있음
@RequestMapping("/messages")
public class MessageController {

  @Inject
  private MessageService service;

  @RequestMapping(value = "/", method = RequestMethod.POST)
  public ResponseEntity<String> addMessage(@RequestBody MessageVO vo) {

    ResponseEntity<String> entity = null;
    try {
      service.addMessage(vo);
      entity = new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return entity;
  }

}
