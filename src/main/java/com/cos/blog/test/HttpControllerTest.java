
package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//** 프로젝트 기본 포트 8000번으로 변경

// 사용자가 한 요청에 대한 HTML 파일에 대한 응답을 위해선 @Controller 사용

// 사용자가 한 요청에 대한 응답을 해주는 컨트롤러 @RestController (data를 응답해줌)
@RestController
public class HttpControllerTest {
	
	private static final String TAG = "HttpControllerTest : ";
	
	//lombok을 통한 getter setter 테스트 Member 클래스 파일에 어노테이션, import로 설정되어 있음
	@GetMapping("http/lombok")
	public String lombokTest()
	{
		//Member m = new Member(1, "ssar", "1234", "email");
		//builder패턴을 통한 생성자의 모든 매개변수를 넣을 필요도 없고 원하는것만 넣어서 가변적으로 생성자를 돌릴수 있음 맨뒤에 .build()는 꼭 해줘야함
		Member m = Member.builder().password("1234").username("ssar").email("asdf@asdf").build();
		System.out.println(TAG + "getter : " + m.getId());
		m.setId(5000);
		System.out.println(TAG + "setter : " + m.getId());
		System.out.println(TAG + "getter : " + m.getUsername());
		m.setUsername("cos");
		System.out.println(TAG + "setter : " + m.getUsername());
		return "lombok test 완료";
	}
	
	//인터넷 브라우저 요청은 무조건 get 요청밖에 할 수 없음
	//http://localhost:8000/blog/http/get    (select)
	@GetMapping("/http/get")
	public String getTest()
	{   
		return "get 요청 ";
	}
	
	//http://localhost:8000/blog/http/get    (select)
	@GetMapping("/http/get1")
	public String getTest(@RequestParam int id, @RequestParam String username) //쿼리스트링의 url ? 뒤의 값을 해당 변수에 넣어주는 어노테이션
	{   //http://localhost:8000/http/get?id=1&username=ssar
		return "get 요청 : " + id + "," + username;
	}
	
	@GetMapping("/http/get2")
	public String getTest2(Member m) //이것도 쿼리스트링의 url ? 뒤의 값을 해당 클래스의 멤버에 넣어줌
	{  //http://localhost:8000/blog/http/get2?id=1&username=ssar&password=1234&email=ssar@nate.com
		return "get 요청 : " + m.getId() + "," + m.getUsername() + "," + m.getPassword() +","+m.getEmail();
	}
	//GET 방식의 요청에서 어떤 데이터를 요청하는 방법은 쿼리스트링밖에 없음
	//이렇게 파싱해서 값을 클래스에 매핑해주는걸 MessageConverter (스프링부트) 가 수행
	

	//인터넷 브라우저요청은 get만 되니 이 밑 주소들은 브라우저에 치면 오류날거 주소창에서는 post put delete요청을 할 수 없음
	//그래서 우리는 postman을 설치한거 밑에거 테스트 해보려고
	//http://localhost:8000/blog/http/post   (insert)
	@PostMapping("/http/post")
	public String postTest(Member m) 
	{
		return "post 요청 : " + m.getId() + "," + m.getUsername() + "," + m.getPassword() +","+m.getEmail();
	}
	// POST ,PUT, DELETE는 쿼리스트링으로 데이터를 보내지 않고 패킷의 BODY부분에 데이터를 담아서 보냄
	//그래서 포스트맨의 body에 x-www-form-urlencoded로 요청하면 html의 <form> 태그로 요청하는방식으로 데이터전송
	
	@PostMapping("/http/post2")
	public String postTest2(@RequestBody String text) // 요청의 body값을 해당 변수에 넣어주는 어노테이션(http요청과 함께받은 json데이터를 java객체에 매핑하기위해 사용)
	{
		return "post 요청 : " + text;
	}
	//포스트맨의 post요청 body에 raw 방식으로 텍스트(평문 text/plain 이라고 포스트맨에 설정할수있음)를 요청하면 String 변수에 평문을 담아줌
	
	@PostMapping("/http/post3")
	public String postTest3(@RequestBody Member m) // 요청의 body값을 해당 변수에 넣어주는 어노테이션(http요청과 함께받은 json데이터를 java객체에 매핑하기위해 사용)
	{
		return "post 요청 : " + m.getId() + "," + m.getUsername() + "," + m.getPassword() +","+m.getEmail();
	}
	//포스트맨의 post요청 body에 raw로 json(application/json)을 설정하고 json 스타일의 값을 요청하면 json 안의 값을 파싱해 member 변수에 매핑해줌
	//이렇게 파싱해서 값을 클래스에 매핑해주는걸 MessageConverter (스프링부트) 가 수행
	/* json 형식의 값
	 * {
	 * 	"id" : 1,
	 *	    "username" : "a",
	 *     "password" : 123,
	 *     "email" : "S@S"
	 * }
	 */
	
	//http://localhost:8000/http/put    (update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) // 요청의 body값을 해당 변수에 넣어주는 어노테이션(http요청과 함께받은 json데이터를 java객체에 매핑하기위해 사용)
	{
		return "put 요청 : " + m.getId() + "," + m.getUsername() + "," + m.getPassword() +","+m.getEmail();
	}
	//이것도 post처럼 body로 데이터 요청
	
	//http://localhost:8000/http/delete   (delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() 
	{
		return "delete 요청";
	}
	//이것도 post처럼 body로 데이터 요청
}
