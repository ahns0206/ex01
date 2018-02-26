package com.javalec.service;

import com.javalec.domain.MessageVO;

public interface MessageService {

  public void addMessage(MessageVO vo) throws Exception;

  public MessageVO readMessage(String uid, Integer mno) throws Exception;
}
