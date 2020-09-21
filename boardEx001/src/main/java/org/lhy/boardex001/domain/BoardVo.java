package org.lhy.boardex001.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BoardVo {
	private Long bno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private Date updatedate;
	private int readcount;
	private int replycount;
	
	private List<BoardAttachVO> attachList;
}
