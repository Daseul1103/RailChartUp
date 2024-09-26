package kr.co.wizbrain.apRailroad.comm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.wizbrain.apRailroad.user.vo.UserVO;

public class FileManager {

//	public static final String fileName = "tmp.txt";
//	public static final String fileDir = "C:\\terrahubF\\tmp";
//	public static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
//
//	// 파일 생성
//	public File createFile(UserVO uvo) {
//		File file = null;
//		File folder;
//		try {
//			//디렉토리 미 존재시 디렉토리 생성 후 파일 생성
//			folder = new File(fileDir);
//			folder.mkdirs();
//			
//			file = new File(fileDir + "\\" + fileName);
//			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
//			if (file.isFile() && file.canWrite()) {
//				// id
//				bufferedWriter.write("ID:"+uvo.getUserId());
//				bufferedWriter.newLine();
//				//pw
//				bufferedWriter.write("PASSWORD:"+uvo.getUserPw());
//				bufferedWriter.newLine();
//				//시험코드
//				bufferedWriter.write("TESTCODE:"+uvo.getTestCode());
//				bufferedWriter.newLine();
//				//시험일정
//				bufferedWriter.write("STARTDATE:"+uvo.getStartDate());
//				bufferedWriter.newLine();
//				
//				bufferedWriter.write("ENDDATE:"+uvo.getEndDate());
//				bufferedWriter.close();
//			}
//
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return file;
//	}
//	
//	//로그아웃 및 세션 종료시 인식
//	public void deleteFileInit() {
//		deleteDirFile(fileDir);
//	}
//	
//	// 디렉토리 및 파일 삭제
//	public void deleteDirFile(String path) {
//		File folder = new File(path);
//		try {
//			if (folder.exists()) {
//				File[] folder_list = folder.listFiles(); // 파일리스트 얻어오기
//
//				for (int i = 0; i < folder_list.length; i++) {
//					if (folder_list[i].isFile()) {
//						folder_list[i].delete();
//						System.out.println("파일이 삭제되었습니다.");
//					} else {
//						deleteDirFile(folder_list[i].getPath()); // 재귀함수호출
//						System.out.println("폴더가 삭제되었습니다.");
//					}
//					folder_list[i].delete();
//				}
//				folder.delete(); // 폴더 삭제
//			}
//		} catch (Exception e) {
//			e.getStackTrace();
//		}
//	}
}
