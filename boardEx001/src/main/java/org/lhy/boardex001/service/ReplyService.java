package org.lhy.boardex001.service;

import java.util.List;

import org.lhy.boardex001.domain.ReplyVo;
import org.lhy.boardex001.util.Criteria;

public interface ReplyService {
	
	public int register(ReplyVo vo);
	public ReplyVo get(Long rno);
	public int modify(ReplyVo vo);
	public int remove(Long rno);
	public List<ReplyVo> getList(Criteria cri, Long bno);
	
}
