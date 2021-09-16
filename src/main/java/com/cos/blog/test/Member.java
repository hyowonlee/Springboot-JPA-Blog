package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// **lombok이란 - 라이브러리로서 생성자 getter setter 등을 밑에 어노테이션으로 import하여 자동으로 만들어주는 것 
//HttpControllerTest에 테스트 있음
//@Getter //lombok을 통한 getter 생성 어노테이션 ctrl space 눌러서 생성
//@Setter //lombok을 통한 setter 생성 어노테이션 ctrl space 눌러서 생성
@Data //lombok을 통한 getter, setter 동시 생성 어노테이션 ctrl space 눌러서 생성
//@AllArgsConstructor //lombok을 통한 모든 필드를 다 쓰는 생성자 생성 어노테이션 ctrl space 눌러서 생성
@NoArgsConstructor //lombok을 통한 빈생성자 생성 어노테이션 ctrl space 눌러서 생성
//@RequiredArgsConstructor //lombok을 통한 final 필드에 대한 생성자 생성 어노테이션 ctrl space 눌러서 생성
public class Member {
	private int id;
	private String username;
	private String password;
	private String email;
	
	@Builder // lombok 어노테이션으로서 builder패턴을 만들어줌
	//원래는 new 해서 생성자로 생성할때 매개변수가 똑같이 있어야 작동되지만 . 을 통해 원하는 매개변수만 넣게해줄수 있다 장점은 생성자의 매개변수 순서, 매개변수 개수를 안지켜도 됨
	//HttpControllerTest에 테스트 있음
	public Member(int id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	
	
}
