package org.lhy.boardex001.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
	private int pageNum;
	private int amount;
	
	public Criteria() {
		this(1, 10); // 아래의 Criteria(int pageNum, int amount)를 호출
	}
	
	public Criteria(int pageNum, int amount) {
		super();
		this.pageNum = pageNum;
		this.amount = amount;
	}

}
