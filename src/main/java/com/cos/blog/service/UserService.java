package com.cos.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;


//스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IOC를 해준다 즉 컴포넌트 스캔 대상
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository; // 이 객체를 통해 db 접근

	//SecurityConfig에서 bean등록해놓은것으로 di(autowired) 됨
	@Autowired
	private BCryptPasswordEncoder encoder; //해쉬 암호화 시키려고 만든 변수

	
	@Transactional // 이 어노테이션을 붙여주면 이 함수 전체의 서비스가 하나의 트랜잭션으로 묶이게됨
	public void 회원가입(User user) {
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);//스프링 시큐리티가 들고있는 함수로 해당 문자열을 해쉬 암호화 시킴
		user.setPassword(encPassword); //해쉬암호화 시킨 값으로 암호 설정
		user.setRole(RoleType.USER); //UserApiController.java에서 웹페이지에서 안받아온 정보인 role을 여기서 입력
		userRepository.save(user); //insert 해주는 것으로 userRepository.save()따라가서 db에 저장할것
	}

	
	@Transactional // 이 어노테이션을 붙여주면 이 함수 전체의 서비스가 하나의 트랜잭션으로 묶이게됨
	public void 회원수정(User user) { //우리가 받은 이 user는 웹으로 부터 받은 user
		//수정시에는 영속성 컨텍스트 User 오브젝트를 영속화를 시키고(select 한번해서 시킬것), 영속화된 User 오브젝트를 수정
		//select해서 User오브젝트를 db로 부터 가져오는 이유는 영속화하기 위해서!!
		//영속화된 오브젝트를 변경하면 자동으로 db에 update문을 날려주니
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원 찾기 실패");
		});
		String rawPassword = user.getPassword(); // 지금 회원 수정 페이지에서 수정한 패스워드를 들고와서
		String encPassword = encoder.encode(rawPassword); //암호화
		persistance.setPassword(encPassword); // 암호화한 비밀번호를 User오브젝트에 설정
		persistance.setEmail(user.getEmail()); //회원 수정 페이지에서 수정한 이메일을 들고와서 User오브젝트에 설정

		
		//회원수정 함수 종료 == 서비스종료 == 트랜잭션종료(@Transactional이니) == commit이 자동으로 됨(영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 변화된것들을 자동으로 update문 날려줌)
	}
	
	//스프링 시큐리티를 사용하므로 이건 사용 안함
//	@Transactional(readOnly = true) //readOnly = true를 통해 select할때 트랜잭션 시작, 서비스 종료시 트랜잭션 종료 (트랜잭션 동작동안 정합성(데이터가 항상 같도록 보장)을 유지시킬 수 있음)
//	public User 로그인(User user) { // 이 서비스는 로그인만하니까 select만 수행
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
}
