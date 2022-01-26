package com.cos.blog.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.cos.blog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{
	
	//댓글 작성할때 dto 같은 방식을 쓸수도 있지만 그럼 복잡해짐 그래서 쿼리를 직접 넣는 네이티브 쿼리 방식으로 할수도 있다
	//이건 jpa 네이밍 전략 아니라 쿼리 직접넣음
	//?1 ?2 ?3는 들어가는 변수 여기선 replySaveRequestDto의 필드를 순서대로 1, 2, 3에 받아옴 
	// nativeQuery = true로 적어넣어놔서 내가 직접 적은 쿼리를 실행 이렇게 해주면 영속화 해줄 필요가없음 직접 쿼리로 쏴주니
	@Query(value = "INSERT INTO reply(userId, boardId, content, createDate) VALUES(?1, ?2, ?3, now())", nativeQuery = true)
	@Modifying //transactional하니 오류났음 modifying으로 수정
	int mSave(int userId, int boardId, String content); // jdbc가 insert update delete실행시 리턴값을 업데이트 된 행의 개수를 리턴해줌 반환값을 int로 해서 찍어보면 행의 개수가 출력됨
		
}