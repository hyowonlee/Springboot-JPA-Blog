package com.cos.blog;

import org.junit.Test;

import com.cos.blog.model.Reply;


public class ReplyObjectTest {

	@Test // 테스트코드로 실행하려면 junit이 들고있는 테스트 어노테이션을 적어줌 이 파일 화면에서 오른쪽 버튼 runas 해서 junit test 실행하면됨
	public void tostring테스트()
	{
		Reply reply = Reply.builder()
				.id(1)
				.user(null)
				.board(null)
				.content("안녕")
				.build();
		
		System.out.println(reply); //오브젝트 출력시에 toString()이 자동 호출됨
	}
}
