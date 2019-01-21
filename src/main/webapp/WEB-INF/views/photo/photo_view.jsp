<%@ page pageEncoding="UTF-8" %>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/photo/photo-view.css" />
<script src="${pageContext.request.contextPath}/resources/js/photo/photo-view.js"></script> 
<sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin" />
</head>
<body>
<div class="wrapper">
	<c:import url="../included/included_nav.jsp" charEncoding="UTF-8">
	</c:import>

	<input type="hidden" id="seqs" value="<c:out value='${seqs}'/>"/>
	
	<div class="wrap-photo-list">
		<div class="photo-list">
			<div class="photo-list-item">
				<input type="hidden" class="photo-seq"/>
				<div class="photo-img"></div>
				<div class="photo-sub">
					<div class="photo-menu">
						<img class="btn-photo-like" onclick="photoDoLike(this)" src="${pageContext.request.contextPath}/resources/image/btn-photo-like.svg" />
						<img class="btn-photo-comment" onclick="drawCommentForm(this)" src="${pageContext.request.contextPath}/resources/image/btn-photo-comment.svg" />
					</div>
					
					<div class="photo-detail">
						<div class="photo-info">
							<div class="photo-name"></div>
							<div class="photo-like"></div>
							<div style="flex : 1"></div>
							<div class="photo-date-loc"></div>
						</div>
						<div class="photo-desc editor-contents"></div>
						<div class="photo-tag"></div>
					</div>
					
					<div class="photo-comments">			
					</div>
					
					<div class="photo-write-comment none">
						<div class="write-userinfo">
							<div><input type="text" class="username" placeholder="NAME" style="font-size: 0.6rem"></div>
							<div><input type="password" class="password" placeholder="PWD" style="font-size: 0.6rem"></div>
						</div>
						<div class="write-comment">
							<div><textarea class="contents" style="font-size: 0.6rem"></textarea></div>
						</div>
						
						<div class="btn-write col-center" onclick="commentDoWrite(this)">등록</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="snapsht-list">
			<div class="snapsht-list-cut"></div>
			<c:forEach items="${photos}" var="photo" varStatus="status" >
				<div class="snapsht-list-item" onclick="showPhoto('${status.index}')"  
					style="background-image: url('${pageContext.request.contextPath}${photoThumbDir}${photo.thumbnail}')">
				</div>
			</c:forEach>
			<div class="snapsht-list-cut"></div>
		</div>
	</div>
	
</div>

</body>
</html>