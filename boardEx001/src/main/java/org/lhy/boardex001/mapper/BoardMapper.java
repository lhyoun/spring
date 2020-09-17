package org.lhy.boardex001.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.lhy.boardex001.domain.BoardVo;
import org.lhy.boardex001.util.Criteria;

public interface BoardMapper {
	
	// sql session factory를 만든다? 복잡한 쿼리는 xml에서
	//@Select("select * from board where bno>0")
	public List<BoardVo> getList();
	
	public List<BoardVo> getListWithPaging(Criteria cri);
	// cri에 amount, page
	public BoardVo read(Long bno);
	
	public void insert(BoardVo board);
	
	public void insertSelectKey(BoardVo board);
	
	public int update(BoardVo board);
	
	public int delete(Long bno);
	
	public int totalCount(Criteria cri);
	
	public int readCount(Long bno);
	
	public int replyCount(@Param("amount") int amount, @Param("bno") Long bno);
	// amount=1or-1
}
