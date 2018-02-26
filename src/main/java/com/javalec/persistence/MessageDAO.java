package com.javalec.persistence;

import com.javalec.domain.MessageVO;

//메시지에 대한 등록, 수정, 업데이트 처리
public interface MessageDAO {

  public void create(MessageVO vo) throws Exception;

  public MessageVO readMessage(Integer mid) throws Exception;

  public void updateState(Integer mid) throws Exception;

}
