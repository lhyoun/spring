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
	
	//@GetMapping("/list")
	public void list(Model model) {
		log.info("list");
		model.addAttribute("list", service.getList());
	}// void라서 mapping과 같은 값을 return
	
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		log.info("-- list+Paging");
		log.info("-- pagenum: "+cri.getPageNum());
		log.info("-- amount: "+cri.getAmount());
		model.addAttribute("list", service.getList(cri));
		
		int total=service.getTotal();

		model.addAttribute("pageMaker", new PageDTO(cri, total));
		
	}
	
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
	public void get(Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
		log.info("get");
		model.addAttribute("board", service.get(bno));
	}
	 
	@GetMapping("/modify")
	public void modify(Long bno, @ModelAttribute("cri") Criteria cri, Model model ) {
		log.info("mofify(GetMapping)");
		model.addAttribute("board", service.get(bno));
	}// get은 @ModelAttribute("cri") Criteria cri만 해줘도 modify.jsp까지 간다
	
	/*@GetMapping("/remove")	// 삭제
	public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result", bno+"삭제 성공");
			return "redirect:/board/list";
		}
		else { 
			return "redirect:/board/get";
		}
	}*/
	
	@GetMapping("/remove")
	public String remove(@RequestParam("bno") Long bno,
			@ModelAttribute("cri") Criteria cri,RedirectAttributes rttr) {
		log.info("remove..."+bno);
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result", bno+"삭제 성공");
		}
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		return "redirect:/board/list";	
	}
	
	@PostMapping("/modify")	// 수정
	public String modify(BoardVo board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		if(service.modify(board)) {
				rttr.addFlashAttribute("result", board.getBno()+"수정 성공");
		}
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		return "redirect:/board/list";
		//return "list";
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