package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//회원가입 insert 연습을 위한 dummy data 생성용

//사용자가 한 요청에 대한 응답을 해주는 컨트롤러 @RestController (data를 응답해줌)
@RestController
public class DummyControllerTest {
	
	// com.cos.blog.repository.UserRepository 인터페이스 파일에서 구현한 그 인터페이스
	@Autowired //원래 이 변수엔 아무것도 안넣어줘서 null 이지만 이 어노테이션을 붙여주면 DummyControllerTest클래스가 메모리에 뜰때
	//이 userRepository도 메모리에 같이 뜨게되는데 @Autowired이게 지금 이 타입으로 메모리에 있는 객체를 여기에 넣어주라는 의미이기 때문
	//UserRepository이놈은 @Repository(생략가능)가 붙어있으니 처음 컴포넌트 스캔할때 뜬 놈이 여기에 들어가게 될것 이게 DI임
	private UserRepository userRepository; 
	
	
	/* select하는 방법들 */
	//{id} 주소로 파라메터를 전달 받을 수 있음
	// http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) // 위의 getmapping 의 변수이름과 같이 적어줘야 매핑됨 {"id"}, int "id"
	{
		//findById()는 Optional 객체를 반환하는데
		// 내가 user를 id로 db에서 못찾게되면 user가 null이 될거니까 문제발생가능
		//그래서 Optional로 User 객체를 감싸서 가져올테니 null인지 아닌지 추가적인 .함수로 판단해서 return하라
		
//		  User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
//		  //orelseget은 없으면 빈 User 객체하나 만들어서 user에 넣어달라는거 그럼 null이 아니니
//		  // Supplier는 인터페이스로 new 할수 없지만 익명 클래스를 만들어주면 객체 생성이 가능 그래서 내부 구성요소를 여기서 바로 구현
//		  // 찾는 해당 id가 없는 경우 이 부분을 실행하게 되겠지만 값이 있으면 이건 실행 안함 즉 orelseget은 없는경우만 Supplier 객체로 만들어져서 값이 있으면 해당 user 객체가 잘 들어감
//		  @Override 
//		  public User get() { 
//			  // TODO Auto-generated method stub
//			  return new User(); // id가 없는것을 찾게되면 이 빈객체를 User user 여기에 넣어주게 될거임
//		  } });
		
//		// 이런 기본 get 방법으로 해줘도 illegalArgument와 거의 동일하게 됨 하지만 오류가났을때 해당오류에 대한 메시지를 개별적으로 띄워줄 수 없음
//		  User user = userRepository.findById(id).get(); 
//		  return user;
//		 
		
//		//이렇게 람다식을 사용할 수도 있음 이런경우 매개변수에 어떤게 들어오는지 타입을 신경쓰지 않아도 됨 이게 좋긴 함
//		User user = userRepository.findById(id).orElseThrow(()->{
//			return new IllegalArgumentException("해당 id의 유저는 없습니다. id:" + id);
//		});
		
		//보통 위의 방식이 아닌 이 방법을 쓰라고 나와있음 없는 매개변수 예외를 날려주는 것
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 id의 유저는 없습니다. id:" + id); //만약 없는 값을 찾아서 null이 나오면 예외처리시켜줌
			}
		});
		
		// user 객체 = 자바 오브젝트 우리가 RestController라 html파일이 아니라 data를 리턴해줌
		// 근데 요청을 웹에서 했으니 웹브라우저는 자바 오브젝트를 이해 못함 그래서 user객체를 웹브라우저가 이해할수있게 변환해야됨
		// 그래서 user 자바 객체를 json으로 변환해줌
		//예전엔 Gson같은 라이브러리를 써서 자바 오브젝트를 json으로 변경해서 던졌지만 스프링 부트는 MessageConverter라는 애가 응답시에 자동 작동
		//만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서 오브젝트를 json으로 변환해서 브라우저에 던져줌
		return user; //이제 저 위의 주소로 get요청을 보내면 해당 객체의 멤버들이 json타입으로 잘 가져와진걸 볼수있음 없는 id는 오류페이지 나옴
	}
	
	//전체 정보 select
	// http://localhost:8000/blog/dummy/users
	@GetMapping("/dummy/users")
	public List<User> list() //전체 정보를 select 하는 함수라 List로 모든 정보들을 받아와줌
	{
		return userRepository.findAll(); // 전체정보를 다 리턴해줌
	}
	
	//한페이지당 2건의 데이터를 리턴받아볼것 (간단하게 텍스트로 페이징을 해볼것 다른거에선 페이지 만들어야되지만 스프링부트엔 pageable이란게 있다)
	// http://localhost:8000/blog/dummy/user                      다음 페이지 볼려면 뒤에 url의 ?page=0부터 첫페이지가 시작해서 이 값을 올려주면 됨
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction=Sort.Direction.DESC) Pageable pageable) //한페이지당 2건씩 sort는 id로 id를 desc로 최신순으로 볼거다
	{ //Page를 통해 간단한 페이지형태의 리턴을 할수있게됨 url뒤에 쿼리스트링으로 ?page=0 으로 첫번째 페이지부터 불러올수있음
		Page<User> pagingUser = userRepository.findAll(pageable); // 이놈이 페이지를 만들어주는데 필요한 데이터인 content말고 설정값들도 같이 리턴해줌 (이 pagingUser리턴하면 설정값 볼 수 있음 해보고싶으면 이 함수 리턴값도 Page<User>로 바꿔주셈)
		
		List<User> users = pagingUser.getContent(); // 이렇게 pagingUser의 .getContent()를 쓰면 List형태로 필요한 데이터인 Content만 리턴해준다 이렇게 List로 리턴해주는게 좋데 이외에 Page클래스는 isFirst(), isLast()같은 if로 분기처리 할수있는 함수들도 지원해줌 
		
		return users; // 다끝나면 결과는 List형태로 리턴해주는게 좋다
	}
	
	/* insert하는 방법들 */
	
	@PostMapping("/dummy/join1") //insert 할거니까 post로 날려야됨
	public String join(String username, String password, String email) //<form>태그의 값(x-www-form-urlencoded)을 받을때는 key=value형태로 값을 받음, 변수명이 정확해서 @RequestParam 어노테이션이 필요없는거
	{
		//user의 id는 db에 데이터가 들어가면 자동으로 autoincrement되고 role도 디폴트user로 들어가고 시간도 자동으로 들어가니 나머지만 받으면 됨
		System.out.println("username: "+username);
		System.out.println("password: "+password);
		System.out.println("email: "+email);
		
		return "회원가입이 완료되었습니다.";
	}
	//public String join(@RequestParam("username") String username)
	//원래라면 이렇게 어노테이션을 붙어서 실제로 받는 키 값을 적어줘야함 key(괄호안username)=value(변수username)형태의 값을 받음
	//하지만 변수명과 요청시의 key값이 같게 정확히 적어주면 http의 body에 username, password, email 데이터를 가지고 요청시 스프링 매개변수이름에서 key를 찾아 파싱해서 value값을 자동으로 넣어줌
	
	
	@PostMapping("/dummy/join")
	public String join(User user) //더쉬운 방법은 매개변수를 object로 받아주면 됨
	{
		System.out.println("id: "+user.getId()); // 지금은 username pw email만 post로 받았으니 이건 null(0)값으로 데이터를 받게됨
		System.out.println("username: "+user.getUsername());
		System.out.println("password: "+user.getPassword());
		System.out.println("email: "+user.getEmail());
		System.out.println("role: "+user.getRole()); // 지금은 username pw email만 post로 받았으니 이건 null(0)값으로 데이터를 받게됨
		System.out.println("createDate: "+user.getCreateDate()); // 지금은 username pw email만 post로 받았으니 이건 null(0)값으로 데이터를 받게됨
		
		user.setRole(RoleType.USER); // null값이 들어가면 안되니 enum인 RoleType으로 설정해줬었음 그래서 ENUM으로 USER값을 넣어줬음
		
		userRepository.save(user); // 데이터를 db에 insert해주는 함수
		/*User.java에 @DynamicInsert가 붙어있으면 null인 필드는 insert쿼리문에서 제외시켜줌
		*id는 autoincrement니까 자동으로 들어갈거고 date도 User.java에 @CreationTimestamp가 붙어있으니 자동으로 시간이 들어갈것
		*role은 디폴트값이 user로 설정되어있으니 user로 들어감 만약 role을 쿼리문에서 제외하지 않았으면 null이 들어있으니 db에 null이 들어가게될것
		*근데 이 방법은 안쓸거 어노테이션을 덕지덕지 붙이는건 좋지않고 직접 구현하는편이 잡기술이 아니고 좋음
		*/
		return "회원가입이 완료되었습니다.";
	}
	
}
