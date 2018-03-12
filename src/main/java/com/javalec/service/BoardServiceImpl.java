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
	  dao.create(board); //1. 게시물 등록
    
	  String[] files = board.getFiles(); //2. 첨부파일의 이름 배열
    
	  if(files == null) { return; } 
    
	  for (String fileName : files) { //3. 각 파일 이름을 db에 추가
		  dao.addAttach(fileName);
	  }   
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

  //첨부파일 존재 시, 게시물 수정하려면
  //원 게시물 수정 + 기존 첨부파일 목록 삭제 + 새 첨부파일 정보 입력
  //3가지 작업이 이뤄지기에 트랜잭션으로 처리함
  @Transactional
  @Override
  public void modify(BoardVO board) throws Exception {
    dao.update(board);
    
    Integer bno = board.getBno();
    
    dao.deleteAttach(bno);
    
    String[] files = board.getFiles();
    
    if(files == null) { return; } 
    
    for (String fileName : files) {
      dao.replaceAttach(fileName, bno);
    }
  }

  @Transactional
  @Override
  public void remove(Integer bno) throws Exception {
	dao.deleteAttach(bno); //첨부파일 삭제
    dao.delete(bno); //게시물 삭제
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


  @Override
  public List<String> getAttach(Integer bno) throws Exception {
    
    return dao.getAttach(bno);
  }   
}
