package org.lhy.boardex001.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.lhy.boardex001.domain.BoardAttachVO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import lombok.extern.java.Log;
import net.coobird.thumbnailator.Thumbnailator;


@Controller
@Log
@RequestMapping("/upload")
public class UploadController {
	// 주소창에 sample/uploadAjax 검색하면 uploadAjax.jsp로 간다
		@GetMapping("/uploadAjax")
		public void uploadAjax() {}
		
		// produces는 responseEntity가 browser로 내보내는 tpye을 나타낸다
		// 여기서는 AttachFileDto객체로 된 List를 json type으로 내보낸다
		@PostMapping(value="/ajaxAction", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
		@ResponseBody
		public ResponseEntity<List<BoardAttachVO>> uploadAjaxPost(MultipartFile[] uploadFile) {

			List<BoardAttachVO> list = new ArrayList<BoardAttachVO>();
			// return할 list
			
			String uploadFolder = "d:\\upload";
			// upload할 위치 (수업시간에는 upload/temp였는데 upload로 바꼈음)
			
			String uploadFolderPath = getFolder();
			// getFolder()는 오늘 날짜를 문자열로 return. 즉, uploadFolderPath에는 2020\09\18가 들어간다
			
			File uploadPath = new File(uploadFolder, uploadFolderPath);
			// new File(String parent, String child)는 parent 폴더 안에 child 젤 밑에 파일 하나를 만든다? 
			// 즉 uploadPath(내가 파일 만들 위치)는 d://upload/2019/09/18이다. 여기에 파일을 만들겠다. 이런뜻
			
			if (uploadPath.exists() == false) {
				uploadPath.mkdirs();
			}
			// uploadPath.exists()는 d://upload/2019/09/18 이거다. 즉 이 경로가 없으면(폴더가 안 만들어져 있으면)
			// make directory

			for (MultipartFile multipartFile : uploadFile) {
				// form에서 보내주는 file이 multiple형태니까 걔를 받아서 MultipartFile의 객체를 만들 수 있다
				// 이거를 여기 for문에서는 form에서 uploadFile라는 이름으로 보내준 걸 multipartFile이라고 부르겠다
				BoardAttachVO attachVO = new BoardAttachVO();
				// 이 객체를 하나 만들겠다(domain에 만들어둠). 앞으로 이 객체를 '객체'라고 부르겠다
				
				String uploadFileName = multipartFile.getOriginalFilename();
				// 방금 받아온 파일의 이름을 String으로 저장
				
				// 이거는 IE설정. 무시해도 무방
				uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
				log.info("only file name: " + uploadFileName);
				
				attachVO.setFileName(uploadFileName);
				// 객체에 이름을 넣어줌
				
				UUID uuid = UUID.randomUUID();
				uploadFileName = uuid.toString() + "_" + uploadFileName;
				// 파일 이름이 중복되면 저장이 안되니까 이를 방지하기 
				//위해 랜덤의 숫자를 하나 만들어서 원래의 파일이름 앞에 붙여준다

				try {
					File saveFile = new File(uploadPath, uploadFileName);
					// 저장할 하고자 하는 파일이다(위에서 만든 경로[d://upload/2019/09/18] / [uuid+원래이름] 형태의 이름)
					multipartFile.transferTo(saveFile);
					// transferTo는 파일을 저장하라는 명령어다. 알맹이는 form에서 받아온 multipartFile이고
					// 경로와 이름은 위에서 만든 saveFile이다
					
					attachVO.setUuid(uuid.toString());
					attachVO.setUploadPath(uploadFolderPath);
					// 지금 저장하려는 파일의 정보를 객체에 담아준다. (이는 form에서 내가 업로드한 게 뭔지 보여주려고 저장하는거) 
					
					// 저장하는 파일이 이미지 파일인지 체크한다
					if (checkImageType(saveFile)) {
						
						attachVO.setFileType(true);
						// 객체에 이미지인지 여부를 넣어준다
						
						FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
						Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
						// 이거는 이미지 파일은 섬네일 이미지를 만들어주기 위해서 똑같은 경로에 / 파일이름에는 앞에 s_를 붙여서 섬네일 이미지를 만들어준다
						// 가로세로 100
						thumbnail.close();
						// 이거는 뭘까. 큰 의미 없음
					}

					// 내가 업로드 한 파일이 5개라고 가정하면 for가 5번 돌아가고
					// list에는 5개의 파일이 들어간 뒤 browser로 return해준다
					// 브라우저에서 업로드 누르면 내가 올린 파일이랑 정보랑 뜨는 이유가 여기서 list를 리턴해줘서
					list.add(attachVO);

				} catch (Exception e) {
					e.printStackTrace();
				}

			} // end for
			return new ResponseEntity<List<BoardAttachVO>>(list, HttpStatus.OK);
		}
		
		// 지금까지는 업로드 버튼을 누르면 일어나는 내용
		
		
		// 여기는 업로드 했을 때 그 파일이 이미지가 아닌 다른 파일이면 다운로드가 가능한데 그 다운로드에 해당하는 내용이다
		
		// form에서 down를 요청했을 때 오는곳
		// produces = MediaType.APPLICATION_OCTET_STREAM_VALUE 이거는 파일내용을 return해줄 때 적음
		@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
		@ResponseBody
		public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName) {
			
			Resource resource = new FileSystemResource("d:\\upload\\" + fileName);
			// 일단 Resource는 인터페이스다
			// 이 인터페이스에 FileSystemResource class의 객체를 담는데
			// 이 객체는 d:\\upload\\" + fileName이다.
			// 참고로 fileName은
			// str += "<li><div><a href='/sample/download?fileName="+fileCallPath+"'>" 이런식으로 download를 요청할 때 보내주기 때문에
			// 어떠한 경로에 있는 특정 파일을 Resource에 담게되는 것이다
			
			if (resource.exists() == false) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			// 만약 다운로드 하려는 파일이 존재하지 않는 파일이면 HttpStatus.NOT_FOUND를 return

			String resourceName = resource.getFilename();
			// FileSystemResource는 파일이름을 불러오는 method가 있나보다
			
			// remove UUID
			String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);
			// 지금 우리가 다운로드 하려는 파일의 이름은 저장할 때 앞에 uuid를 붙여서 djkaks;dal;skel;kal;mc_실제이름 이런식으로 저장되어 있는데
			// 여기서 실제이름만 뽑아내는 작업
			// 시작위치만 적으면 끝까지 뽑아옴 

