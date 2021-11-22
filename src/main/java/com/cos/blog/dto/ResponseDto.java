package com.cos.blog.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//lombok 어노테이션
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//lombok 어노테이션 끝
public class ResponseDto<T> {
	int status;
	T data;
}
