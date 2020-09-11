package org.lhy.boardex001.service;

import java.util.List;

import org.lhy.boardex001.domain.BoardVo;

public interface BoardService {
	public void register(BoardVo board);
	public BoardVo get(Long bno);
	public boolean modify(BoardVo board);
	public boolean remove(Long bno);
	public List<BoardVo> getList();
	
}
