function checkImageType(fileName){
	
	var pattern = /jpg|gif|png|jpeg/i;
	
	return fileName.match(pattern);

}

//썸네일 템플릿에 필요한 객체 생성
function getFileInfo(fullName){
		
	var fileName,  //경로, UUID 제외된 파일의 이름
	imgsrc, //썸네일, 파일 이미지 경로
	getLink; //화면에서 원본 파일 볼 수 있는 경로
	
	var fileLink;
	
	if(checkImageType(fullName)){//썸네일 이미지, 다운로드, 원본 이미지 파일의 경로 계산
		imgsrc = "/displayFile?fileName="+fullName;
		fileLink = fullName.substr(14);
		
		var front = fullName.substr(0,12); // /2015/07/01/ 
		var end = fullName.substr(14);
		
		getLink = "/displayFile?fileName="+front + end;
		
	}else{
		imgsrc ="/resources/dist/img/file.png";
		fileLink = fullName.substr(12);
		getLink = "/displayFile?fileName="+fullName;
	}
	fileName = fileLink.substr(fileLink.indexOf("_")+1);
	
	return  {fileName:fileName, imgsrc:imgsrc, getLink:getLink, fullName:fullName};
	
}


