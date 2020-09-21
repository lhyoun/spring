package org.lhy.boardex001.mapper;

import java.util.List;

import org.lhy.boardex001.domain.BoardAttachVO;

public interface BoardAttachMapper {
	public void insert(BoardAttachVO vo);
	public void delete(String uuid);
	public List<BoardAttachVO> findByBno(Long bno);
	public List<BoardAttachVO> deleteAll(Long bno);
}
