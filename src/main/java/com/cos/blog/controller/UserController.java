package com.cos.blog.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class UserController {
	
	@Value("${cos.key}") //카카오 oauth에서 가져온 정보로 회원가입 시키기가 안되서 application.yml에 저장해놓은 cos.key 값으로 oauth 회원가입 비밀번호를 고정해놓고 그걸 가져다 쓰는 어노테이션
	private String cosKey; //카카오 oauth 비밀번호
	
	@Autowired //SecurityConfig.java에서 만들어준 bean을 di (세션 생성용 매니저)
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
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
	
	
	//카카오 oauth (원래 oauth할때 이렇게 직접 구현하진 않고 라이브러리를 사용해서 쉽게 구현하지만 oauth 개념을 이해하기위해 직접 만들어본것)
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) // return "redirect:/"로 종료후 메인 페이지로 가기에 @ResposeBody를 안씀
	{													   //이 매개변수는 카카오에서 인증이 완료되었다고 쿼리 스트링으로 반환해주는 code를 가져오는것
		
		//POST방식으로 key = value 데이터를 전달 (카카오쪽으로 access토큰을 요청하기 위해)
		//HttpHeader 오브젝트 생성
		RestTemplate rt = new RestTemplate(); // http 요청을 편하게 해주는 함수
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); // http 요청 해더에 mime타입 명시 (여기선 key = value 형태로 하겠다 즉 내가 보낼타입이 이거라는거)
		
		//카카오에서 access토큰을 요청하기 위해 전송해야될 데이터들을 담는과정
		//HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>(); // key = value형태의 body 데이터를 담을 오브젝트
		params.add("grant_type", "authorization_code");
		params.add("client_id", "96c3fff31177ce34a1d9f9105540d008");
		params.add("redirect_uri","http://localhost:8000/auth/kakao/callback");
		params.add("code", code); //code값은 카카오에서 로그인 요청이 정상 완료되면 쿼리스트링으로 반환해주는 값으로 매개변수로 가져옴
		
		//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
				new HttpEntity<>(params, headers); //이걸로 body데이터와 header값을 가진 entity가 된다
		
		// Http 요청하기 - post방식으로 - 그리고 response변수로 응답받음
		ResponseEntity<String> response = rt.exchange( //http 요청 보내는 함수
				"https://kauth.kakao.com/oauth/token", //토큰 요청 주소
				HttpMethod.POST, //http 요청 메소드
				kakaoTokenRequest, //http body 와 header 값
				String.class // 응답받을 클래스 (String데이터로 받겠다)
				);
		//여기 response에서 받은 access_token 값으로 카카오쪽에 저장된 사용자의 정보에 접근할 수 있는 권한이 생기는것
		
		ObjectMapper objectMapper =  new ObjectMapper();
		//OAuthToken은 model에서 accesstoken받으려고 만든 객체(자바에서 처리하기위해 자바 오브젝트로 바꿔준것)
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);  //응답받은걸 OAuthToken 클래스에 매핑하여 자바 오브젝트로 저장하겠다
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		//엑세스 토큰 발급 완료
		System.out.println("카카오 엑세스 토큰 : "+ oauthToken.getAccess_token());
		
		
		//----------이 밑은 엑세스 토큰을 통한 사용자 정보 조회 --------------------------------------------------------
		
		//POST방식으로 key = value 데이터를 전달 (카카오쪽으로 access토큰을 요청하기 위해)
		//HttpHeader 오브젝트 생성
		RestTemplate rt2 = new RestTemplate(); // http 요청을 편하게 해주는 함수
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); // http 요청 해더에 mime타입 명시 (여기선 key = value 형태로 하겠다 즉 내가 보낼타입이 이거라는거)
		
		//HttpHeader를 하나의 오브젝트에 담기, 여기선 body데이터를 요구하지 않아서 헤더만 해주면 됨
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
				new HttpEntity<>(headers2); //이걸로 header값을 가진 entity가 된다
		
		// Http 요청하기 - post방식으로 - 그리고 response변수로 응답받음
		ResponseEntity<String> response2 = rt2.exchange( //http 요청 보내는 함수
				"https://kapi.kakao.com/v2/user/me", //토큰 요청 주소
				HttpMethod.POST, //http 요청 메소드
				kakaoProfileRequest2, //http body 와 header 값
				String.class // 응답받을 클래스 (String데이터로 받겠다)
				);
		//여기 response에서 받은 access_token 값으로 카카오쪽에 저장된 사용자의 정보에 접근할 수 있는 권한이 생기는것
		
		ObjectMapper objectMapper2 =  new ObjectMapper();
		//kakaoProfile은 model에서 카카오에서 받은 정보를 객체화 시키려고 만든 객체(자바에서 처리하기위해 자바 오브젝트로 바꿔준것)
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);  //응답받은걸 kakaoProfile 클래스에 매핑하여 자바 오브젝트로 저장하겠다
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		//카카오에서 받아온 정보 출력
		System.out.println("카카오 아이디"+kakaoProfile.getId());
		System.out.println("카카오 이메일"+kakaoProfile.getKakao_account().getEmail());
		
		//카카오 정보를 받아서 회원가입(model의 User 오브젝트를 생성)해줘야하는데 그때 필요한 정보들이 username, password, email (나머지는 자동으로 넣어지는거나 어차피 들어갈게 정해져있는것)
		System.out.println("블로그서버db에 저장할 유저네임 :" + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
		System.out.println("블로그서버db에 저장할 이메일 :" + kakaoProfile.getKakao_account().getEmail());
		//카카오에서 로그인과는 달리 우리 db에 들어갈 pw를 따로 공통 패스워드로 지정 (cosKey값은 application.yml에 설정)
		System.out.println("블로그서버db에 저장할 패스워드 :" + cosKey);
		
		//카카오에서 온 정보를 통해 빌더패턴으로 회원가입 시키기
		User kakaoUser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId())
				.password(cosKey)
				.email(kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();
		
		//가입자 혹은 비가입자 체크 후 처리
		User originUser = userService.회원찾기(kakaoUser.getUsername());
		// -----비가입자인 경우 회원가입 실시
		if(originUser.getUsername() == null) 
		{
			System.out.println("기존 회원이 아니기에 자동 회원가입을 진행합니다.");
			userService.회원가입(kakaoUser);
		}
		//-----회원가입 후 또는 원래 있던 회원인 경우 로그인 진행
		System.out.println("자동 로그인 진행");
		//-----로그인 처리
		//(UserApiController.java에서 가져옴) 세션 등록(로그인이라고 봐도 될듯?),  authenticationManager가 토큰을 이용해서 Authentication 객체 생성
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
		//(UserApiController.java에서 가져옴) 만들어진 Authentication객체를 시큐리티 컨택스트에 집어넣어서 세션 등록
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "redirect:/"; //회원가입 및 로그인 처리 완료
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm()
	{
		return "user/updateForm";
	}
}
