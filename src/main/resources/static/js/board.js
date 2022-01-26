/* saveForm.jsp, detail.jsp에서 import한 js 코드 */
let index =
{
	init: function()
	{
		$("#btn-save").on("click", ()=>
		{
			this.save();
		});

		$("#btn-delete").on("click", ()=>
		{
			this.deleteById();
		});
		
		$("#btn-update").on("click", ()=>
		{
			this.update();
		});
		
		$("#btn-reply-save").on("click", ()=>
		{
			this.replySave();
		});
	},

	save: function()
	{
		//이거 user.js랑 같으니 주석은 그거 봐라
		let data = 
		{
			title: $("#title").val(),  // .val()은 값이라는 의미로 이 id로 값을 찾아서 data에 넣는다 jsp에서 입력된값들을 가져온것
			content: $("#content").val(),
		};
	
		$.ajax({
			type: "POST", 
			url: "/api/board",  //BoardApiController.java 에 구현
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8",
			dataType: "json" 
		}).done(function(resp){ //성공시 컨트롤러에서 리턴하는 값이 resp 안에 들어감
			alert("글쓰기가 완료되었습니다");
			location.href="/"; //글쓰기 완료 후 이 경로로 이동
		}).fail(function(error){ //실패시 컨트롤러에서 리턴하는 값이 error 안에 들어감
			JSON.stringify(error);
		});
	},
	
	deleteById: function()
	{
		let id = $("#id").text(); //detail.jsp 의 <span id="id"><i>${board.id} </i></span> 에서 span 안에 id가 적혀있으니 text로 가져와야함
		
		$.ajax({
			type: "DELETE", 
			url: "/api/board/"+id,  //BoardApiController.java 에 구현
			dataType: "json" 
		}).done(function(resp){ //성공시 컨트롤러에서 리턴하는 값이 resp 안에 들어감
			alert("삭제가 완료되었습니다");
			location.href="/"; //글삭제 완료 후 이 경로로 이동
		}).fail(function(error){ //실패시 컨트롤러에서 리턴하는 값이 error 안에 들어감
			JSON.stringify(error);
		});
	},
	
	update: function()
	{
		//이거 user.js랑 같으니 주석은 그거 봐라
		let id = $("#id").val();
		
		let data = 
		{
			title: $("#title").val(),  // .val()은 값이라는 의미로 이 id로 값을 찾아서 data에 넣는다 jsp에서 입력된값들을 가져온것
			content: $("#content").val()
		};
	
		$.ajax({
			type: "PUT", 
			url: "/api/board/"+id,  //BoardApiController.java 에 구현
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8",
			dataType: "json" 
		}).done(function(resp){ //성공시 컨트롤러에서 리턴하는 값이 resp 안에 들어감
			alert("글수정이 완료되었습니다");
			location.href="/"; //글쓰기 완료 후 이 경로로 이동
		}).fail(function(error){ //실패시 컨트롤러에서 리턴하는 값이 error 안에 들어감
			JSON.stringify(error);
		});
	},
	
	replySave: function()
	{
		//이거 user.js랑 같으니 주석은 그거 봐라
		let data = 
		{
			userId: $("#userId").val(),
			boardId: $("#boardId").val(),
			content: $("#reply-content").val() // .val()은 값이라는 의미로 이 id로 값을 찾아서 data에 넣는다 jsp에서 입력된값들을 가져온것
		};

		$.ajax({
			type: "POST", 
			url: `/api/board/${data.boardId}/reply`,  //BoardApiController.java 에 구현, 여기서 문자 안에 ${data.boardId}값을 표기하기 위해 키보드 ~에 있는 따옴표 사용 일반따옴표쓰면 문자로인식하네
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8",
			dataType: "json" 
		}).done(function(resp){ //성공시 컨트롤러에서 리턴하는 값이 resp 안에 들어감
			alert("댓글작성이 완료되었습니다");
			location.href=`/board/${data.boardId}`; //완료 후 이 경로로 이동
		}).fail(function(error){ //실패시 컨트롤러에서 리턴하는 값이 error 안에 들어감
			JSON.stringify(error);
		});
	},
	
	replyDelete: function(boardId, replyId)
	{
		$.ajax({
			type: "DELETE", 
			url: `/api/board/${boardId}/reply/${replyId}`,  //BoardApiController.java 에 구현, 여기서 문자 안에 ${data.boardId}값을 표기하기 위해 키보드 ~에 있는 따옴표 사용 일반따옴표쓰면 문자로인식하네
			dataType: "json" 
		}).done(function(resp){ //성공시 컨트롤러에서 리턴하는 값이 resp 안에 들어감
			alert("댓글삭제 성공");
			location.href=`/board/${boardId}`; //완료 후 이 경로로 이동
		}).fail(function(error){ //실패시 컨트롤러에서 리턴하는 값이 error 안에 들어감
			JSON.stringify(error);
		});
	},
	
/*	replySave: function()
	{
		//이거 user.js랑 같으니 주석은 그거 봐라
		let data = 
		{
			content: $("#reply-content").val(), // .val()은 값이라는 의미로 이 id로 값을 찾아서 data에 넣는다 jsp에서 입력된값들을 가져온것
		};
		let boardId=$("#boardId").val()
		
		$.ajax({
			type: "POST", 
			url: `/api/board/${boardId}/reply`,  //BoardApiController.java 에 구현, 여기서 문자 안에 ${data.boardId}값을 표기하기 위해 키보드 ~에 있는 따옴표 사용 일반따옴표쓰면 문자로인식하네
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8",
			dataType: "json" 
		}).done(function(resp){ //성공시 컨트롤러에서 리턴하는 값이 resp 안에 들어감
			alert("댓글작성이 완료되었습니다");
			location.href=`/board/${boardId}`; //완료 후 이 경로로 이동
		}).fail(function(error){ //실패시 컨트롤러에서 리턴하는 값이 error 안에 들어감
			JSON.stringify(error);
		});
	},*/
}

index.init();