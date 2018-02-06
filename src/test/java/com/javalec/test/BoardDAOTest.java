package com.javalec.test;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import com.javalec.domain.BoardVO;
import com.javalec.domain.Criteria;
import com.javalec.domain.SearchCriteria;
import com.javalec.persistence.BoardDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class BoardDAOTest {

  @Inject
  private BoardDAO dao;

  private static Logger logger = LoggerFactory.getLogger(BoardDAOTest.class);

  //@Test 앞에 주석"//"를 하나씩 풀면서 DB상 변화 확인
  //@Test
  public void testCreate() throws Exception {

    BoardVO board = new BoardVO();
    board.setTitle("새로운 글을 넣습니다.");
    board.setContent("새로운 글을 넣습니다.");
    board.setWriter("ahns0206");
    dao.create(board);
  }

  //@Test
  public void testRead() throws Exception {

	  logger.info(dao.read(8).toString()); //bno
  }

  //@Test
  public void testUpdate() throws Exception {

    BoardVO board = new BoardVO();
    board.setBno(1); //bno
    board.setTitle("수정된 글입니다.");
    board.setContent("수정 테스트 ");
    dao.update(board);
  }

  //@Test
  public void testDelete() throws Exception {

    dao.delete(8); //bno
  }

  //@Test
  public void testListAll() throws Exception {

    logger.info(dao.listAll().toString());

  }

  //@Test
  public void testListPage() throws Exception {

    int page = 3;

    List<BoardVO> list = dao.listPage(page);

    //가장 최근 데이터 3페이지 분량인 10개가 출력되어야 함 (최근 20번째 데이터부터 10개)
    for (BoardVO boardVO : list) {
      logger.info(boardVO.getBno() + ":" + boardVO.getTitle());
    }
  }

  //@Test
  public void testListCriteria() throws Exception {

    Criteria cri = new Criteria();
    cri.setPage(2);
    cri.setPerPageNum(20);
    //하기 SQL문과 동일함
    //select * from tbl_board where bno>0 order by bno desc limit 20, 20

    List<BoardVO> list = dao.listCriteria(cri);

    for (BoardVO boardVO : list) {
      logger.info(boardVO.getBno() + ":" + boardVO.getTitle());
    }
  }

  //@Test
  public void testURI() throws Exception {

	//UriComponents클래스는 Builder패턴으로 path나 query에 해당하는 문자열을 추가해서 원하는 URI를 생성할 수 있게 함
    UriComponents uriComponents = UriComponentsBuilder.newInstance().path("/board/read").queryParam("bno", 12)
        .queryParam("perPageNum", 20).build();

    logger.info("/board/read?bno=12&perPageNum=20");
    logger.info(uriComponents.toString());

  }

  @Test
  public void testURI2() throws Exception {

	//UriComponents클래스는 특정 URI를 먼저 지정하고 작업 가능하게 함
    UriComponents uriComponents = UriComponentsBuilder.newInstance().path("/{module}/{page}").queryParam("bno", 12)
        .queryParam("perPageNum", 20).build().expand("board", "read").encode();
    //{module}경로를 board로, {page}경로를 read로 변경

    logger.info("/board/read?bno=12&perPageNum=20");
    logger.info(uriComponents.toString());
  }

  //@Test
  public void testDynamic1() throws Exception {

    SearchCriteria cri = new SearchCriteria();
    cri.setPage(1);
    cri.setKeyword("글");
    cri.setSearchType("t");

    logger.info("=====================================");

    List<BoardVO> list = dao.listSearch(cri);

    for (BoardVO boardVO : list) {
      logger.info(boardVO.getBno() + ": " + boardVO.getTitle());
    }

    logger.info("=====================================");

    logger.info("COUNT: " + dao.listSearchCount(cri));
  } //INFO : jdbc.sqltiming - select count(bno) from tbl_board where bno > 0 and title like CONCAT('%', '글', '%') 
    //동적으로 위와 같이 SQL문이 실행된 것을 확인

}
