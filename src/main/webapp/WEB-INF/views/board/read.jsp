<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="../include/header.jsp"%>

<!-- Main content -->
<section class="content">
	<div class="row">
		<!-- left column -->
		<div class="col-md-12">
			<!-- general form elements -->
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">READ BOARD</h3>
				</div>
				<!-- /.box-header -->
				
<!-- 이후 수정, 삭제 작업에서 bno가 필요하기에 <form>태그에서 <input type='hidden'> 이용해 bno값 처리함 -->
<form role="form" method="post">
	<input type='hidden' name='bno' value="${boardVO.bno}">
</form>

<div class="box-body">
	<div class="form-group">
		<label for="exampleInputEmail1">Title</label> <input type="text"
			name='title' class="form-control" value="${boardVO.title}"
			readonly="readonly">
	</div>
	<div class="form-group">
		<label for="exampleInputPassword1">Content</label>
		<textarea class="form-control" name="content" rows="3"
			readonly="readonly">${boardVO.content}</textarea>
	</div>
	<div class="form-group">
		<label for="exampleInputEmail1">Writer</label> <input type="text"
			name="writer" class="form-control" value="${boardVO.writer}"
			readonly="readonly">
	</div>
</div>
<!-- /.box-body -->

<div class="box-footer">
	<button type="submit" class="btn btn-warning">MODIFY</button>
	<button type="submit" class="btn btn-danger">REMOVE</button>
	<button type="submit" class="btn btn-primary">LIST ALL</button>
</div>

<script>
	//jQuery 사용
	$(document).ready(function(){
		
		var formObj = $("form[role='form']");//formObj는 <form> 태그를 의미
		
		console.log(formObj);

		//$(".btn-danger")의 이벤트 처리: 삭제를 위해 <form>태그의 action을 "/board/remove"로 되게 처리 및 전송
		$(".btn-danger").on("click", function(){
			formObj.attr("action", "/board/remove");
			formObj.submit();
		});
		
		//$(".btn-warning")의 이벤트 처리: 수정 페이지로 이동하도록 위의 <form>태그 속성을 수정 및 전송
		$(".btn-warning").on("click", function(){
			formObj.attr("action", "/board/modify");
			formObj.attr("method", "get");		
			formObj.submit();
		});
		
		//$(".btn-primary")의 이벤트 처리: 다시 목록으로 가는 작업은 현재 화면의 링크를 "/board/listAll"로 처리 
		$(".btn-primary").on("click", function(){
			self.location = "/board/listAll";
		});
		
	});
</script>

			</div>
			<!-- /.box -->
		</div>
		<!--/.col (left) -->

	</div>
	<!-- /.row -->
</section>
<!-- /.content -->
</div>
<!-- /.content-wrapper -->

<%@include file="../include/footer.jsp"%>
