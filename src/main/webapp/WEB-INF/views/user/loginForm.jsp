<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<%-- header는 모든 페이지에 들어가야 하니 따로 만들어주고 include만 해준다
상대경로를 통해 찾아줄거라 ../으로 한 폴더 위로 올라간다--%>

<div class="container">

	<form action="/auth/loginProc" method="post"> <!-- 스프링 시큐리티가 로그인 요청을 가로챌거기에 /auth/loginProc이란 컨트롤러는 안만들것 대신  SecurityConfig.java에 설정-->
		<div class="form-group">
			<label for="username">Username</label> 
			<input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
		</div>
		
		<div class="form-group">
			<label for="password">Password</label> 
			<input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
		</div>
		
		<button id="btn-login" class="btn btn-primary">로그인</button>
	</form>

</div>


<%-- //스프링 시큐리티를 사용하므로 이건 사용 안함 
<script src="/js/user.js"></script>
		//ajax요청용 js코드 --%>




<%@ include file="../layout/footer.jsp"%>
<%-- footer는 모든 페이지에 들어가야 하니 따로 만들어주고 include만 해준다
상대경로를 통해 찾아줄거라 ../으로 한 폴더 위로 올라간다 --%>


