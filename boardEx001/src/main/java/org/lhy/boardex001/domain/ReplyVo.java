package org.lhy.boardex001.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyVo {
	private Long rno;
	private Long bno;
	private String reply;
	private String replyer;
	private Date replydate;
	private Date updatadate;
}
