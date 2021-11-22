<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%--이거 saveForm 복사한거 --%>

<%@ include file="../layout/header.jsp"%>  <!-- 이파일 위치가 board 안이니 한칸 위로 가서 header를 찾아야함 -->
<%-- header는 모든 페이지에 들어가야 하니 따로 만들어주고 include만 해준다 --%>

<div class="container">
	<form>
	<input type="hidden" id="id" value="${board.id }"/>
		<div class="form-group"> 
			<input value="${board.title}" type="text" class="form-control" placeholder="Enter title" id="title"> <%-- 여기는 컨트롤러에서 값을 가져오니까 value="${board.title}"로 가져옴 --%>
		</div>
		
		<div class="form-group">
  			<textarea class="form-control summernote" rows="5" id="content">${board.content }</textarea> <%-- 여기는 컨트롤러에서 값을 가져오니까 ${board.content}로 가져옴 --%>
		</div>
	</form>
<button id="btn-update" class="btn btn-primary">글수정완료</button>
</div>

<%-- summernote 스크립트 (content 쓰는 칸을 summernote로 만들거) --%>
<script>
      $('.summernote').summernote({
        tabsize: 2,
        height: 300
      });
</script>
<script src="/js/board.js"></script>
<%-- ajax요청용 js코드 --%>
<%@ include file="../layout/footer.jsp"%>
<%-- footer는 모든 페이지에 들어가야 하니 따로 만들어주고 include만 해준다 --%>


