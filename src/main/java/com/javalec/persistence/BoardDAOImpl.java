package com.javalec.persistence;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.javalec.domain.BoardVO;
import com.javalec.domain.Criteria;
import com.javalec.domain.SearchCriteria;

//SqlSessionTemplate를 이용해서 원하는 코드를 호출
//해당 클래스 작성 후 반드시 스프링의 빈으로 제대로 등록되었는지 확인(root-context.xml > Bean Graph 탭 이용)
@Repository
public class BoardDAOImpl implements BoardDAO {

  @Inject
  private SqlSession session;

  private static String namespace = "com.javalec.mapper.BoardMapper";
  //공통으로 사용하는 XML Mapper의 namespace를 지정
  
  @Override
  public void create(BoardVO vo) throws Exception {
    session.insert(namespace + ".create", vo);
  }

  @Override
  public BoardVO read(Integer bno) throws Exception {
    return session.selectOne(namespace + ".read", bno);
  }

  @Override
  public void update(BoardVO vo) throws Exception {
    session.update(namespace + ".update", vo);
  }

  @Override
  public void delete(Integer bno) throws Exception {
    session.delete(namespace + ".delete", bno);
  }

  @Override
  public List<BoardVO> listAll() throws Exception {
    return session.selectList(namespace + ".listAll");
  }

  @Override //문제: 한 페이지에서 보여지는 데이터가 10개가 아니면 해당 메소드 구현이 달라져야 하며, 매번 페이지 계산을 해야 함
  public List<BoardVO> listPage(int page) throws Exception {

    if (page <= 0) {
      page = 1;
    }

    page = (page - 1) * 10;

    return session.selectList(namespace + ".listPage", page);
  }

  @Override// Criteria객체를 파라미터로 전달받고, 필요한 메소드를 호출한 결과를 사용함
  public List<BoardVO> listCriteria(Criteria cri) throws Exception {

    return session.selectList(namespace + ".listCriteria", cri);
  }

  @Override
  public int countPaging(Criteria cri) throws Exception {

    return session.selectOne(namespace + ".countPaging", cri);
  }

  @Override
  public List<BoardVO> listSearch(SearchCriteria cri) throws Exception {

    return session.selectList(namespace + ".listSearch", cri);
  }

  @Override
  public int listSearchCount(SearchCriteria cri) throws Exception {

    return session.selectOne(namespace + ".listSearchCount", cri);
  }

  @Override
  public void updateReplyCnt(Integer bno, int amount) throws Exception {

    Map<String, Object> paramMap = new HashMap<String, Object>();

    paramMap.put("bno", bno);
    paramMap.put("amount", amount);

    session.update(namespace + ".updateReplyCnt", paramMap);
  }

  @Override
  public void updateViewCnt(Integer bno) throws Exception {
    
    session.update(namespace+".updateViewCnt", bno);
    
  }
}