			// 아래는 인코딩 후 다운로드 하고자 하는 파일을 return해주는 내용인듯
			HttpHeaders headers = new HttpHeaders();
			
			try {
				// IE version check
				boolean checkIE = (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1);
				
				String downloadName = null;

				if (checkIE) {
					downloadName = URLEncoder.encode(resourceOriginalName, "UTF8").replaceAll("\\+", " ");
				} else {
					downloadName = new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1");
				}
				// version에 따라 다르게 해준다
				
				headers.add("Content-Disposition", "attachment; filename=" + downloadName);
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
		}
		
		// 여기는 업로드 했을 때 그 파일이 이미지이면 확대해서 보여주기 위한 내용이다
		
		@GetMapping("/display")
		@ResponseBody
		public ResponseEntity<byte[]> getFile(String fileName) {

			log.info("fileName: " + fileName);

			File file = new File("d:\\upload\\" + fileName);

			log.info("file: " + file);

			ResponseEntity<byte[]> result = null;

			try {
				HttpHeaders header = new HttpHeaders();

				header.add("Content-Type", Files.probeContentType(file.toPath()));
				result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}
		
		// 여기는 업로드한 파일들 옆에 x 버튼 눌렀을 때 삭제하기 위한 거 
		
		@PostMapping("/deleteFile")
		@ResponseBody
		public ResponseEntity<String> deleteFile(String fileName, String type) {
			// 일단 파일이름과 type을 알고있다.
			// type을 가져오는 이유는 이미지인 경우는 섬네일도 지워야 하기 떄문에
			log.info("deleteFile: " + fileName);
			
			File file;
			try {
				file = new File("d:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));
				
				file.delete();
				// 파일 삭제
				
				if (type.equals("image")) {	// 섬네일도 추가로 삭제. 이미지이면 

					String largeFileName = file.getAbsolutePath().replace("s_", "");
					
					log.info("largeFileName: " + largeFileName);

					file = new File(largeFileName);

					file.delete();
				}

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
				// 삭제에 실패하면 파일을 찾을 수 없으니 not_found status를 return
			}

			return new ResponseEntity<String>("deleted", HttpStatus.OK);
		}
		
		
		
		
		
		
		// 
		private String getFolder() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String str = sdf.format(date);
			return str.replace("-", File.separator);
		}
		
		//
		private boolean checkImageType(File file) {
			try {
				String contentType = Files.probeContentType(file.toPath());

				return contentType.startsWith("image");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return false;
		}
		
		//
		// 
		// 아래는 Form으로 파일 받아오는거 (위에는 ajax 방식)
		
		@GetMapping("/uploadForm")
		public void uploadForm() {
			// 주소창에 sample/uploadForm 검색하면 uploadForm.jsp로 간다
		}
		
		@PostMapping("/uploadFormAction")
		public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
			String uploadFolder="d:\\upload\\temp";
			File uploadPath=new File(uploadFolder, getFolder());
			if(uploadPath.exists()==false) {	// 존재하지 않으면
				uploadPath.mkdirs();	// path에 폴더를 만들어라, mkdir이 아니고 mkdirs인 이유 = 오늘 날짜가 2020.09.18이면 2020년 폴더 안에 09월 만들고 18일 만들어야 하기 때문. 기존에 2020년, 09월 폴더가 있으면 18일 폴더만 만들게 된다
			}
			
			for(MultipartFile multiPartFile: uploadFile) {	// Form에서 uploadFile 이라는 이름으로 받아와서 multipartFile에 할당
				log.info("----- ----- -----");
				log.info("Upload File Name: "+multiPartFile.getOriginalFilename());
				log.info("Upload File Size: "+multiPartFile.getSize());
				File saveFile=new File(uploadPath, multiPartFile.getOriginalFilename());
				
				try {
					multiPartFile.transferTo(saveFile); // 파일 저장
				}catch(Exception e) {
					log.info(e.getMessage());
				}
			}
		}
	}
