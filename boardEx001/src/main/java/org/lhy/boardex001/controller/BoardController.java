package org.lhy.boardex001.controller;

import javax.servlet.http.HttpServletRequest;

import org.lhy.boardex001.domain.BoardVo;
import org.lhy.boardex001.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	@GetMapping("/register")
	public void register() {
		
	}
	
	@PostMapping("/register")	// 등록
	public String register(BoardVo board, RedirectAttributes rttr) {
		//service.register(board);
		//return "redirect:/board/list";
		
		log.info("registerPro");
		service.register(board);
		rttr.addFlashAttribute("result", board.getBno());
		return "redirect:list";
	}
	
	@GetMapping("/get")		// 상세보기
	public void get(Long bno, Model model ) {
		log.info("get");
		model.addAttribute("board", service.get(bno));
	}
	
	@GetMapping("/remove")	// 삭제
	public String remove(@RequestParam("bno") String bno) {
		service.remove(Long.parseLong(bno));
		return "redirect:/board/list";
	}
	
	@PostMapping("/modify")	// 수정
	public String modify(BoardVo board) {
		service.modify(board);
		return "redirect:/board/list";
	}
	
	/*@PostMapping("/update")
	public String update(BoardVO board) {
		log.info("update");
		if(service.modify(board)) {
			return "redirect:list";
		}else {
			return "redirect:get";
		}
	}
	
	@GetMapping("/delete")
	public String delete(Long bno) {
		log.info("delete");
		if(service.remove(bno)) {
			return "redirect:list";
		}else {
			return "redirect:get";
		}
	}*/

}