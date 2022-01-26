package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌더패턴
@Entity
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)// AUTO _ INCREMENT
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob // 대용량 데이터
	private String content ;// 섬머노트 라이브러리 사용할거라 우리가 적는 글이 디자인이 되서 html태그가 섞여서 들어감 그래서 용량이 커져서 들어가서 @Lob 사용
	
	//@ColumnDefault("0")//게시물 처음 만들면 조회수 0이니 디폴트값 0 //우리가 값 넣는다고 주석처리
	private int count; //조회수
	
	
	@ManyToOne(fetch = FetchType.EAGER)// board가 many, user는 one 한명의 유저는 여러개의 게시글을 쓸 수 있다 (user와의 연관관계를 위해 추가)
	//지금 board 테이블 안에 user가 있는거랑 같은건데 user는 한명 board는 여러개가 되니 many(board) to one(user) 관계를 위해 적은것
	//이렇게 적어주면 자연스럽게 board 테이블 안에 user라는 foreign키가 있는걸로 db에서 연관관계 표현이 됨(즉 자바를 통해 외래키를 만든것)
	//fetch = FetchType.EAGER은 내가 board테이블을 select하면 이 userid값을 반드시 가져오겠다는 의미(manytoone의기본전략)->userid는 1개밖에 없으니 가져올수있음
	//fetch안쓰면 자동으로 기본전략으로 들어감
	@JoinColumn(name="userId") // 실제로 db에 만들어질땐 필드값은 userId로 만들어 질 것 다른 객체(entity)를 가져오는것이니 join인 어노테이션 사용
	private User user; //db에는 알아서 foreign키로 user의 id가 저장이 됨 (int형태로 id가 저장됨)
	//db는 오브젝트를 저장할 수 없다. 자바는 오브젝트를 저장할 수 있다. 보통 작성자를 db에서 표현할땐 자바가 db에 맞춰서 원래는 foreign키로 저장하게 되는데 jpa(orm)에선 foreign키로 저장하지 않고 오브젝트로 저장할 수 있다. 이런식으로 작성하면 자동으로 외래키로 표현해줌
	
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) // reply 테이블의 board(변수이름)으로 mappedBy가 적혀있으면 난 fk가 아니라는 뜻(db에 만들어지는 값이 아니라는거)
	//(연관관계의 주인이 아님)그러니 db에 column을 만들지 마라 fk는 reply에 있는 boardId임 이 변수는 그냥 board를 select할때 join을 통해서 답글 값을 얻기위한 것임
	//onetomany이면 이 값이 여러개가 될 수 있음 그래서 기본전략이 fetch = FetchType.LAZY로 필요하면 가져오고 아니면 안가져오는 전략
	//하지만 우리는 처음부터 댓글이 보이게 될 거니 반드시 가져와야되는 정보로 eager전략으로 바꿔줄것 (fetch안쓰면 자동으로 기본전략으로 들어감)
	//cascade = CascadeType.REMOVE는 게시글이 삭제될때 댓글이 연결되어있어서 삭제가 안됐음 그래서 cascade로 삭제 하면 댓글도 같이 삭제되게함
	@JsonIgnoreProperties({"board"}) //jpa에선 연관관계의 객체를 vo에 넣어버려서 jpa가 데이터를 가져오려고 jackson을 통해 json으로 파싱해줄때 그 객체 안에 있는 또 다른 객체를 불러 loop하게 되는 문제가 있는데 이 어노테이션을 써주면 이 reply객체 안에 있는 board는 부르지 않고 무시함
	@OrderBy("id desc") // db에서 가져올때 id값 내림차순으로 정렬됨
	private List<Reply> replys;// replys id는 fk로 있으면 안됨 왜냐면 답글이 2개면 2개가 들어갈텐데 그럼 도메인이 원자값에 위배됨
	
	//즉 board에서 글을 보면 userid는 반드시 가져와야하지만 댓글은 [더보기] 이런걸로 가려놓으면 댓글은 더보기전까진 필요없음 그러니 이럴땐 lazy전략
	//하지만 처음부터 댓글이 보이면 reply도 eager전략이 되어야 할것
	//사실 reply테이블에서 board를 fk로 가지고 있기때문에 이미 관계가 매핑되어있음 그래서 여기엔 reply정보가 없어도 되지만 board를 select할때 댓글도 같이가져오면 상세보기할때 좋아서 양방향 매핑을 하는것
	
	@CreationTimestamp
	private Timestamp createDate; // 데이터 insert update 될때 자동으로 값이 들어감
}
