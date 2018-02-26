package com.javalec.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.javalec.domain.BoardVO;
import com.javalec.domain.Criteria;
import com.javalec.domain.SearchCriteria;
import com.javalec.persistence.BoardDAO;

@Service //스프링의 빈으로 인식되기 위함
public class BoardServiceImpl implements BoardService {

  @Inject
  private BoardDAO dao;

  @Override
  public void regist(BoardVO board) throws Exception {
    dao.create(board);
  }

//@Override
//public BoardVO read(Integer bno) throws Exception {
//  return dao.read(bno);
//}


  //트랜잭션 격리수준은 READ_COMMITTED로 커밋하지 않은 데이터는 다른 연결이 볼 수 없음
  @Transactional(isolation=Isolation.READ_COMMITTED)
  @Override
  public BoardVO read(Integer bno) throws Exception {
	  dao.updateViewCnt(bno);
	  return dao.read(bno);
  }

  @Override
  public void modify(BoardVO board) throws Exception {
    dao.update(board);
  }

  @Override
  public void remove(Integer bno) throws Exception {
    dao.delete(bno);
  }

  @Override
  public List<BoardVO> listAll() throws Exception {
    return dao.listAll();
  }

  @Override
  public List<BoardVO> listCriteria(Criteria cri) throws Exception {

    return dao.listCriteria(cri);
  }

  @Override
  public int listCountCriteria(Criteria cri) throws Exception {

    return dao.countPaging(cri);
  }

  @Override
  public List<BoardVO> listSearchCriteria(SearchCriteria cri) throws Exception {

    return dao.listSearch(cri);
  }

  @Override
  public int listSearchCount(SearchCriteria cri) throws Exception {

    return dao.listSearchCount(cri);
  }

}
