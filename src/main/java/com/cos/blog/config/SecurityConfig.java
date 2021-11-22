package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;

//이 파일은 스프링 시큐리티 설정 파일
//시큐리티는 모든 리퀘스트를 가로채서 먼저 아래의 설정들을 확인하는 필터링 동작함

//이 아래 3개의 어노테이션은 사실상 세트임 이해가 안가면 3개그냥 세트로 걸어주면 된다함 ㅋㅋ
//빈 등록: 스프링 컨테이너에서 객체를 관리할 수 있게 객체를 빈으로 등록해주는것 (스프링에서 직접 관리하는 객체를 빈 이라고 함)
@Configuration //설정파일 bean 등록하는 어노테이션 (IOC로 관리)
@EnableWebSecurity  //시큐리티 필터가 등록이 된다 (이 시큐리티 필터에 대한 설정을 이 파일에서 하겠다는 어노테이션)
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근시 요청 실행전 미리 권한 및 인증을 체크하겠다는 뜻
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean // BEAN등록으로 이 함수가 리턴하는 해쉬값을 스프링이 관리하게 IOC 됨
	public BCryptPasswordEncoder encodePWD() //UserService.java 에서 사용 해당 문자열을 해쉬 암호화 시키는 해쉬함수
	{
		return new BCryptPasswordEncoder(); //스프링 시큐리티가 들고있는 함수
	}
	
	// 시큐리티가 대신 로그인 해주는데 password를 가로채기 하는데 해당 pw가 어떤 함수로 해쉬가 돼서 회원가입이 되었는지 알아야 같은 해쉬함수로 암호화 해 db에 있는 해쉬랑 비교 가능
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD()); //UserDetails타입으로 만들어준 객체를 가져와서 password를 encodePWD()로 encode해서 비교해줌
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception { //스프링 시큐리티 설정
		http
			.csrf().disable() //csrf 토큰 비활성화 (테스트시 걸어두는게 좋음 테스트 끝나고 다 완성된다면 나중에 활성화) 요청시 csrf 토큰없으면 시큐리티가 자동으로 막음
			//우린 form태그로 데이터를 요청하는게 아닌 자바스크립트 ajax로 통신을 요청하기에 csrf 토큰이 없어서 스프링 시큐리티가 자동으로 막음 그래서 토큰 비활성화
			.authorizeRequests()
				.antMatchers("/auth/**", "/", "/js/**", "/css/**", "/image/**", "/dummy/**") // 이 경로로 들어오는 것들은
				//antMatchers에서 /, js, css, image경로는 왜 넣어줬냐면 회원가입할때 user.js에 가서 ajax통신을 해야하는데 권한이 없어서 못가서 회원가입이 안됐음
				//그리고 user.js의 ajax통신쪽 보면 회원가입 후 최상위경로인 /로 가게되어있는데 그것도 권한이 없어서 회원가입이 안됐음 그래서 /랑 /js/**경로도 포함시킴
				//혹시나해서 css, image도 경로 추가함
				.permitAll() //인증없이 누구나 들어올수 있다
				.anyRequest() // antMathcers외의 다른 모든 요청들은
				.authenticated() // 인증이 되어야 접속이 가능하다
			.and() //위 것들만 쓰면 권한없이 못들어가는 인증이 필요한 나머지 모든 페이지들은 스프링 시큐리티 디폴트 로그인 페이지도 안나오고 아예 접근불가하기에
				.formLogin()
				.loginPage("/auth/loginForm") // 나머지 모든 페이지로 오는 요청들은 우리가 만든 로그인 페이지로 접속시키겠다
				.loginProcessingUrl("/auth/loginProc") // 로그인 페이지에서 로그인 버튼을 누르면 form을 통해 이 주소로 요청을 보냄, 스프링 시큐리티가 이 주소로 오는 로그인 요청을 가로채서 대신 로그인처리를 해준다 
				// loginForm.jsp에서 form태그에서 보내는 url로 컨트롤러를 만들지 않고 스프링 시큐리티 설정에서 처리 (바로 위의 configure(AuthenticationManagerBuilder auth) 함수로 보냄)
				.defaultSuccessUrl("/"); //로그인이 정상적으로 끝나면 이 경로로 보내겠다 failureUrl()를 쓰면 실패시 어디로 보낼지도 설정 가능
		
				// 스프링 시큐리티에서 로그아웃은 그냥 자동으로 localhost:8000/logout 주소가 로그아웃이 될 수 있도록 하는 주소로 로직이 디폴트로 만들어져 있음
	}			

}
