<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="../include/header.jsp"%>

<!-- fileDrop css -->
<style>
.fileDrop {
  width: 80%;
  height: 100px;
  border: 1px dotted gray;
  background-color: lightslategrey;
  margin: auto;
  
}
</style>

<!-- Main content -->
<section class="content">
	<div class="row">
		<!-- left column -->
		<div class="col-md-12">
			<!-- general form elements -->
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">REGISTER BOARD</h3>
				</div>
				<!-- /.box-header -->

				<form role="form" method="post">
					<div class="box-body">
						<div class="form-group">
							<label for="exampleInputEmail1">Title</label> <input type="text"
								name='title' class="form-control" placeholder="Enter Title">
						</div>
						<div class="form-group">
							<label for="exampleInputPassword1">Content</label>
							<textarea class="form-control" name="content" rows="3"
								placeholder="Enter ..."></textarea>
						</div>
						<div class="form-group">
							<label for="exampleInputEmail1">Writer</label> <input type="text"
								name="writer" class="form-control" placeholder="Enter Writer">
						</div>
						<!-- 첨부파일 추가 영역 -->
						<div class="form-group">
							<label for="exampleInputEmail1">File DROP Here</label>
							<div class="fileDrop"></div>
						</div>
					</div>
					<!-- /.box-body -->

					<div class="box-footer">
						<div>
							<hr>
						</div>
						
						<!-- 업로드 된 파일이 보여질 영영 -->
						<ul class="mailbox-attachments clearfix uploadedList">
						</ul>
					
						<button type="submit" class="btn btn-primary">Submit</button>
					</div>
				</form>


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


<script type="text/javascript" src="/resources/js/upload.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>

<!-- 등록 화면의 경우 썸네일에 삭제 버튼 있음 -->
<!-- handlebars의 템플릿으로 첨부파일 보여주는 html 코드 대체 -->
<!-- imgsrc : 이미지 파일인 경우 썸네일 파일의 경로, 이미지 파일 아닌 경우 단순 파일모양 이미지(file.png) 경로 -->
<script id="template" type="text/x-handlebars-template">
<li>
  <span class="mailbox-attachment-icon has-img"><img src="{{imgsrc}}" alt="Attachment"></span>
  <div class="mailbox-attachment-info">
	<a href="{{getLink}}" class="mailbox-attachment-name">{{fileName}}</a>
	<a href="{{fullName}}" 
     class="btn btn-default btn-xs pull-right delbtn"><i class="fa fa-fw fa-remove"></i></a>
	</span>
  </div>
</li>                
</script>    

<script>

var template = Handlebars.compile($("#template").html());

$(".fileDrop").on("dragenter dragover", function(event){
	event.preventDefault();
});


$(".fileDrop").on("drop", function(event){
	event.preventDefault();
	
	var files = event.originalEvent.dataTransfer.files;
	
	var file = files[0];

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
			  
			  var fileInfo = getFileInfo(data); //템플릿에 필요한 객체 생성
			  
			  var html = template(fileInfo); //템플릿을 적용한 html 생성
			  
			  $(".uploadedList").append(html);
		  }
		});	
});

//submit 시, 서버에 사용자 업로드 파일의 정보를 같이 전송 (업로드 된 파일의 이름을 <form>태그의 내부로 포함시켜 전송)
$("#registerForm").submit(function(event){
	event.preventDefault(); //submit 기본 동작 막음
	
	var that = $(this);
	var str ="";
	
	//현재까지 업로드 된 파일들을 <form> 태그의 <input type='hidden'>로 추가
	//<input type='hidden' name='files[0]' value="/2018/03/13/s_~~">
	$(".uploadedList .delbtn").each(function(index){
		 str += "<input type='hidden' name='files["+index+"]' value='"+$(this).attr("href") +"'> ";
	});
	
	that.append(str);

	that.get(0).submit(); //get(0): 순수한 DOM 객체 추출
});

</script>

<%@include file="../include/footer.jsp"%>
