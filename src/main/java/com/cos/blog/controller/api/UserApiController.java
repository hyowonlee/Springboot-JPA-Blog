package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController // 데이터만 리턴할거기에  @RestController (data를 응답해줌)
public class UserApiController {
	
	@Autowired //UserService 클래스 가보면 @Service 붙어있어서 DI가능
	private UserService userService;
	
	// 회원가입 요청시 user.js에서 ajax 통신할때 여기 url을 부름
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) //지금 user로 직접 받는게 user.js에서 받은 username, password, email 이 3개밖에 없는데 user가 들고있는 role빼고 나머지것들은 자동으로 들어감 (role은 서비스에서 넣어줄것)
	{
		System.out.println("UserApiController.java : save 호출됨");
		//user.js에서 ajax 통신 성공시 이 객체를 리턴함 지금 responsedto에서 status, data 매개변수 가진 생성자를 불러온것 HttpStatus.OK는 상태코드 200의미 정상성공했다는 의미 객체라 .value로 int형태로 리턴
		// 회원가입 페이지에서 안받아온 정보인 role은 서비스(UserService.java)에서 입력해줫음, 암호도 해쉬 암호화 때문에 서비스에서 해쉬 암호화 해줌
		//실제로 db에 insert를 하고 아래에서 return이 되면 됨

		//int result = userService.회원가입(user); //오류
		userService.회원가입(user);
		
		//return new ResponseDto<Integer>(HttpStatus.OK, result);//자바오브젝트를 Json으로 변환해서 리턴(Jackson)  //오류
		//result자리는 UserService에서 UserRepository(repository)로 db 인서트 하고 리턴된 값을 넣을것(내가 정의해놨고 성공이면 1 실패면 -1)
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);//자바오브젝트를 Json으로 변환해서 리턴(Jackson) 정상종료됐다는 1, 정상종료했다는 응답후 다시 board.js로 돌아감
		//원랜 위처럼 result를 사용해서 오류시 -1을 반환하게했었지만 GlobalExceptionHandler.java에서 모든 exception을 먼저 가로채기에 -1반환이 안되고 저놈이 낚아챘음
		//그래서 result를 지우고 그냥 .회원가입 메서드가 오류난다면 GlobalExceptionHandler.java에서 처리하게 만들었음

		
	}  
	
	
	//스프링 시큐리티를 사용하므로 이건 사용 안함
//	//전통적인 방식의 로그인 방법
//	@PostMapping("/api/user/login")
//	public ResponseDto<Integer> login(@RequestBody User user, HttpSession session) //@RequestBody는 http요청과 함께받은 json데이터를 java객체에 매핑하기위해 사용
//	{  //로그인 세션을 위해 여기서 HttpSession을 받아주는데 session은 di로 자동으로 넣어줌 즉 스프링 컨테이너가 빈으로 등록해 가지고있다는소리 이렇게 함수 매개변수로 적어주기만 해도 자동 di 해줌
//		//여기에다 세션을 매개변수로 넣어줘도 되고 아님 여기의 매개변수 세션 지우고 맨위에 Autowired로 변수 만들어줘도 자동으로 di 된다 (private HttpSession session; 이걸 autowired)
//		System.out.println("UserApiController.java : login 호출됨");
//		User principal = userService.로그인(user);//principal(접근주체)
//		
//		if(principal != null)//로그인 함수 리턴이 널이 아닌경우 http세션 생성
//		{
//			session.setAttribute("principal", principal); //key, value 매개변수 받음
//		}
//		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 로그인 정상종료시 user.js에서 설정한 값으로 /blog로 리다이렉션 시킴
//		//로그인 정상 종료시 1 을 리턴해주고 정상 종료됐다
//	}
//	
}
