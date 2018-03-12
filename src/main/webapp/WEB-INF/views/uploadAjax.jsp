<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
  "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<style>
.fileDrop {
	width: 100%;
	height: 200px;
	border: 1px dotted blue;
}

small {
	margin-left: 3px;
	font-weight: bold;
	color: gray;
}
</style>
</head>
<body>

	<h3>Ajax File Upload</h3>
	<div class='fileDrop'></div>

	<div class='uploadedList'></div>

	<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
	<script>
		$(".fileDrop").on("dragenter dragover", function(event) {
			event.preventDefault();
			//dragenter dragover시 이벤트 처리 제한해 브라우저 상 파일을 끌어다 놓아도 아무런 변화가 없음
		});
		
		$(".fileDrop").on("drop", function(event){
			event.preventDefault();
			
			var files = event.originalEvent.dataTransfer.files; 
			//event.originalEvent : 이벤트와 같이 전달된 데이터를 의미
			//dataTransfer.files : 전달된 데이터 내 포함된 파일 데이터를 찾음
			
			var file = files[0];

			//console.log(file);
			
			var formData = new FormData(); //<form>태그로 만든 데이터의 전송방식과 동일하게 파일 데이터를 전송 할 수 있음
			
			formData.append("file", file); //file 이름으로 끌어다 놓은 파일 객체를 추가(데이터이름, 데이터값)
			
			//이미지 파일의 경우 파일 업로드 시, 아래쪽에 썸네일 이미지가 출력 (첨부파일 삭제 기능 추가)
			//processData: false, contentType: false 옵션은 데이터 전송을 <form>을 이용하는 파일 업로드와 동일하게 해주는 중요한 역할
			$.ajax({
				  url: '/uploadAjax',
				  data: formData,
				  dataType:'text',
				  processData: false,
				  contentType: false,
				  type: 'POST',
				  success: function(data){
					  
					  var str ="";
					  
					  if(checkImageType(data)){
						  str ="<div><a href=displayFile?fileName="+getImageLink(data)+">"
								  +"<img src='displayFile?fileName="+data+"'/>"
								  +"</a><small data-src="+data+">X</small></div>";
					  }else{
						  str = "<div><a href='displayFile?fileName="+data+"'>" 
								  + getOriginalName(data)+"</a>"
								  +"<small data-src="+data+">X</small></div></div>";
					  }
					  
					  $(".uploadedList").append(str);
				  }
				});	
		});


		//삭제할 파일의 이름을 컨트롤러에 전달
		$(".uploadedList").on("click", "small", function(event){
			var that = $(this);
			
			//사용자가 <small>태그로 처리된 x 클릭 시, data-src속성값으로 사용된 파일 이름 얻어와 post 호출
			$.ajax({
			   url:"deleteFile",
			   type:"post",
			   data: {fileName:$(this).attr("data-src")},
			   dataType:"text",
			   success:function(result){ //파일서버에서 파일이 정상적으로 삭제 시,
				   if(result == 'deleted'){
					   that.parent("div").remove(); //화면에서 첨부파일 보여주기 위한 div 삭제
				   }
			    }
		    });
		});
		
		
/*
$(".fileDrop").on("drop", function(event) {
	event.preventDefault();
	
	var files = event.originalEvent.dataTransfer.files;
	
	var file = files[0];

	//console.log(file);
	var formData = new FormData();
	
	formData.append("file", file);

	//이미지 파일의 경우 파일 업로드 시, 아래쪽에 썸네일 이미지가 출력
	$.ajax({
		  url: '/uploadAjax',
		  data: formData,
		  dataType:'text',
		  processData: false,
		  contentType: false,
		  type: 'POST',
		  success: function(data){
			  
			  var str ="";
			  
			  console.log(data);
			  console.log(checkImageType(data));
			  
			  if(checkImageType(data)){
				  str ="<div><a href='displayFile?fileName="+getImageLink(data)+"'>"
						  +"<img src='displayFile?fileName="+data+"'/></a>"
						  +data +"</div>";
			  }else{
				  str = "<div><a href='displayFile?fileName="+data+"'>" 
						  + getOriginalName(data)+"</a></div>";
			  }
			  
			  $(".uploadedList").append(str); //썸네일 이미지 클릭 시, 원본파일 조회하는 링크로 이동
		  }
		});			
});	 */

//파일 이름이 너무 길게 출력되는데 이를 축소하여 원 파일 이름만 추출
function getOriginalName(fileName){	

	if(checkImageType(fileName)){
		return;
	}
	
	var idx = fileName.indexOf("_") + 1 ;
	return fileName.substr(idx);
	
}

//이미지파일의 원본 파일을 찾기 위해 "/년/월/일s_~"인 파일 이름에서 "s_~"를 제거하여 링크 찾음
function getImageLink(fileName){
	
	if(!checkImageType(fileName)){
		return;
	}
	var front = fileName.substr(0,12); //"/년/월/일"경로 추출
	var end = fileName.substr(14); //파일이름 앞 "s_" 제거
	
	return front + end;
	
}




/* 		$(".fileDrop").on("drop", function(event) {
			event.preventDefault();
			
			var files = event.originalEvent.dataTransfer.files;
			
			var file = files[0];

			//console.log(file);
			var formData = new FormData();
			
			formData.append("file", file);
			
			$.ajax({
				  url: '/uploadAjax',
				  data: formData,
				  dataType:'text',
				  processData: false,
				  contentType: false,
				  type: 'POST',
				  success: function(data){
					 	
					  alert(data);
					 
				  }
				});
			
		}); */
		
	//전송받은 문자열이 이미지 파일인지 확인하는 작업
	function checkImageType(fileName){
		
		var pattern = /jpg|gif|png|jpeg/i; //i는 대,소문자 구분 없게 함
		
		return fileName.match(pattern);
		
	}
		
		
	</script>

</body>
</html>