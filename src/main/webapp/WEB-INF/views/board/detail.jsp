<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<%-- 이파일 위치가 board 안이니 한칸 위로 가서 header를 찾아야함 --%>
<%-- header는 모든 페이지에 들어가야 하니 따로 만들어주고 include만 해준다 --%>

<div class="container">
	<button class="btn btn-secondary" onclick="history.back()">돌아가기</button>
	<%-- history.back() 이전페이지 가는거인듯--%>
	<c:if test="${board.user.id == principal.user.id}">
		<%-- 삭제랑 수정버튼은 글만든 사람과 로그인한 사람의 id가 같아야만 보이게 함 여기서 principal은 header.jsp에서 가져왔는데 PrincipalDetail.java를 뜻하는거--%>
		<a href="/board/${board.id}/updateForm" class="btn btn-warning">수정</a>
		<button id="btn-delete" class="btn btn-danger">삭제</button>
	</c:if>
	<br />
	<br />
	<div>
		글 번호 : <span id="id"><i>${board.id} </i></span> 작성자 : <span><i>${board.user.username} </i></span>
		<%-- board에 대해 select 하면 Board.java보면 user정보랑 reply정보를 eager전략으로 같이 들고와서 사용 가능 --%>
	</div>
	<br />
	<div>
		<h3>${board.title}</h3>
	</div>
	<hr />
	<%-- 화면에 선하나 긋는거 --%>
	<div>
		<div>${board.content}</div>
	</div>
	<hr />

	<div class="card">
		<form>
		<input type="hidden"  id="userId" value="${principal.user.id}">
		<input type="hidden"  id="boardId" value="${board.id}">
		<div class="card-body">
			<textarea id="reply-content" class="form-control" rows="1"></textarea>
		</div>
		<%-- 댓글 쓰는창 --%>
		<div class="card-footer">
			<button type="button" id="btn-reply-save" class="btn btn-primary">등록</button> <%-- 이거 타입을 버튼으로 표시안해주면 댓글등록시 그냥 submit이 되버림 --%>
		</div>
		<%-- 댓글등록 버튼 --%>
		</form>
	</div>
	<br />
	<div class="card">
		<div class="card-header">댓글 리스트</div>
		<ul id="reply-box" class="list-group">
			<c:forEach var="reply" items="${board.replys}">
				<%-- 원래 list-group-item은 block 형태이지만 부트스트랩에선 d-flex를 써주면 flex형태로 바뀜 / justify-content-between는 안의 태그들을 양쪽으로 찢어놓는 형태로 바꿔주는 부트스트랩 클래스 --%>
				<li id="reply-${reply.id}" class="list-group-item d-flex justify-content-between">
					<div>${reply.content}</div>
					<div class="d-flex">
						<div class="font-italic">작성자 : ${reply.user.username} &nbsp;</div>
						<c:if test="${reply.user.id eq principal.user.id}">
							<button onclick="index.replyDelete(${board.id}, ${reply.id})" class="badge">삭제</button>
						</c:if>
					</div>
				</li>

			</c:forEach>
		</ul>
	</div>
</div>


<script src="/js/board.js"></script>
<%-- ajax요청용 js코드 --%>
<%@ include file="../layout/footer.jsp"%>
<%-- footer는 모든 페이지에 들어가야 하니 따로 만들어주고 include만 해준다 --%>


