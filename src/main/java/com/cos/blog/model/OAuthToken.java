package com.cos.blog.model;

import lombok.Data;

//usercontroller.java에서 카카오 로그인에서 사용
//카카오 로그인 성공시 access 토큰을 받을 객체
//카카오 oauth를 자바에서 처리하기위해 자바 오브젝트로 바꿔준것

@Data //getter setter 생성
public class OAuthToken {
	//OAuth 에서 받은 json 데이터를 파싱하기에 변수이름이 json key이름과 같아야 한다 변수이름이 다르면 오류, getter setter 없어도 오류
	private String access_token;
	private String token_type;
	private String  refresh_token;
	private int expires_in;
	private String scope;
	private int refresh_token_expires_in;
}
