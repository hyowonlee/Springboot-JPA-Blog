package com.cos.blog.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.UserRepository;
import com.cos.blog.repository.ReplyRepository;

//이거 BoardService랑 같음 거기서 주석보셈

//스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IOC를 해준다 즉 컴포넌트 스캔 대상
@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository; // 이 객체를 통해 db 접근
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Transactional // 이 어노테이션을 붙여주면 이 함수 전체의 서비스가 하나의 트랜잭션으로 묶이게됨
	public void 글쓰기(Board board, User user) { //title, content를 가져오고 
		//글쓸때 유저 정보가 필요 그래서 유저정보를 board로 넘겨주려고 UserApiController에서 넘겨주는걸로 작업함
		board.setCount(0); //지정 안되어있는 값들 지정
		board.setUser(user);
		boardRepository.save(board); //저장으로 boardRepository.save()따라가서 db에 저장할것
	}
	
	//boardController에서 사용
	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable)
	{
		return boardRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Board 글상세보기(int id)
	{
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패: 글 id를 찾을 수 없습니다.");
				});
	}
	
	@Transactional
	public void 글삭제하기(int id)
	{
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정하기(int id, Board requestBoard)
	{
		//수정하려면 먼저 영속화를 시킴
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패: 글 id를 찾을 수 없습니다.");
				}); //영속화 완료 지금 board의 값은 db의 데이터와 동기화 되어있는 상태임 여기서 수정시 서비스종료할때 db에서도 수정됨
		
		board.setTitle(requestBoard.getTitle()); //updateForm에서 받았던 값으로 타이틀 수정
		board.setContent(requestBoard.getContent());//updateForm에서 받았던 값으로 내용 수정
		//해당함수 종료시(Service가 종료될때) 트랜잭션이 종료됨 이때 더티체킹으로 영속화된 board의 데이터가 db와 달라졌기에 더티체킹을 통해 db에 자동업데이트 해줌 (db쪽으로 flush됨 즉 commit이 된다는소리)
	}
	
	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto)
	{
//		//이건 dto 방식
//		Board board = boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(()->{
//			return new IllegalArgumentException("댓글쓰기 실패: 게시글 id를 찾을 수 없습니다.");
//			//영속화 완료
//		}) ;
//		
//		User user = userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(()->{
//			return new IllegalArgumentException("댓글쓰기 실패: 유저 id를 찾을 수 없습니다.");
//			//영속화 완료
//		}) ;
//		
//		//빌더패턴을 통한 객체 생성 생성자
//		Reply reply = Reply.builder()
//				.user(user)
//				.board(board)
//				.content(replySaveRequestDto.getContent())
//				.build();
//		replyRepository.save(reply);
		
		//native query방식으로 쿼리는 ReplyRepository.java에 다 작성되어 있음 이 함수를 불러서 native query문을 실행함
		int result = replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
		System.out.println("수정된 행의 수 = " + result);
		
	}
	
	@Transactional
	public void 댓글삭제(int replyId)
	{
		replyRepository.deleteById(replyId);
	}
	
	
	
//	public void 댓글쓰기(User user, int boardId, Reply requestReply)
//	{
//		Board board = boardRepository.findById(boardId).orElseThrow(()->{
//			return new IllegalArgumentException("댓글쓰기 실패: 게시글 id를 찾을 수 없습니다.");
//			//영속화 완료
//		}) ;
//		
//		requestReply.setUser(user);
//		requestReply.setBoard(board);
//		
//		replyRepository.save(requestReply);
//	}
	
	//스프링 시큐리티를 사용하므로 이건 사용 안함
//	@Transactional(readOnly = true) //readOnly = true를 통해 select할때 트랜잭션 시작, 서비스 종료시 트랜잭션 종료 (트랜잭션 동작동안 정합성(데이터가 항상 같도록 보장)을 유지시킬 수 있음)
//	public User 로그인(User user) { // 이 서비스는 로그인만하니까 select만 수행
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
}
