<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>  <%-- 이파일 위치가 board 안이니 한칸 위로 가서 header를 찾아야함 --%>
<%-- header는 모든 페이지에 들어가야 하니 따로 만들어주고 include만 해준다 --%>

<div class="container">
		<button class="btn btn-secondary" onclick="history.back()">돌아가기</button> <%-- history.back() 이전페이지 가는거인듯--%>
		<c:if test="${board.user.id == principal.user.id}">  <%-- 삭제랑 수정버튼은 글만든 사람과 로그인한 사람의 id가 같아야만 보이게 함 여기서 principal은 header.jsp에서 가져왔는데 PrincipalDetail.java를 뜻하는거--%>
		<a href="/board/${board.id}/updateForm" class="btn btn-warning">수정</a>
		<button id="btn-delete" class="btn btn-danger">삭제</button>
		</c:if>
		<br /><br />
		<div>
			글 번호 : <span id="id"><i>${board.id} </i></span>
			작성자 : <span><i>${board.user.username} </i></span> <%-- board에 대해 select 하면 Board.java보면 user정보랑 reply정보를 eager전략으로 같이 들고와서 사용 가능 --%>
		</div>
		<br />
		<div>
			<h3>${board.title}</h3>
		</div>
		<hr /> <%-- 화면에 선하나 긋는거 --%>
		<div>
  			<div>${board.content}</div>
		</div>
		<hr />
</div>


<script src="/js/board.js"></script>
<%-- ajax요청용 js코드 --%>
<%@ include file="../layout/footer.jsp"%>
<%-- footer는 모든 페이지에 들어가야 하니 따로 만들어주고 include만 해준다 --%>


