package org.lhy.boardex001.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.lhy.boardex001.domain.BoardVo;

public interface BoardMapper {
	
	@Select("select * from board where bno>0")
	public List<BoardVo> getList();
	
}