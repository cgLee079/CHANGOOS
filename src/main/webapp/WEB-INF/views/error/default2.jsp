<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<style>
	.wrap-message{
		position: absolute;
		left : 0;
		right : 0;
		top 	: 0;
		bottom : 0;
		
		display:  flex;
		align-items: center;
		justify-content: center;
	}
	
	.message{
		background : #FFF;
		padding : 2rem;
		border-radius: 10px;
	    box-shadow: 0px 10px 10px #ECF0F1;
	}
	
	.message .message-icon{
		height : 10rem;
		background-position: center;
	    background-repeat: no-repeat;
	    background-size: contain;
	    
	    opacity : 0.7;
	    margin-bottom: 2rem;
	}
	
	.message .message-title{
		font-size: 1rem;
		font-weight: bold;
		margin-bottom: 2rem;
	}
	
	.message .message-content{
		text-align: center;
		font-size: 0.7rem;
	}
	
	.message .message-content .btn-before{
		color: #00F;
		text-decoration: underline;
		cursor: pointer;
	}
</style>
</head>

<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
		<div class="wrap-message">
			<div class="message">
				<div class="message-icon" style="background-image: url('${pageContext.request.contextPath}/resources/image/icon-error-warning.svg')">
				</div>
				<div class="message-title">
					원인을 알 수 없는 에러입니다.
				</div>
				<div class="message-content">
					${exception} <br/>
					<br/>
					<span class="btn-before" onclick="history.back()">이전 페이지로</span>
				</div>
			</div>
		</div>
	</div>
</body>
</html>