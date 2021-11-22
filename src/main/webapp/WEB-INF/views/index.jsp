<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="layout/header.jsp"%>
<%-- header는 모든 페이지에 들어가야 하니 따로 만들어주고 include만 해준다 --%>

<div class="container">
	
<%-- ${} 이거는 el 표현식 이라는것으로 자바쪽(controller파일)에서 넘겨준 변수를 사용할수있는표현법  --%>
<%-- BoardController에서 model.addAttribute로 넘겨줬었던 boards를 board라는 이름으로 사용하겠다 --%>
<%-- el표현식에서 ${board.title} 이라고 하면 getter가 호출됨--%>
<c:forEach var="board" items="${boards.content}"> <%-- boards가 컨트롤러에서 .글목록()함수로 Page타입으로 넘겨졌기에 안의 내용물을 사용하기 위해 .content (넘어간 값들중에 content란 이름의 변수가 정보를 다 가지고 있어서 content로 접근) --%>
	<div class="card m-2">
		<div class="card-body">
			<h4 class="card-title">${board.title}</h4>
			<a href="/board/${board.id}" class="btn btn-primary">상세보기</a>
		</div>
	</div>
</c:forEach>
	
<ul class="pagination justify-content-center"> <%-- justify-content-center는 부트스트랩의 flex타입(그 학교서 flex froggy한 그거) 중앙 정렬 start end 등도 사용 가능--%>
   	<%-- jstl 문법 header.jsp에서 썼었음 --%>
	<%-- if else 문법 --%>
   	<c:choose>
   		<c:when test="${boards.first}">  <%-- 만약 가져온 페이지 정보에서 첫번째 페이지라는 first가 true면 이전버튼 비활성화 --%>
   		  	<li class="page-item disabled"><a class="page-link" href="?page=${boards.number-1 }">Previous</a></li> <%-- 클래스의 disabled은 버튼 비활성화 --%>
   		</c:when>
   		<c:otherwise>
   			<li class="page-item"><a class="page-link" href="?page=${boards.number-1 }">Previous</a></li>
   		</c:otherwise>
   	</c:choose>
	
	<c:choose>
   		<c:when test="${boards.last}">  <%-- 만약 가져온 페이지 정보에서 마지막 페이지라는 last가 true면 다음버튼 비활성화 --%>
   		  	<li class="page-item disabled"><a class="page-link" href="?page=${boards.number+1 }">Next</a></li> <%-- 페이징을 위한 Page타입의 내용중 현재 페이지인 number 가 있는데 그걸 사용 --%>
   		</c:when>
   		<c:otherwise>
   			<li class="page-item"><a class="page-link" href="?page=${boards.number+1 }">Next</a></li>
   		</c:otherwise>
   	</c:choose>
</ul>
	

</div>

<%@ include file="layout/footer.jsp"%>
<%-- footer는 모든 페이지에 들어가야 하니 따로 만들어주고 include만 해준다 --%>


