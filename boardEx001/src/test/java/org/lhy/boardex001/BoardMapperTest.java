package org.lhy.boardex001;

import org.junit.runner.RunWith;
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
/*	@Setter(onMethod_= @Autowired)
	private DataSource dataSource;
	
	@Test
	public void testConnection() {
		try(Connection conn=dataSource.getConnection()){
			log.info(conn);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}*/
	
	@Setter(onMethod_= @Autowired)
	private BoardMapper mapper;
	
	@Test
	public void testGList() {
		mapper.getList().forEach(board->log.info(board));
	}

}