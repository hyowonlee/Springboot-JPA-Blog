package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cos.blog.model.User;

//이 인터페이스는 db의 user테이블을 관리하는 repository이고 이 테이블의 primarykey는 integer라고 정의
//이놈의 부모가 테이블의 값들을 crud 하게 해주는 함수들을 가지고 있음 즉 이놈은 DAO라고 보면 됨
//이놈은 JpaRepository를 상속받고있어 자동으로 bean등록이 됨 그래서 @Repository 이걸 생략가능하다
//예전엔 @Repository가 있어야 스프링이 컴포넌트 스캔해서 bean으로 등록해 메모리에 띄워줬었음 이젠 생략가능하고 스프링이 처음에 컴포넌트 스캔할때 이놈을 메모리에 띄워줌
@Repository//생략가능하다
public interface UserRepository extends JpaRepository <User, Integer> //인터페이스가 인터페이스를 상속받을땐 extends 사용 
{
	//PrincipalDetailService.java에서 username을 db에서 찾을때 사용
	// jpa 네이밍 쿼리전략으로 SELECT * FROM user WHERE username =1?;  매개변수가 1? 자리에 들어갈것
	Optional<User> findByUsername(String username);
	
	
	
	//스프링 시큐리티를 사용하므로 이건 사용 안함
//	//JPA Naming 쿼리 전략 이란게 있음 이 전략에 맞게 함수 이름을 지어주면 jpa가 들고있는 함수가 아니어도 자동으로 이름을 분석해 쿼리 로직을 만들어줌
//	// 이 함수이름으론 SELECT * FROM user WHERE username = ?1 AND password = ?2;  username과 password의 ?1 ?2는 매개변수의 값이 들어가게됨
//	User findByUsernameAndPassword(String username, String password);
//	
//	
//	//@Query(value="SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true) //이런식으로 네이티브 쿼리를 직접 넣어줘서 만들수도 있음 위의놈과 똑같이 동작함
//	//User login(String username, String password);
}
