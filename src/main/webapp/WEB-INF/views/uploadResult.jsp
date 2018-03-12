<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page session="false"%>
<!-- <iframe> 내에서 동작하기 위해 작성된 페이지
	: 화면 구성않고 <script> 사용해 자신이 속한 화면의 바깥쪽 parent(부모창)의 addFilrPath()를 호출 -->
<script>

var result = '${savedName}';

parent.addFilePath(result);

</script>

