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
	.wrap-form{
		position: absolute;
		left : 0;
		right : 0;
		top 	: 0;
		bottom : 0;
		
		display:  flex;
		align-items: center;
		justify-content: center;
		
	}
	.input-head{
		font-weight: bold;
		font-size: 2rem;
		text-align: center;
		margin-bottom: 1rem;
	}
	
	.input-id, .input-pwd{
		border: 1px solid #AAA;
		border-radius: 2px;
		width : 100%;
		height : 1rem;
		margin-bottom: 0.5rem;
		padding-left : 0.2rem;
	}	
	
	.btn-submit{
		border-radius: 2px;
		width : 100%;
		background: #222;
		color : #FFF;
		text-align: center;
		padding : 0.3rem 0.1rem;
		cursor: pointer;
		font-size: 0.6rem;
	}
</style>
</head>

<body>
	<div class="wrapper">
		<c:import url="included/included_nav.jsp" charEncoding="UTF-8" />
	
		<div class="wrap-form">
			<form id="form-admin-login" action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
				<div class="input-head">
					CHANGOO'S
				</div>
				
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
</body>
</html>