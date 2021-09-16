package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cos.blog.model.User;

@Repository
public interface UserRepository extends JpaRepository <User, Integer> //인터페이스가 인터페이스를 상속받을땐 extends 사용 
// 이 인터페이스는 user테이블을 관리하는 repository이고 이 테이블의 primarykey는 integer라고 정의
//이 부모가 테이블의 값들을 crud 하게 해주는 함수들을 가지고 있음 즉 이놈은 DAO라고 보면 됨
//이놈은 JpaRepository를 상속받고있어 자동으로 bean등록이 됨 그래서 @Repository 이걸 생략가능하다
//예전엔 이게 있어야 스프링이 컴포넌트 스캔해서 bean으로 등록해 메모리에 띄워줬었음 그러니 이놈은 스프링이 처음에 컴포넌트 스캔할때 이놈을 메모리에 띄워줌
{
	
}
