package org.lhy.boardex001.controller;

import org.lhy.boardex001.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Controller
@Log
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {
	//위에 @AllArgsConstructor 이게 없으면 @autowired 적어야 한다
	private BoardService service;
	
	@GetMapping("/list")
	public void list(Model model) {
		log.info("list");
		model.addAttribute("list", service.getList());
	}// void라서 mapping과 같은 값을 return
	
	
}
