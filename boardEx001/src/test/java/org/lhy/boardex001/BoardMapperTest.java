package org.lhy.boardex001;

import org.junit.runner.RunWith;
import org.lhy.boardex001.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.junit.Test;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTest {
	// mapper를 생성할 수 없어서 메소드에 접근할 수 없다. 아래의 @는 lombok의 기능인데 객체 생성없이 메소드를 사용할 수 있게 해준다
	@Setter(onMethod_=@Autowired)
	private BoardMapper mapper;
	
	@Test
	public void testGList() {
		mapper.getList().forEach(board->log.info(board));
	}
	
}
