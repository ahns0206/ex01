package com.javalec.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

//파일 업로드용 클래스
public class UploadFileUtils {

  private static final Logger logger = 
      LoggerFactory.getLogger(UploadFileUtils.class);

//  public static String uploadFile(String uploadPath, 
//      String originalName, 
//      byte[] fileData)throws Exception{
//    
//    return null;
//  }
//  

  //폴더 생성 및 파일 저장
  public static String uploadFile(String uploadPath, //파일의 저장경로
                              String originalName, //원본 파일의 이름
                              byte[] fileData //파일 데이터
                              )throws Exception{
    
    UUID uid = UUID.randomUUID(); //1. 고유한 값 생성
    
    String savedName = uid.toString() +"_"+originalName; //2. 업로드 파일 이름 생성
    
    String savedPath = calcPath(uploadPath); //3. 파일이 저장될 경로 계산 (파일이 저장될 '/년/월/일'정보 생성 & 업로드 기본경로와 '/년/월/일' 폴더 생성)
    
    File target = new File(uploadPath +savedPath,savedName); //4. 기본경로+폴더경로+파일 이름으로 파일 저장
    
    FileCopyUtils.copy(fileData, target); //원본 파일을 저장
    
    String formatName = originalName.substring(originalName.lastIndexOf(".")+1); //원본 파일의 확장자
    
    String uploadedFileName = null;
    
    if(MediaUtils.getMediaType(formatName) != null){//이미지 타입인 경우
      uploadedFileName = makeThumbnail(uploadPath, savedPath, savedName); //썸네일 생성
    }else{ //이미지 타입의 파일이 아닌 경우
      uploadedFileName = makeIcon(uploadPath, savedPath, savedName); //경로 처리 문자열
    }
    
    return uploadedFileName;
    
  }
  
  private static  String makeIcon(String uploadPath, 
      String path, 
      String fileName)throws Exception{

    String iconName = 
        uploadPath + path + File.separator+ fileName;
    
    return iconName.substring(
        uploadPath.length()).replace(File.separatorChar, '/');
  }
  
  //썸네일 이미지 생성
  private static  String makeThumbnail(
              String uploadPath, //기본 경로
              String path, //'/년/월/일' 폴더
              String fileName //현재 업로드 된 파일 이름
              )throws Exception{
            
    BufferedImage sourceImg = //실제 이미지 아닌 메모리상 이미지를 의미
        ImageIO.read(new File(uploadPath + path, fileName));
    
    BufferedImage destImg = 
        Scalr.resize(sourceImg, 
            Scalr.Method.AUTOMATIC, 
            Scalr.Mode.FIT_TO_HEIGHT,100); //FIT_TO_HEIGHT: 썸네일 파일 높이를 100px로 동일하게 만드는 역할
    
    String thumbnailName = 
        uploadPath + path + File.separator +"s_"+ fileName;
    //s_로 시작하면 썸네일 이미지, s_로 안시작하면 원본 파일
    
    File newFile = new File(thumbnailName);
    String formatName = 
        fileName.substring(fileName.lastIndexOf(".")+1);
    
    
    ImageIO.write(destImg, formatName.toUpperCase(), newFile);
    return thumbnailName.substring(
        uploadPath.length()).replace(File.separatorChar, '/'); //브라우저에서 \문자는 정상경로로 인식 안되기에 /로 치환
  } 
  
  //파일이 저장될 '/년/월/일'정보 생성 후, '/년/월/일' 폴더 생성
  private static String calcPath(String uploadPath){
    
    Calendar cal = Calendar.getInstance();
    
    String yearPath = File.separator+cal.get(Calendar.YEAR);
    
    String monthPath = yearPath + 
        File.separator + 
        new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);

    String datePath = monthPath + 
        File.separator + 
        new DecimalFormat("00").format(cal.get(Calendar.DATE));
    
    makeDir(uploadPath, yearPath,monthPath,datePath);
    
    logger.info(datePath);
    
    return datePath;
  }
  
  
  private static void makeDir(String uploadPath, String... paths){
    
    if(new File(paths[paths.length-1]).exists()){
      return;
    }
    
    for (String path : paths) {
      
      File dirPath = new File(uploadPath + path);
      
      if(! dirPath.exists() ){
        dirPath.mkdir();
      } 
    }
  }
  
  
}
