package org.lhy.boardex001.service;

import java.util.List;

import org.lhy.boardex001.domain.BoardVo;
import org.lhy.boardex001.mapper.BoardMapper;
import org.lhy.boardex001.util.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Log
@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	private BoardMapper boardMapper;
	
	@Override
	public void register(BoardVo board) {
		// TODO Auto-generated method stub
		//boardMapper.insert(board);
		boardMapper.insertSelectKey(board);
	}

	@Transactional
	@Override
	public BoardVo get(Long bno) {
		// TODO Auto-generated method stub
		boardMapper.readCount(bno);
		return boardMapper.read(bno);
	}

	@Override
	public boolean modify(BoardVo board) {
		// TODO Auto-generated method stub
		int n=boardMapper.update(board);
		if(n==1) 
			return true;
		else
			return false;
	}

	@Override
	public boolean remove(Long bno) {
		// TODO Auto-generated method stub
		int n=boardMapper.delete(bno);
		if(n==1) 
			return true;
		else
			return false;
	}

	@Override
	public List<BoardVo> getList() {
		// TODO Auto-generated method stub
		return boardMapper.getList();
	}

	@Override
	public List<BoardVo> getList(Criteria cri) {
		// TODO Auto-generated method stub
		return boardMapper.getListWithPaging(cri);
	}

	@Override
	public int getTotal(Criteria cri) {
		// TODO Auto-generated method stub
		return boardMapper.totalCount(cri);
	}
	
}
