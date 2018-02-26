package com.javalec.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javalec.domain.Criteria;
import com.javalec.domain.ReplyVO;
import com.javalec.persistence.BoardDAO;
import com.javalec.persistence.ReplyDAO;

@Service
public class ReplyServiceImpl implements ReplyService {

  @Inject
  private ReplyDAO replyDAO;

  @Inject
  private BoardDAO boardDAO;

  @Transactional
  @Override
  public void addReply(ReplyVO vo) throws Exception {
	  //»õ ´ñ±Û Ãß°¡ ½Ã, replycnt Ä®·³ °ª +1
	  replyDAO.create(vo);
	  boardDAO.updateReplyCnt(vo.getBno(), 1);
  }
  
  @Transactional
  @Override
  public void removeReply(Integer rno) throws Exception {
	  //´ñ±Û »èÁ¦ ½Ã, replycnt Ä®·³ °ª -1 
	  int bno = replyDAO.getBno(rno);
	  replyDAO.delete(rno);
	  boardDAO.updateReplyCnt(bno, -1);
  }  
  @Override
  public List<ReplyVO> listReply(Integer bno) throws Exception {
	  return replyDAO.list(bno);
  }

  @Override
  public void modifyReply(ReplyVO vo) throws Exception {
	  replyDAO.update(vo);
  }

  @Override
  public List<ReplyVO> listReplyPage(Integer bno, Criteria cri) throws Exception {
	  return replyDAO.listPage(bno, cri);
  }

  @Override
  public int count(Integer bno) throws Exception {
	  return replyDAO.count(bno);
  }

}
