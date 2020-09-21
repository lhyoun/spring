package org.lhy.boardex001.controller;

import org.lhy.boardex001.domain.BoardVo;
import org.lhy.boardex001.mapper.BoardMapper;
import org.lhy.boardex001.service.BoardService;
import org.lhy.boardex001.util.Criteria;
import org.lhy.boardex001.util.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
	// @AllArgsConstructor 없으면 @Autowired 적어야 한다
	BoardMapper boardMapper;
	private BoardService service;
	/* 	@AllArgsConstructor적는다고 Autowired가 되는 이유(BoarMapper, BoardService를 생성 안 해도 자동으로 생성되는 이유) - 
		autowired하는 법은 3가지가 있는데(필드, 생성자, setter) 필드, setter의 경우 명시적으로 @Autowired를 적어줘야 하지만
		생성자의 경우 @Autowired의 생략이 가능히다.
		따라서 자동으로 생성자를 만들어주는 @AllArgsConstructor의 경우  별도의 @Autowired선언이 없어도 Autowired가 된다*/
	
	
	
	/* 	해당 project의 최초 진입점이다
		주소창에 http://localhost:8082/board/list라고 적어도 된다
		http://localhost:8082/boardex001/board/list가 아니고 boardex001이 생략된 이유 -
		Toacat server modules설정에서 documentBase인 boardex001를 루트(/)로 설정해 뒀기 때문
		즉 http://localhost:8082 다음의 '/'가  '/boardex001'를 뜻함*/
	@GetMapping("/list")
	public void list(Criteria cri,Model model) {
		/* 	우선 View로 보낼 data가 존재하면 'Model model'이 필요
		 	Criteria에는 pageNum/amount/type/keyword 정보가 담겨는데, 이는 예를 들어서 글을 보다가 다시 리스트로 돌아갈 때
		 	원래 읽고 있던 페이지로 돌아가기 위해서 필요한 것이다
		 	list.jsp에 처음 진입 할 경우 그냥 1페이지로 가면 되기 때문에 cri를 넘겨줄 필요는 없다
		 	즉, list(Criteria cri,Model model) 여기서 매개변수로 cri를 받는다고 해둬도 반드시 넘겨줘야하는 것은 아니다*/
		model.addAttribute("list",service.getList(cri));
		/*	model은 view로 보내줄 정보를 의미한다. 즉 service.getList(cri)의 return값을 "list"의 이름으로 view로 보내준다 
			(view에서는 "${list}" 이렇게 받아온 데이터를 사용할 수 있다)*/
		int total=service.getTotal(cri);
		PageDTO pageDTO=new PageDTO(cri,total);
		// 	PageDTO는 페이징을 위한 class인데 게시물의 총 개수를 구하기 위해 service.getTotal(cri);
		model.addAttribute("pageMaker", pageDTO);
		// 페이징을 하기 위해서 pageDTO객체도 "pageMaker"라는 이름으로 보내준다
	}

	
	
	@GetMapping("/get")		
	public void get(Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
		// 리스트에서 하나의 게시물을 선택하면 오는 곳이다. 선택한 게시물을 구분하기 위해서 bno를 받아온다
		model.addAttribute("board", service.get(bno));
		// 받은 bno에 해당하는 게시물 하나를 "board"라는 이름으로 보내준다
	}
	 
	
	
	@GetMapping("/register")
	public void register() {}
	/* 	위에서 설명하지 않은 public 'void'의 의미 -
	 	Mapping에서 들어온 경로 그대로를 return. 그렇게 되면 /WEB-INF/views/ <return값>.jsp로 이동하게 된다
	 	/WEB-INF/views/<return값>.jsp로 이동하는 이유는 servlet-context에서 return값 앞에 적을 값과 뒤에 적을 값을 설정해 두었기 때문
	 	
	 	그 내용은 다음과 같다
	 	<beans:property name="prefix" value="/WEB-INF/views/" />
		<beans:property name="suffix" value=".jsp" />*/
	
	
	
	/* 	게시물을 등록하는 내용이다. register.jsp에서 사용하는 부분인데 게시글을 작성해서 다음과 같은 form으로 데이터를 전송하게 된다
	 	<form role="form" action="/board/register" method="post">
	 	method가 post기 때문에 PostMapping. 만약에 get이나 method에 대한 내용이 없으면(default:get) GetMapping을 해주어야 한다*/
	@PostMapping("/register")
	public String register(BoardVo board, RedirectAttributes rttr) {
		/* 	BoardVo객체를 받아온다고 되어있는데 register.jsp에서 from으로 데이터를 전송할 때 
		 	<input class="form-control" name="title">
		 	name에 해당하는 부분이 BoardVo객체에 set되어서 받아진다 (별도로 controller에서 일일이 값을 받아서 객체를 만들어 줄 필요가 없다)
		 	
		 	RedirectAttibutes는 return에서 redirect를 사용할 경우 이동하는 view까지 함께 가져갈 데이터를 rttr에 담으면 된다. 
		 	redirect란 -
		 	우선 public 'String'은 return값으로 return한 곳으로 이동하게 된다. 위에서 설명한 것 처럼 /WEB-INF/views/<return값>.jsp으로 가게된다
		 	그런데 redirect를 할 경우 view로 가지 않고 @Controller로 간다. 즉 아래에서 redirect:list";의 의미는 @Controller에서  @GetMapping("/list")로 다시 가는것이다
		 	
		 	이를 풀어서 설명하면 게시물 등록 페이지에서 <등록> 버튼을 누르게 되면 데이터를 받아와 db에 저장하고 다시 listpage로 이동하라는 뜻
		 	추가로 getBno를 가져가는 이유는 내가 등록한 게시글 번호가 25번이면 리스트로 돌아감과 동시에 "25번 게시물이 등록되었습니다"라는 modal을 띄우기 위함
		 	만약 여기서 추가한 bno를 보내주지 않으면 listpage에서는 내가 방금 추가한 게시물의 번호가 몇 번인지 알 수 없기 때문
		 	<list.jsp script부분 function checkModal(result)에 관한 내용>*/
		
		if(board.getAttachList()!=null) {
			board.getAttachList().forEach(attach->log.info("첨부파일 리스트: "+attach));
		}
		
		service.register(board);
		rttr.addFlashAttribute("result", board.getBno());
		return "redirect:list";
	}
	
	
	
	/* 	get.jsp에서 <수정하기>버튼을 누르면 오는 곳
	 	Long bno는 몇 번 게시글을 수정하는지 알게하기 위해 받음
	 	@ModelAttribute("cri") Criteria cri는 Criteria를 받아서 view에 "cri"라는 이름으로 보내겠다. 라는 뜻
	 	만약 @ModelAttribute("cri") 없이 Criteria cri라고 하면 view에서 cri로 사용할 수 없다
	 	Criteria 다음에 cri라고 적든 abc라고 적은 view에서는 criteria(첫 자만 소문자로 변경)로 사용할 수 있다*/
	@GetMapping("/modify")
	public void modify(Long bno, @ModelAttribute("cri") Criteria cri, Model model ) {
		log.info("mofify(GetMapping)");
		model.addAttribute("board", service.get(bno));
		// 수정하고자 하는 게시물의 정보를 "board"라는 이름으로 보낸 뒤 (void니까)modify.sjp에 전달해준다
	}
	/* 	get은 @ModelAttribute("cri") Criteria cri만 해줘도 modify.jsp까지 간다
	 	즉, public void modify(Long bno, @ModelAttribute("cri") Criteria cri, Model model )
	 	여기서 ()안에 있는 값들을 http://localhost:8082/board/get?pageNum=1&amount=10&type=&keyword=&bno=24
	 	이런식으로 ?뒤에 달고 간다 (신경 안 써도 무방)*/
	
	
	
	// 	modify.jsp에서 <수정하기>버튼을 누르면 오는 곳
	@PostMapping("/modify")
	public String modify(BoardVo board, Criteria cri, RedirectAttributes rttr) {
		if(service.modify(board)) rttr.addFlashAttribute("result", board.getBno()+"수정 성공");
		/* 	sql에서 modify를 사용하면 변경된 row의 개수(?정확하지 않음. 어쨌든 숫자)를 return해주는데
		 	이는 즉, if(service.modify(board))에서 하나의 게시물 수정을 성공하면(1을 return해주니까, true가 됨) "result"라는 이름으로 
		 	"bno 수정성공" 이라는 String을 보낸다는 뜻. 이 result는  list.jsp의 script function checkModal(result) 함수에서
		 	수정을 성공한 경우 "bno번 수정성공" 이런식으로 modal을 띄워주기 위해 rttr로 보내준다*/
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		return "redirect:/board/list";
	}
	/* 	rttr.addFlashAttribute()는 위에서 설명을 했는데,
	 	Flash는 다음 view까지만 가져가고 Flash없는건 계속 가지고간다? 이런 차이였던 거 같은데 
	 	addFlashAttribute가 아니라 Attribute인 이유는 pageNum이랑 amount는 항상 가지고 다녀야 하기 때문이다*/

	
	// get.jspdptj <삭제하기>버튼을 누르면 오는 곳
	@GetMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri,RedirectAttributes rttr) {
		/* 	@RequestParam("bno") Long bno는 받아온 데이터 중에 "bno"라는 이름으로 받아온 데이터를 Long bno에 저장하겠다? 이런 뜻
			참고로 보낼 때의 type과 상관없이 int bno 해도되고 Long bno 해도 된다(원하는 type으로 받아짐) */
		if(service.remove(bno)) rttr.addFlashAttribute("result", bno+"삭제 성공");
		// 위에서 modify처럼 삭제된 row의 개수를 return해줌 아마도
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		return "redirect:/board/list";	
	}
}