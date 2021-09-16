package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Reply {
	
	@Id // 이 변수가 PRIMARY KEY 라는걸 알려주는 어노테이션
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 넘버링 전략을 의미하며 프로젝트에서 연결된 db의 넘버링 전략을 따라간다는 의미로
	private int id; //primary key (오라클에선 시퀀스, mysql에선 auto_increment)
	
	@Column(nullable = false, length = 200)
	private String content;
	
	//어느 게시글의 답변인지 연관관계 필요
	@ManyToOne // 한 게시글에는 여러 답변이 있을수 있으니 many to one 자동으로 연관관계 foreign key 관계를 적용시켜줌
	@JoinColumn(name="boardId")
	private Board board; //db에는 int형태로 id가 저장됨
	
	// 누가 답변 작성했는지 
	@ManyToOne //foreignkey 관계 적용
	@JoinColumn(name="userId")
	private User user; //db에는 int형태로 id가 저장됨
	
	@CreationTimestamp
	private Timestamp createDate;
}
