<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<%-- header는 모든 페이지에 들어가야 하니 따로 만들어주고 include만 해준다 상대경로를 통해 찾아줄거라 ../으로 한 폴더 위로 올라간다--%>
<%--이건 joinForm 복사한거 --%>
<%-- header.jsp 가보면 현재 세션이 principal이라는 변수에 들어가있음 --%>
<div class="container">

	<form>
		<input type="hidden" id="id" value="${principal.user.id }"/> <%-- 회원정보 수정의 자바스크립트 update 동작에서 username을 들고가지 않을거라 해당 회원을 식별하기 위해 hidden값을 넣어줌 --%>
		<div class="form-group">
			<label for="username">Username</label> 
			<%--회원정보는 굳이 select 안하고 세션에서 들고옴 --%>
			<input type="text" value="${principal.user.username}" class="form-control" placeholder="Enter username" id="username" readonly="readonly">
		</div>
		
		<div class="form-group">
			<label for="password">Password</label> 
			<input type="password" class="form-control" placeholder="Enter password" id="password">
		</div>
		
		<div class="form-group">
			<label for="email">Email</label> 
			<input type="email" value="${principal.user.email }" class="form-control" placeholder="Enter email" id="email">
		</div>
		

	</form>

	<button id="btn-update" class="btn btn-primary">회원수정완료</button>  <%--로그인 버튼을 form태그 안에 넣었기에 이걸 누르면 submit이 된다 (submit어딨나 한참 찾았는데 button으로도 되네) --%>

</div>

<script src="/js/user.js"></script>
<%-- ajax요청용 js코드 --%>

<%@ include file="../layout/footer.jsp"%>
<%-- footer는 모든 페이지에 들어가야 하니 따로 만들어주고 include만 해준다 상대경로를 통해 찾아줄거라 ../으로 한 폴더 위로 올라간다 --%>


