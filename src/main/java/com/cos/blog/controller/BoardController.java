package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.service.BoardService;

@Controller //이 컨트롤러는 데이터를 보내는 restcontroller가 아니고 웹페이지를 리턴하는놈으로 리턴시 viewResolver가 작동해서 웹페이지를 만들어줌
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	//@AuthenticationPrincipal PrincipalDetail principal //컨트롤러에서 세션을 찾는 방법 이게 매개변수 안에 들어가면 됨
	@GetMapping({"", "/"}) //이렇게 중괄호 써주면 둘다 여기에 해당된다는 의미 즉 아무것도 안적었을때랑 /만 적었을때 둘다 해당됨
	public String index(Model model, @PageableDefault(size=3, sort="id", direction=Sort.Direction.DESC) Pageable pageable)
	// 스프링에선 데이터를 가져갈때 Model이 필요 model은 jsp에선 request정보라고 생각하면됨 (Model에 저장한 데이터를 가지고 View까지 이동시켜주는게 Model)
	//@PageableDefault는 dummycontroller에서 해봤던 페이징 하는 방법
	{
		model.addAttribute("boards",boardService.글목록(pageable)); //boards라는 키값으로 boardService.글목록()을 가져갈것, 누가 /주소를 요청하면 모든 글목록을 boardService.글목록()으로 다 들고옴
		//가져온 값들을 index.jsp에 뿌릴것 model.addAttribute로 index.jsp 페이지에 boards를 날려줌 (model은 데이터를 view까지전송시켜주는 역할)
		
		// "/WEB-INF/views/" index ".jsp"  application.yml가보면 prefix suffix 설정이 되어있어서 viewResolver가  "/WEB-INF/views/" , ".jsp"를 앞뒤로 붙여주는것
		return "index"; //이 클래스는 restcontroller가 아니라 일반 controller이기에 리턴시 viewResolver가 작동해서 동적으로 index.jsp페이지를 만들어줌
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) //@PathVariable은 주소창에서 적은 {id}의 값을 가져와서 매개변수 int id에 넣기 즉 글의 id를 가져오기
	{
		model.addAttribute("board", boardService.글상세보기(id)); //글 id로 가져온 글의 정보를 board라는 변수에 담아서 board/detail 페이지로 보냄
		return "board/detail";
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) //Model에 저장한 데이터를 가지고 View까지 이동시켜주는게 Model
	{
		model.addAttribute("board",boardService.글상세보기(id));
		return "board/updateForm";
	}
	
	@GetMapping("/board/saveForm")
	public String saveForm()
	{
		return "board/saveForm";
	}
}
