package com.javalec.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.javalec.domain.MessageVO;
import com.javalec.persistence.MessageDAO;
import com.javalec.persistence.PointDAO;

@Service
public class MessageServiceImpl implements MessageService {

  @Inject
  private MessageDAO messageDAO;

  @Inject
  private PointDAO pointDAO;


  //@Transactional
  @Override
  public void addMessage(MessageVO vo) throws Exception {

    messageDAO.create(vo); //새로운 메시지 추가
    pointDAO.updatePoint(vo.getSender(), 10); //메시지 보낸 이에게 포인트 10점 추가
  }

  @Override
  public MessageVO readMessage(String uid, Integer mid) throws Exception {
 
    messageDAO.updateState(mid); //메시지 상태 변경

    pointDAO.updatePoint(uid, 5); //메시지를 본 이에게 포인트 5점 추가

    return messageDAO.readMessage(mid); //메시지를 조회해서 반환
  }
}
