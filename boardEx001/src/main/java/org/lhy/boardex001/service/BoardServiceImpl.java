package org.lhy.boardex001.service;

import java.util.List;

import org.lhy.boardex001.domain.BoardVo;
import org.lhy.boardex001.mapper.BoardMapper;
import org.springframework.stereotype.Service;

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

	@Override
	public BoardVo get(Long bno) {
		// TODO Auto-generated method stub
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
	
}