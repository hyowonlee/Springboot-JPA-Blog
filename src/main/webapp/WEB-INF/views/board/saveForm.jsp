<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>  <!-- 이파일 위치가 board 안이니 한칸 위로 가서 header를 찾아야함 -->
<%-- header는 모든 페이지에 들어가야 하니 따로 만들어주고 include만 해준다 --%>

<div class="container">
	<form>
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Enter title" id="title">
		</div>
		
		<div class="form-group">
  			<textarea class="form-control summernote" rows="5" id="content"></textarea>
		</div>
	</form>
<button id="btn-save" class="btn btn-primary">글쓰기완료</button>
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


