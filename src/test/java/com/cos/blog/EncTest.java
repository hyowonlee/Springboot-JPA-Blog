package com.cos.blog;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncTest {
	
	//스프링 시큐리티의 비밀번호 해쉬 암호화 함수 테스트
	@Test
	public void 해쉬_암호화테스트()
	{
		String encPassword = new BCryptPasswordEncoder().encode("1234");
		System.out.println("1234의 해쉬값 :"+encPassword);
	}
}
