package com.cos.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//이걸 안만들어주면 시큐리티가 우리 정보를 몰라서 시큐리티의 기본 계정과 id로 세팅을 해버려서 유저를 사용 못함
//SecurityConfig.java 에서 사용하려고 만듬

@Service // bean 등록
public class PrincipalDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	// 스프링이 로그인 요청을 가로챌때 username, password 변수 2개를 가로채는데 pw부분처리는 알아서 함 username이 db에 있는지만 확인해주면 됨 그걸 여기서 한다
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User principal = userRepository.findByUsername(username) //pw 처리는 알아서 해주니 username 처리만 수행
				.orElseThrow(()->
				{
					return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다.:"+username);
				});
		return new PrincipalDetail(principal); //스프링 시큐리티가 사용할 수 있는 UserDetails 타입으로 리턴해줌 그래서 이때 시큐리티의 세션에 유저정보가 저장됨
	}
}
