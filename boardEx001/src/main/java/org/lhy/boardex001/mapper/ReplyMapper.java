package org.lhy.boardex001.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.lhy.boardex001.domain.ReplyVo;
import org.lhy.boardex001.util.Criteria;

public interface ReplyMapper {
	
	public int insert(ReplyVo vo);
	
	public ReplyVo read(Long rno);
	
	public int delete(Long rno);
	
	public int update(ReplyVo vo);
	
	//public List<ReplyVo> getList(Long bno);
	// 페이징이 없으면 bno만 보내면 됨
	
	//public List<ReplyVo> getWithPaging(Criteria cri, Long bno);
	// mapper로 data 이런식으로 2개 못 보낸다
	
	public List<ReplyVo> getList(@Param("cri") Criteria cri, @Param("bno") Long bno);
}
