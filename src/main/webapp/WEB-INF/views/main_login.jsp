<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<c:if test="${param.result eq false}">
	<script>
		$(document).ready(function(){
			swal({text : "로그인 실패!", icon : "error"});
		});
	</script>
</c:if>
<style>
	.wrap-body{
		position : absolute;
		left : 0;
		right: 0;
		bottom : 0;
		top : 0;
		display:  flex;
		align-items: center;
		justify-content: center;
		
	}
	
	.wrap-form{
		max-width : 150px;
	}
	
	.input-head{
		width : 100%;
		margin-bottom: 10px;
	}
	
	.input-id, .input-pwd{
		border : 0.5px solid #DDD;
		height : 1rem;
		margin-bottom: 7px;
		padding : 3px  2px;
		width : 100%;
	}	
	
	.btn-submit{
		width : 100%;
		background: #222;
		color : #FFF;
		text-align: center;
		padding : 0.3rem 0.2rem;
		cursor: pointer;
		font-size: 0.6rem;
	}
</style>
</head>

<body>
	<div class="wrapper">
		<c:import url="included/included_nav.jsp" charEncoding="UTF-8" />
	
		<div class="wrap-body">
			<div class="wrap-form">
				<form id="form-admin-login" action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
					<img class="input-head" src="${pageContext.request.contextPath}/resources/image/icon-logo.svg"  class="logo" onclick="window.location.href = '${pageContext.request.contextPath}/'" ></img>
					
					<div>
					<input type="text" name="username" placeholder="ID" onkeydown="javascript:if(event.keyCode==13){$('.input-pwd').focus();}" class="input-id"/>
					</div>
					
					<div>
					<input type="password" name="password" placeholder="PASSWORD" onkeydown="javascript:if(event.keyCode==13){$('#form-admin-login').submit()}" class="input-pwd"/>
					</div>
					
					<div class="btn-submit" onclick="$('#form-admin-login').submit()"> 로그인 </div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>