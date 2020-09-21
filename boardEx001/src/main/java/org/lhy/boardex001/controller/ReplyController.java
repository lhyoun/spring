package org.lhy.boardex001.controller;

import java.util.Date;
import java.util.List;

import org.lhy.boardex001.domain.ReplyVo;
import org.lhy.boardex001.service.ReplyService;
import org.lhy.boardex001.util.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("/replies")
@AllArgsConstructor
public class ReplyController {
	/* 	우선 RestController란 -
	 	return값을 원하는 형태로 browser에 바로 출력하고자 할 때 사용
	 	일반 Controller는 view를 return하고 그 view를 보여주게 되는데 Rest는 view없이 return한 값만 browser에 출력하게 된다*/
	private ReplyService service;
	
	/* 	consumes는 입력받을 데이터 타입, produce는 return할 데이터 타입. 아래에서 자세히 설명하겠다
	  	참고로 consumes 때문에 에러가 나면 415 ,produces 때문에 에러가 나면 406 에러가 발생한다*/
	@PostMapping(value="/new", consumes="application/json", produces= {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> create(@RequestBody ReplyVo vo){
		/*	일단 여기에 ResponseEntity<String>로 return한다고 되어있는데, 
		 	browser에 string을 적을건데 타입은 produces에 적힌 TEXT_PLAIN_VALUE이다. TEXT_PLAIN_VALUE는 
		 	아래 return에서 "reply add success"이렇게 적은 것 처럼 원하는 text를 적을 수 있다
		  	@RequestBody는 ajax로 받은 vo의 필드들을 합쳐서 ReplyVo vo에 집어넣겠다. 이런 뜻 
		 */
		int insertCount=service.register(vo);
		/*	설명이 늦었지만, 
		 	여기는 댓글을 추가했을 때 오는 곳이다. 입력하고자 하는 댓글 내용과 댓글 쓴이를  ajax를 이용해서 이곳으로 보낸다
		 	그렇게하면 여기서는 위에서 말한 것 처럼 받은 데이터로 vo로 만들어서 service.register(vo);를 통해 댓글을 insert하게 된다
		 	참고로 ReplyVo는 아래의 필드들로 구성되는데
		 	private Long rno;
			private Long bno;
			private String reply;
			private String replyer;
			private Date replydate;
			private Date updatadate;
			이 내용을 모두 보내면 vo에 다 들어가고 이 중 3개만 보내면 3개만 들어있고 나머지는 null인 vo가 만들어진다. 
			(예를 들어 replydate는 null로 insert를 해도 db에서 default sysdate가 있기때문에 아무런 문제가 없다)*/
		return insertCount==1? new ResponseEntity<>("reply add success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}	/*  쿼리를 실행하면 insert된 row의 개수를 return하는데 하나의 댓글이 등록 성공되면 insertCount에는 1이 들어가게 된다
			그렇게 되면 new ResponseEntity<>("reply add success", HttpStatus.OK)이 값이 return된다
			reply add success는 browser에 출력되고 HttpStatus.OK는 F12누르면 console인가 어딘가에서 확인할 수 있다*/
	
	
	
	/* 	이 부분은 get.jsp의  script에 showList(page)에 아래와 같은 방식으로 요청하게 된다
	 	replyService.getList({bno:bnoValue,page: page|| 1 }, function(list)
	 	여기서 replyService.getList는 reply.js file의 function getList(param, callback, error)에 해당하는 내용인데
	 	$.getJSON("/replies/pages/" + bno + "/" + page + ".json" 이렇게 요청한다.
	 	여기서 /replies/pages/bno/page 이렇게 요청하는데 @GetMapping(value="/pages/{bno}/{page}" 이렇게 해놓으면
	 	@PathVariable("page") int page, @PathVariable("bno") Long bno 이렇게 해서 받을 수 있다
	 	
	 	그리고 {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE}은 xml,json형태로 browser에 출력하겠다 이런 의미인데
	 	json은 안적어줘도 자동으로 지원하기 때문에 선이 그어져 있다*/
	@GetMapping(value="/pages/{bno}/{page}",
		produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<List<ReplyVo>> getList(@PathVariable("page") int page, @PathVariable("bno") Long bno){
		// json, xml로 return할 내용은 ReplyVo로 이루어진 List 
		Criteria cri=new Criteria(page,10);
		return new ResponseEntity<>(service.getList(cri, bno),HttpStatus.OK);
	}
	
	
	
	//
	@GetMapping(value = "/{rno}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<ReplyVo> get(@PathVariable("rno") Long rno) {
		
		log.info("get: " + rno);
		
		return new ResponseEntity<>(service.get(rno), HttpStatus.OK);
	}
	
	
	
	//
	@DeleteMapping(value= "/{rno}" ,produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> remove(@PathVariable("rno") Long rno) {
		log.info("remove: " + rno);
		return service.remove(rno) == 1 ? new ResponseEntity<>("reply delete success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	
	//
	@RequestMapping(method = { RequestMethod.PUT, RequestMethod.PATCH }, value = "/{rno}", consumes = "application/json", produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> modify(@RequestBody ReplyVo vo, @PathVariable("rno") Long rno) {
		vo.setRno(rno);
		log.info("rno: " + rno);
		log.info("modify: " + vo);
		return service.modify(vo) == 1 ? new ResponseEntity<>("reply modify success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}