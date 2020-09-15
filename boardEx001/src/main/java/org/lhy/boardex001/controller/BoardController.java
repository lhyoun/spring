package org.lhy.boardex001.controller;

import org.lhy.boardex001.domain.BoardVo;
import org.lhy.boardex001.mapper.BoardMapper;
import org.lhy.boardex001.service.BoardService;
import org.lhy.boardex001.util.Criteria;
import org.lhy.boardex001.util.PageDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	BoardMapper boardMapper;
	
	private BoardService service;
	
	@GetMapping("/list")
	public void list(Criteria cri,Model model) {
		model.addAttribute("list",service.getList(cri));
		int total=service.getTotal(cri);
		PageDTO pageDTO=new PageDTO(cri,total);		
		model.addAttribute("pageMaker", pageDTO);
	}

	@GetMapping("/get")		
	public void get(Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
		model.addAttribute("board", service.get(bno));
	}
	 
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public String register(BoardVo board, RedirectAttributes rttr) {
		service.register(board);
		rttr.addFlashAttribute("result", board.getBno());
		return "redirect:list";
	}
	
	@GetMapping("/modify")
	public void modify(Long bno, @ModelAttribute("cri") Criteria cri, Model model ) {
		log.info("mofify(GetMapping)");
		model.addAttribute("board", service.get(bno));
	}
	// get은 @ModelAttribute("cri") Criteria cri만 해줘도 modify.jsp까지 간다
	
	@PostMapping("/modify")
	public String modify(BoardVo board, Criteria cri, RedirectAttributes rttr) {
		if(service.modify(board)) rttr.addFlashAttribute("result", board.getBno()+"수정 성공");
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		return "redirect:/board/list";
	}
	// addFlashAttribute가 아니라 Attribute인 이유는 pageNum이랑 amount는 항상 가지고 다녀야 하기 때문

	@GetMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri,RedirectAttributes rttr) {
		if(service.remove(bno)) rttr.addFlashAttribute("result", bno+"삭제 성공");
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		return "redirect:/board/list";	
	}

}