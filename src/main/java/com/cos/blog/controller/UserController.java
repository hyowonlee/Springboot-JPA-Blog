package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
	
	//스프링 시큐리티 - 인증이 안된 사용자들이 출입할수 있는 경로에 auth를 붙일것 /auth/** 허용 
	//그냥 주소가 /이면 가는 index.jsp도 인증없이 허용 허용 
	//static폴더 이하에 있는 /js/** , /css/** , /image/** 도 인증없이 허용
	@GetMapping("/auth/joinForm")
	public String joinForm()
	{
		return "user/joinForm";
	}

	@GetMapping("/auth/loginForm")
	public String loginForm()
	{
		return "user/loginForm";
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm()
	{
		return "user/updateForm";
	}
}
