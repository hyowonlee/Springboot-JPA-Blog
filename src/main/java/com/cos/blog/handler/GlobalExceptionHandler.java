package com.cos.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;

@ControllerAdvice // 모든 exception이 발생했을때 이 클래스로 들어오게하는 어노테이션
@RestController
public class GlobalExceptionHandler {

//		@ExceptionHandler(value=IllegalArgumentException.class) // IllegalArgumentException오류가 나오면 이 함수의 매개변수 e로 에러를 전달해줌 그리고 여기서 리턴하는걸 리턴해줌
//		public String handleArgumentException(IllegalArgumentException e)
//		{
//			return "<h1>"+e.getMessage()+"</h1>";
//		}
		
//		@ExceptionHandler(value=Exception.class) // 이렇게 최상위 Exception을 해주면 모든 예외가 이쪽으로 오게됨
//		public String handleException(Exception e)
//		{
//			return "<h1>"+e.getMessage()+"</h1>";
//		}
	
	@ExceptionHandler(value=Exception.class) // 이렇게 최상위 Exception을 해주면 모든 예외가 이쪽으로 오게됨
	public ResponseDto<String> handleException(Exception e)
	{
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}
	
	
}
