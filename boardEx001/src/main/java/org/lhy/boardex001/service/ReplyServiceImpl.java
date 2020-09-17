package org.lhy.boardex001.service;

import java.util.List;

import org.lhy.boardex001.domain.ReplyVo;
import org.lhy.boardex001.mapper.BoardMapper;
import org.lhy.boardex001.mapper.ReplyMapper;
import org.lhy.boardex001.util.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Log
@Service
@AllArgsConstructor
public class ReplyServiceImpl implements ReplyService {

	private ReplyMapper replyMapper;
	private BoardMapper boardMapper;
	
	@Transactional
	@Override
	public int register(ReplyVo vo) {
		// TODO Auto-generated method stub
		boardMapper.replyCount(1, vo.getBno());
		return replyMapper.insert(vo);
	}

	@Override
	public ReplyVo get(Long rno) {
		// TODO Auto-generated method stub
		return replyMapper.read(rno);
	}

	@Override
	public int modify(ReplyVo vo) {
		// TODO Auto-generated method stub
		return replyMapper.update(vo);
	}

	@Transactional
	@Override
	public int remove(Long rno) {
		// TODO Auto-generated method stub
		ReplyVo vo=replyMapper.read(rno);
		boardMapper.replyCount(-1, vo.getBno());
		return replyMapper.delete(rno);
	}

	@Override
	public List<ReplyVo> getList(Criteria cri, Long bno) {
		// TODO Auto-generated method stub
		return replyMapper.getList(cri, bno);
	}

}
