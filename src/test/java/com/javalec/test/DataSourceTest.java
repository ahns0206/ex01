package com.javalec.test;

import java.sql.Connection;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
//locations 속성 경로의 xml파일을 이용해서 스프링 로딩되도록 하는 부분
public class DataSourceTest {

	@Inject //스프링이 root-context.xml 설정 보고 bean 객체를 생성해서 주입해줌
	private DataSource ds;
	
	@Test
	public void testConection()throws Exception{
		
		try(Connection con = ds.getConnection()){
			System.out.println(">> Connection obj : " + con); //Connection 객체 생성 확인
		}catch(Exception e){
			System.out.println(">> ERROR!!");
			e.printStackTrace();
		}
	}
}
