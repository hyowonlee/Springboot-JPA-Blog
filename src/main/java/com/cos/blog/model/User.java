package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//@DynamicInsert // insert할때 null인 필드를 제외시켜줌 이런 어노테이션은 너무 많이 쓰면 안좋음 그냥 구현해볼것
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌더패턴
//jpa 는 orm(object relational mapping)으로 객체와 관계형 데이터베이스를 매핑해주는걸로 java나 객체형 언어의 객체를 db에 자동으로 테이블로 매핑시켜주는것
//즉  orm -> Java(혹은 다른 언어) Object -> 테이블로 매핑해주는 기술이 orm
//db 테이블 생성
//이 클래스를 테이블화 시키려면 Entity라는 어노테이션 삽입
@Entity // User 클래스가 스프링부트 프로젝트가 실행될때 이 클래스를 통해서 맴버들을 읽어서 자동으로 mysql에 table이 생성됨
public class User {
	
	@Id // 이 변수가 PRIMARY KEY 라는걸 알려주는 어노테이션
	//** https://gmlwjd9405.github.io/2019/08/12/primary-key-mapping.html 기본키 매핑 넘버링 전략
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 넘버링 전략을 의미하며 프로젝트에서 연결된 db의 넘버링 전략을 따라간다는 의미로
	//오라클을 연결했다면 sequence 형식 mysql을 연결했다면 autoincrement를 사용한다는 의미 즉 db의 기본전략을 따라감
	private int id; //primary key (오라클에선 시퀀스, mysql에선 auto_increment)
	
	@Column(nullable = false, length = 30, unique = true) // username이 null이면 안되기 때문에 이 어노테이션으로 null이 안들어가게 해줌, 길이 최대 30
	private String username; // 아이디
	  
	@Column(nullable = false, length = 100) // 암호 길이를 길게 주는이유는 나중에 이 암호를 해쉬로 변경해 암호화해서 암호화된 pw를 db에 넣을거기 때문에
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	//@ColumnDefault("'user'") //회원가입을 할때 일단 디폴트값을 줄건데 " " 안에 ' ' 가 들어가 있어야됨 좀 독특 이게 문자라는걸 알려주기 위함 //DummyControllerTest에서 null값이 들어가는것 때문에 지워줌
	@Enumerated(EnumType.STRING)//db는 RoleType이라는게 없음 그래서 이 어노테이션을 붙여서 이 enum이 String이라고 알려줘야함
	private RoleType role; // Enum 타입을 쓰는게 정확함, enum을쓰면 어떤데이터의 도메인(들어갈수있는 값들)을 만들어줄수 있음 (admin, user만 들어가게 할수있음)
	//String으로 하면 실수가 있을수도 있으니 RoleType.java에서 선언한 enum타입으로 설정
	
	
	@CreationTimestamp //이 어노테이션쓰면 자바에서 현재 시간을 자동으로 입력해줌
	private Timestamp createDate; // 이 회원이 가입한 시간
	
	
	//당연히 서비스에서 mysql이 동작하고 있어야됨
}
