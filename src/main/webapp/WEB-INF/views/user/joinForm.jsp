<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<%-- header는 모든 페이지에 들어가야 하니 따로 만들어주고 include만 해준다 상대경로를 통해 찾아줄거라 ../으로 한 폴더 위로 올라간다--%>

<div class="container">

	<form>
		<div class="form-group">
			<label for="username">Username</label> 
			<input type="text" class="form-control" placeholder="Enter username" id="username">
		</div>
		
		<div class="form-group">
			<label for="password">Password</label> 
			<input type="password" class="form-control" placeholder="Enter password" id="password">
		</div>
		
		<div class="form-group">
			<label for="email">Email</label> 
			<input type="email" class="form-control" placeholder="Enter email" id="email">
		</div>
		

	</form>

	<button id="btn-save" class="btn btn-primary">회원가입완료</button>

</div>

<script src="/js/user.js"></script>
<%-- ajax요청용 js코드 --%>

<%@ include file="../layout/footer.jsp"%>
<%-- footer는 모든 페이지에 들어가야 하니 따로 만들어주고 include만 해준다 상대경로를 통해 찾아줄거라 ../으로 한 폴더 위로 올라간다 --%>


