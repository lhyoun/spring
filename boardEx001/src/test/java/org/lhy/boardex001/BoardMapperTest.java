package org.lhy.boardex001;

import org.junit.runner.RunWith;
import org.lhy.boardex001.domain.BoardVo;
import org.lhy.boardex001.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Test;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTest {
/*	@Setter(onMethod_=@Autowired)
	private DataSource dataSource;
	
	@Test
	public void testConnection() {
		try(Connection conn=dataSource.getConnection()){
			log.info(conn);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}*/
	
	@Setter(onMethod_=@Autowired)
	private BoardMapper mapper;
	
	@Test
	public void testGetList() {
		mapper.getList().forEach(board->log.info(board));
	}
	
	@Test
	public void testRead() {
		log.info(mapper.read(1L));
	}
	
	@Test
	public void testInsert() {
		BoardVo board=new BoardVo();
		board.setTitle("새글 제목");
		board.setContent("새글 내용");
		board.setWriter("newUser");
		
		mapper.insert(board);
		log.info(board);
	}
	
	@Test
	public void testInsert2() {
		BoardVo board=new BoardVo();
		board.setTitle("새글 제목");
		board.setContent("새글 내용");
		board.setWriter("newUser");
		
		mapper.insertSelectKey(board);
		log.info(board);
	}
	
	@Test
	public void testUpdate() {
		BoardVo board=new BoardVo();
		board.setBno(1L);
		board.setTitle("update 제목");
		board.setContent("update 내용");
		board.setWriter("update User");
		int n=mapper.update(board);
		if(n==1) {
			log.info("업데이트 성공");
		}
		testGetList();
	}
	
	@Test
	public void testDelete() {
		int n=mapper.delete(10L);
		if(n==1) {
			log.info("삭제 성공");
		}
	}
}