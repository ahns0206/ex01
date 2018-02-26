<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<!-- modDiv의 CSS처리 -->
<style>
#modDiv {
	width: 300px;
	height: 100px;
	background-color: gray;
	position: absolute;
	top: 50%;
	left: 50%;
	margin-top: -50px;
	margin-left: -150px;
	padding: 10px;
	z-index: 1000;
}

.pagination {
  width: 100%;
}

.pagination li{
  list-style: none;
  float: left; 
  padding: 3px; 
  border: 1px solid blue;
  margin:3px;  
}

.pagination li a{
  margin: 3px;
  text-decoration: none;  
}

</style>
</head>

<body>

	<!-- 수정 삭제 위한 div로  display: none 시 화면에서 안보이게 됨-->
	<div id='modDiv' style="display: none;">
		<div class='modal-title'></div>
		<div>
			<input type='text' id='replytext'>
		</div>
		<div>
			<button type="button" id="replyModBtn">Modify</button>
			<button type="button" id="replyDelBtn">DELETE</button>
			<button type="button" id='closeBtn'>Close</button>
		</div>
	</div>

	<h2>Ajax Test Page</h2>
	<!-- 댓글 등록 화면 -->
	<div>
		<div>
			REPLYER <input type='text' name='replyer' id='newReplyWriter'>
		</div>
		<div>
			REPLY TEXT <input type='text' name='replytext' id='newReplyText'>
		</div>
		<button id="replyAddBtn">ADD REPLY</button>
	</div>
	
	<!-- 댓글 출력 공간 처리 -->
	<ul id="replies"></ul>
	
	<!-- 댓글 페이징 관련 출력 공간 처리 -->
	<ul class='pagination'></ul>	


	<!-- jQuery 2.1.4 -->
	<script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
	
	<!-- 전체 댓글 목록 출력 -->
	<script>
		var bno = 123239;
		
		getPageList(1);

		//전체 목록에 대한 함수 처리
		function getAllList() {
			$.getJSON("/replies/all/" + bno, function(data) {
				//console.log(data.length);

				var str = "";

				//댓글 수정 버튼 추가
				$(data).each(function() {
					//li는 댓글의 각 항목을 의미하며 Ajax통신 후 생성되는 요소임
					str += "<li data-rno='"+this.rno+"' class='replyLi'>"
					+ this.rno
					+ ":"
					+ this.replytext
					+ "<button>MOD</button></li>";});
				$("#replies").html(str);
				});
		}

		//ADD REPLY 버튼 사용자가 클릭 시, 이벤트 처리 (jQuery의 이벤트는 아직 존재하지 않는 요소에도 이벤트를 위임해줌)
		$("#replyAddBtn").on("click", function() {

			var replyer = $("#newReplyWriter").val();
			var replytext = $("#newReplyText").val();

			$.ajax({
				type : 'post',
				url : '/replies',
				headers : {
					"Content-Type" : "application/json",
					"X-HTTP-Method-Override" : "POST"
				},
				dataType : 'text',
				data : JSON.stringify({
					bno : bno,
					replyer : replyer,
					replytext : replytext
				}),
				success : function(result) {

					if (result == 'SUCCESS') {

						alert("등록 되었습니다.");
						getAllList(); //댓글 등록 후, 전체 댓글 목록 갱신

					}
				}
			});
		});

		//항목 선택 시, 해당 항목의 댓글 번호, 내용을 <div>이용하여 출력
		$("#replies").on("click", ".replyLi button", function() {

			var reply = $(this).parent();

			var rno = reply.attr("data-rno");
			var replytext = reply.text();

			$(".modal-title").html(rno);
			$("#replytext").val(replytext);
			$("#modDiv").show("slow");
			//MOD 버튼 클릭 시, 댓글의 번호와 내용을 <div>이용해 출력
		});

		//DELETE 버튼 사용자가 클릭 시, 이벤트 처리
		$("#replyDelBtn").on("click", function() {

			var rno = $(".modal-title").html();
			var replytext = $("#replytext").val();

			$.ajax({
				type : 'delete',
				url : '/replies/' + rno,
				headers : {
					"Content-Type" : "application/json",
					"X-HTTP-Method-Override" : "DELETE"
				},
				dataType : 'text',
				success : function(result) {
					console.log("result: " + result);
					if (result == 'SUCCESS') {
						alert("삭제 되었습니다.");
						$("#modDiv").hide("slow"); //출력되연 <div> 감추기
						getAllList(); //전체 목록 가져오기
					}
				}
			});
		});
		
		//Modify 버튼 사용자가 클릭 시, 이벤트 처리
		$("#replyModBtn").on("click",function(){
			  
			  var rno = $(".modal-title").html();
			  var replytext = $("#replytext").val();
			  
			  $.ajax({
					type:'put',
					url:'/replies/'+rno,
					headers: { 
					      "Content-Type": "application/json",
					      "X-HTTP-Method-Override": "PUT" },
					data:JSON.stringify({replytext:replytext}), 
					dataType:'text', 
					success:function(result){
						console.log("result: " + result);
						if(result == 'SUCCESS'){
							alert("수정 되었습니다.");
							 $("#modDiv").hide("slow");
							//getAllList();
							 getPageList(replyPage);
						}
				}});
		});		
		
		//페이지 번호 입력받고, jQuery의 getJSON()을 이용하여 가져온 데이터를 처리함
		function getPageList(page){
			
		  $.getJSON("/replies/"+bno+"/"+page , function(data){
			  
			  console.log(data.list.length);
			  
			  var str ="";
			  
			  $(data.list).each(function(){
				  str+= "<li data-rno='"+this.rno+"' class='replyLi'>" 
				  +this.rno+":"+ this.replytext+
				  "<button>MOD</button></li>";
			  });
			  
			  $("#replies").html(str);
			  
			  printPaging(data.pageMaker);
			  //서버에서 전송되된 댓글 목록인 list 데이터 이용하여 댓글 내용을 표시하고, 페이징 처리위한 pageMaker 데이터 이용
		  });
	  }		
		
		//js객체인 pageMaker로 화면에 페이지 번호 출력		  
		function printPaging(pageMaker){
			
			var str = "";
			
			if(pageMaker.prev){
				str += "<li><a href='"+(pageMaker.startPage-1)+"'> << </a></li>";
			}
			
			for(var i=pageMaker.startPage, len = pageMaker.endPage; i <= len; i++){				
					var strClass= pageMaker.cri.page == i?'class=active':'';
				  str += "<li "+strClass+"><a href='"+i+"'>"+i+"</a></li>";
			}
			
			if(pageMaker.next){
				str += "<li><a href='"+(pageMaker.endPage + 1)+"'> >> </a></li>";
			}
			$('.pagination').html(str);				
		}
		
		var replyPage = 1;
		
		//페이지 번호 클릭 시, 이벤트 처리
		$(".pagination").on("click", "li a", function(event){
			//<a>태그 내용 중 페이지 번호를 추출하여 Ajax 호출 처리함
			
			event.preventDefault(); //<a href="">태그의 기본 동작인 페이지 전환을 막음
			replyPage = $(this).attr("href"); //현재 클릭된 페이지 정보 추출
			getPageList(replyPage);
			
		});
	  		
	</script>

</body>
</html>

