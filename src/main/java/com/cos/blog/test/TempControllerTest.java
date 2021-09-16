package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//** 프로젝트 기본 포트 8000번으로 변경
//** 파일 기본경로도 src/main/WEB-INF/views 로 변경

//사용자가 한 요청에 대한 응답을 해주는 컨트롤러 @Controller (html파일을 응답해줌)
@Controller
public class TempControllerTest {

		//http://localhost:8000/blog/temp/home   html파일을 리턴해줌
		@GetMapping("/temp/home")
		public String tempHome()
		{
			System.out.println("tempHome()");
			//파일리턴 기본경로 : src/main/resources/static이 기본경로
			// 풀경로 : src/main/resources/static/home.html
			//** 이 기본경로는 스프링이 정적인 파일을 받는 경로로 jsp파일을 인식하지 못함 그래서 다른경로에 넣고 application.yml설정을 바꿔줘야 함
			//** static 폴더 안에는 브라우저가 인식할 수 있는 파일만 넣어야 함
			return "/home.html"; // 경로에 있는 파일을 찾아야해서 /home.html로 리턴해줘야함
		}
		
		@GetMapping("/temp/img")
		public String tempImg()
		{
			return "/a.jpg"; // 정적파일이고 브라우저가 인식할 수 있어서 기본경로에 있어도 오류 없음
		}
		
		@GetMapping("/temp/jsp")
		public String tempJsp()
		{
			// 컴파일이 일어나야하는 동적파일이라 기본경로에선 찾을 수 없음
			//그래서 src/main 밑에 webapp/WEB-INF/views 폴더를 만들어줘서 jsp 파일 넣어주고 application.yml에서 설정 바꿔서 기본경로로 만들어줌
			// prefix : /WEB-INF/views/
			// suffix : .jsp
			// 풀네임 : /WEB-INF/views/tes.jsp
			return "test"; 
		}
}
