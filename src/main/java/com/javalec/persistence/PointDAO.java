package com.javalec.persistence;

//메시지 전송이나 확인에 따라 달라지는 사용자의 활동에 따른 처리
public interface PointDAO {

	public void updatePoint(String uid,int point)throws Exception;
	
}

