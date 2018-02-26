package com.javalec.persistence;

import java.util.List;

import com.javalec.domain.BoardVO;
import com.javalec.domain.Criteria;
import com.javalec.domain.SearchCriteria;

public interface BoardDAO {

  public void create(BoardVO vo) throws Exception;

  public BoardVO read(Integer bno) throws Exception;

  public void update(BoardVO vo) throws Exception;

  public void delete(Integer bno) throws Exception;

  public List<BoardVO> listAll() throws Exception; //총 데이터 반환

  public List<BoardVO> listPage(int page) throws Exception; //현재 페이지에 해당하는 10개 데이터 반환

  public List<BoardVO> listCriteria(Criteria cri) throws Exception; //x 페이지에 설정한 y개 데이터 반환

  public int countPaging(Criteria cri) throws Exception; //totalCount 반환
  
  //use for dynamic sql
  public List<BoardVO> listSearch(SearchCriteria cri)throws Exception;
  
  public int listSearchCount(SearchCriteria cri)throws Exception;

  public void updateReplyCnt(Integer bno, int amount)throws Exception;
  
  public void updateViewCnt(Integer bno)throws Exception;
  
}
