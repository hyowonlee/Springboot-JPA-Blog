<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%-- jstl 라이브러리 추가 pom.xml에서도 설정 해야됨 --%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> <%-- spring security taglib --%>
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/> <%--이 var=principal은 PrincipalDetail.java의 PrincipalDetail 객체를 의미 그래서 principal.user라 하면 안의 user객체에 접근 가능--%>

</sec:authorize>


<!DOCTYPE html>
<html lang="en">
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>

<%-- <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script> 원랜 w3c에서 가져온 jsscript는 이거였는데 summernote에서 오류나서 바로밑걸로 바꿈 --%>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet"> 
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>


</head>
<body>
	<nav class="navbar navbar-expand-md bg-dark navbar-dark">
		<a class="navbar-brand" href="/">Cos</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="collapsibleNavbar">
			<%-- jstl 문법 --%>
			<%-- if else 문법 --%>
			<%-- 위 sec:authentication에서 만든 principal 변수는 로그인된 세션을 principal 변수에 넣어준것(아마 전통적인 로그인에선 UserApiController.java로그인 함수에서 설정해주고/ 스프링 시큐리티에선 자동으로 설정 되는듯?) --%>
			<%-- principal세션이 비어있으면 when의 화면이 나올것 --%>
			<%-- 아니라면 otherwise 나올것 즉 화면 헤더 부분에 로그인 됐을때랑 안됐을때랑 화면을 다르게 하겠다는뜻--%>
			<c:choose>
				<c:when test="${empty principal }">
					<ul class="navbar-nav">
						<li class="nav-item"><a class="nav-link" href="/auth/loginForm">로그인</a></li>
						<li class="nav-item"><a class="nav-link" href="/auth/joinForm">회원가입</a></li>
					</ul>
				</c:when>
				<c:otherwise>
					<ul class="navbar-nav">
						<li class="nav-item"><a class="nav-link" href="/board/saveForm">글쓰기</a></li>
						<li class="nav-item"><a class="nav-link" href="/user/updateForm">회원정보</a></li>
						<li class="nav-item"><a class="nav-link" href="/logout">로그아웃</a></li>
						<%-- 스프링 시큐리티에서 로그아웃은 그냥 자동으로 localhost:8000/logout 주소가 로그아웃이 될 수 있도록 하는 주소로 로직이 디폴트로 만들어져 있음 --%>
					</ul>
				</c:otherwise>
			</c:choose>


		</div>
	</nav>
	<br />