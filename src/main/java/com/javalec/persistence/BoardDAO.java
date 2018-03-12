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
  
  //첨부파일 생성
  public void addAttach(String fullName)throws Exception;
  
  //게시물 번호에 속하는 모든 첨부파일 조회
  public List<String> getAttach(Integer bno)throws Exception;  
  
  //게시물 번호에 속하는 모든 첨부파일 삭제
  public void deleteAttach(Integer bno)throws Exception;
  
  //수정된 상태의 파일 이름과 기등록 게시물 번호로 tbl_attach내 새 첨부파일 정보 생성
  public void replaceAttach(String fullName, Integer bno)throws Exception;
  
}
