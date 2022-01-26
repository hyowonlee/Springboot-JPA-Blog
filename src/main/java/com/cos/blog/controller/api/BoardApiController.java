package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.UserService;

@RestController // 데이터만 리턴할거기에  @RestController (@RestController는 data를 응답해줌, 메서드 반환타입앞에 @ResponseBody를 적어줘도 data를 리턴해주는 컨트롤러 함수가 됨)
public class BoardApiController {
	//이거 UserApiController.java와 같음 주석은 거기봐
	
	@Autowired
	private BoardService boardService;
	
	@PostMapping("/api/board")  //여기 매개변수 board는 우리가 board.js에서 title이랑 content만 보냈으니 그 2개만 가지고있고 나머진 boardService.글쓰기에서 직접 넣어줘야함
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) //컨트롤러에서 세션을 찾는 방법은 이 매개변수 처럼 어노테이션을 붙여서 UserDetails타입의 세션을 가져옴
	{
		boardService.글쓰기(board, principal.getUser()); //principal.getUser()를 통해 현재 로그인중인 세션의 User객체를 넘겨줌
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);//자바오브젝트를 Json으로 변환해서 리턴(Jackson) 정상종료됐다는 1, 정상종료했다는 응답후 다시 board.js로 돌아감
	}  
	
	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id) //@PathVariable은 주소창에서 적은 {id}의 값을 가져와서 매개변수 int id에 넣기 즉 글의 id를 가져오기
	{
		boardService.글삭제하기(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);//정상종료됐다는 1, 정상종료했다는 응답후 다시 board.js로 돌아감
	}
	
	@PutMapping("/api/board/{id}") //delete mapping의 url이랑 url이 같은데 상관없음 요청하는 타입이 달라서 put요청하면 열로 delete요청하면 절로 감
	public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board) //@RequestBody는 http요청과 함께받은 json데이터를 java객체에 매핑하기위해 사용
	{
		boardService.글수정하기(id, board);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);//정상종료됐다는 1, 정상종료했다는 응답후 다시 board.js로 돌아감
	}
	
	
		//데이터 받을때 컨트롤러에서 dto(vo)를 만들어서 받는게 좋음
		//이 프로젝트에서 dto 안쓴 이유는 작은 프로젝트여서임
		@PostMapping("/api/board/{boardId}/reply")
		public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto)
		{
			boardService.댓글쓰기(replySaveRequestDto); //principal.getUser()를 통해 현재 로그인중인 세션의 User객체를 넘겨줌
			return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);//자바오브젝트를 Json으로 변환해서 리턴(Jackson) 정상종료됐다는 1, 정상종료했다는 응답후 다시 board.js로 돌아감
		}  
	
		@DeleteMapping("/api/board/{boardId}/reply/{replyId}")
		public ResponseDto<Integer> replyDelete(@PathVariable int replyId)
		{
			boardService.댓글삭제(replyId);
			return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
		}
	
	
//	//데이터 받을때 컨트롤러에서 dto(vo)를 만들어서 받는게 좋음
//	//여기서 dto 안쓴 이유는 작은 프로젝트여서
//	@PostMapping("/api/board/{boardId}/reply")
//	public ResponseDto<Integer> replySave(@PathVariable int boardId, @RequestBody Reply reply, @AuthenticationPrincipal PrincipalDetail principal) //컨트롤러에서 세션을 찾는 방법은 이 매개변수 처럼 어노테이션을 붙여서 UserDetails타입의 세션을 가져옴
//	{
//		boardService.댓글쓰기(principal.getUser(), boardId, reply); //principal.getUser()를 통해 현재 로그인중인 세션의 User객체를 넘겨줌
//		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);//자바오브젝트를 Json으로 변환해서 리턴(Jackson) 정상종료됐다는 1, 정상종료했다는 응답후 다시 board.js로 돌아감
//	}  
	
	
	
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
