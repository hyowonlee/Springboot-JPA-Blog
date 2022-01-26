/* joinForm.jsp에서 import한 js 코드 */
let index =
{
	init: function()
	{
		$("#btn-save").on("click", ()=>   //화살표함수를 쓰는 이유는 this를 바인딩하기 위해서임 코드를 줄이기 위해서가 아님 여기서 function(){}로 사용하면 this가 이 index변수가 아닌 윈도우객체를 가리킴
		//만약 꼭 function으로 써야겠다 한다면 위의 index 시작부분에서 let _this = this 처럼 index를 가리키는 this를 만들어서 그거로  function(){} 이 안에서 사용해야됨
		{
			if($("#username").val().trim() == "" || $("#password").val().trim() == ""  || $("#email").val().trim() == "") //id, 비밀번호, email 창이 공백일경우 오류
			{
				alert("id, 비밀번호, email을 입력하세요");
				return false;
			}
			
			this.save(); //회원가입
		});
		
		$("#btn-update").on("click", ()=>   //화살표함수를 쓰는 이유는 this를 바인딩하기 위해서임 코드를 줄이기 위해서가 아님 여기서 function(){}로 사용하면 this가 이 index변수가 아닌 윈도우객체를 가리킴
		//만약 꼭 function으로 써야겠다 한다면 위의 index 시작부분에서 let _this = this 처럼 index를 가리키는 this를 만들어서 그거로  function(){} 이 안에서 사용해야됨
		{
		if($("#username").val().trim() == "" || $("#password").val().trim() == ""  || $("#email").val().trim() == "") //id, 비밀번호, email 창이 공백일경우 오류
			{
				alert("id, 비밀번호, email을 입력하세요");
				return false;
			}
			this.update(); //회원정보 수정
		});

//스프링 시큐리티를 사용하므로 이건 사용 안함
/*		$("#btn-login").on("click", ()=>   //화살표함수를 쓰는 이유는 this를 바인딩하기 위해서임 코드를 줄이기 위해서가 아님 여기서 function(){}로 사용하면 this가 이 index변수가 아닌 윈도우객체를 가리킴
		//만약 꼭 function으로 써야겠다 한다면 위의 index 시작부분에서 let _this = this 처럼 index를 가리키는 this를 만들어서 그거로  function(){} 이 안에서 사용해야됨
		{
			this.login();
		});*/
	},

	save: function()
	{
		//alert('user.js 의 function save');
		let data = 
		{
			username: $("#username").val(),  // .val()은 값이라는 의미로 이 id로 값을 찾아서 data에 넣는다 jsp에서 입력된값들을 가져온것
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		//console.log(data);
		
		//ajax 호출시 default가 비동기 호출 그래서 이 밑에 다른 코드가 있어도 이게 실행되는동안 다른것들이 동시 실행됨 그리고 이게 끝나면 다시 done으로 옴
		 // ajax 통신 시도 , ajax 통신을 이용해 3개의 데이터(username password email)를 json으로 변경해 insert 요청
		 //ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 오브젝트로 변환해주네요
		$.ajax({
			//여기엔 오브젝트가 들어와야 돼서 {}
			//이놈들이 .으로 연결되어있는데 여기서 회원가입 수행을 요청하고 성공하면 done에있는 함수를 실패하면 fail에 있는 함수를 실행
			type: "POST", //메소드 방식을 뭐로할거냐
			url: "/auth/joinProc",  //UserApiController.java 에 구현
			/* data , contentType은 세트임 */
			data: JSON.stringify(data),  // 내가 날릴 데이터 여기선 위의 let data 변수를 날릴거 근데 이건 js 객체이니 자바에 넘기면 이해 못해서 JSON으로 변경시킴
			contentType: "application/json; charset=utf-8",//여기서 http에서 데이터를 보낼땐 http body 데이터 이 http의 body 데이터를 보낼땐 이게 어떤타입인지 알려줘야함(이놈이 Mime타입)
			/* data , contentType은 세트임 http로 데이터를 보내니 body 로 보내는것이고 body로 데이터를 보낼땐 그게 무슨 데이터인지 알릴 Mime타입이 필요 */
			dataType: "json" // 요청을 해서 서버로 응답이 왔을때 기본적으로 모든 것이 문자열타입으로 받게됨 이때 받은 데이터의 모양이 json이라면 dataType에 json이라고 적어주면 응답의 결과를 javascript오브젝트로 변경해줌
		}).done(function(resp){ //성공시 컨트롤러에서 리턴하는 값이 resp 안에 들어감
			if(resp.status == 500 ){
				alert("회원가입에 실패하였습니다");
			}
			else{
				alert("회원가입이 완료되었습니다");	
				//console.log(resp);
				location.href="/"; //회원가입 완료 후 이 경로로 이동
			}
		}).fail(function(error){ //실패시 컨트롤러에서 리턴하는 값이 error 안에 들어감
			JSON.stringify(error);
		});
		
		
	},
	
	update: function()
	{
		//alert('user.js 의 function save');
		let data = 
		{
			id: $("#id").val(),// .val()은 값이라는 의미로 이 id로 값을 찾아서 data에 넣는다 jsp에서 입력된값들을 가져온것
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		$.ajax({
			//여기엔 오브젝트가 들어와야 돼서 {}
			//이놈들이 .으로 연결되어있는데 여기서 회원가입 수행을 요청하고 성공하면 done에있는 함수를 실패하면 fail에 있는 함수를 실행
			type: "PUT", //메소드 방식을 뭐로할거냐
			url: "/user",  //UserApiController.java 에 구현
			/* data , contentType은 세트임 */
			data: JSON.stringify(data),  // 내가 날릴 데이터 여기선 위의 let data 변수를 날릴거 근데 이건 js 객체이니 자바에 넘기면 이해 못해서 JSON으로 변경시킴
			contentType: "application/json; charset=utf-8",//여기서 http에서 데이터를 보낼땐 http body 데이터 이 http의 body 데이터를 보낼땐 이게 어떤타입인지 알려줘야함(이놈이 Mime타입)
			/* data , contentType은 세트임 http로 데이터를 보내니 body 로 보내는것이고 body로 데이터를 보낼땐 그게 무슨 데이터인지 알릴 Mime타입이 필요 */
			dataType: "json" // 요청을 해서 서버로 응답이 왔을때 기본적으로 모든 것이 문자열타입으로 받게됨 이때 받은 데이터의 모양이 json이라면 dataType에 json이라고 적어주면 응답의 결과를 javascript오브젝트로 변경해줌
		}).done(function(resp){ //성공시 컨트롤러에서 리턴하는 값이 resp 안에 들어감
			alert("회원수정이 완료되었습니다");
			//console.log(resp);
			location.href="/"; //회원가입 완료 후 이 경로로 이동
		}).fail(function(error){ //실패시 컨트롤러에서 리턴하는 값이 error 안에 들어감
			JSON.stringify(error);
		});
		
		
	},
	
	//스프링 시큐리티를 사용하므로 이건 사용 안함
/*		login: function()
	{
		//alert('user.js 의 function save');
		let data = 
		{
			username: $("#username").val(),  // .val()은 값이라는 의미로 이 id로 값을 찾아서 data에 넣는다 jsp에서 입력된값들을 가져온것
			password: $("#password").val(),
		};
		
		//console.log(data);
		
		//ajax 호출시 default가 비동기 호출 그래서 이 밑에 다른 코드가 있어도 이게 실행되는동안 다른것들이 동시 실행됨 그리고 이게 끝나면 다시 done으로 옴
		 //ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 오브젝트로 변환해주네요
		$.ajax({
			//여기엔 오브젝트가 들어와야 돼서 {}
			//이놈들이 .으로 연결되어있는데 여기서 수행을 요청하고 성공하면 done에있는 함수를 실패하면 fail에 있는 함수를 실행
			type: "POST", //메소드 방식을 뭐로할거냐
			url: "/api/user/login",  //UserApiController.java 에 구현
			// data , contentType은 세트임 
			data: JSON.stringify(data),  // 내가 날릴 데이터 여기선 위의 let data 변수를 날릴거 근데 이건 js 객체이니 자바에 넘기면 이해 못해서 JSON으로 변경시킴
			contentType: "application/json; charset=utf-8",//여기서 http에서 데이터를 보낼땐 http body 데이터 이 http의 body 데이터를 보낼땐 이게 어떤타입인지 알려줘야함(이놈이 Mime타입)
			// data , contentType은 세트임 http로 데이터를 보내니 body 로 보내는것이고 body로 데이터를 보낼땐 그게 무슨 데이터인지 알릴 Mime타입이 필요 
			dataType: "json" // 요청을 해서 서버로 응답이 왔을때 기본적으로 모든 것이 문자열타입으로 받게됨 이때 받은 데이터의 모양이 json이라면 dataType에 json이라고 적어주면 응답의 결과를 javascript오브젝트로 변경해줌
		}).done(function(resp){ //성공시 컨트롤러에서 리턴하는 값이 resp 안에 들어감
			alert("로그인이 완료되었습니다");
			//console.log(resp);
			location.href="/";
		}).fail(function(error){ //실패시 컨트롤러에서 리턴하는 값이 error 안에 들어감
			JSON.stringify(error);
		});
	}*/
	
}

index.init();