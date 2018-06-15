<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin"></sec:authorize>
<div class="wrap-comment">
	<input type="hidden" id="boardType" value="<c:out value='${param.boardType}'/>"/>
	<input type="hidden" id="boardSeq" value="<c:out value='${param.boardSeq}'/>"/>
	<input type="hidden" id="comtCnt" value="<c:out value='${param.comtCnt}'/>"/>
	<input type="hidden" id="isAdmin" value="<c:out value='${isAdmin}'/>"/>
	
	<div class="comment-header">COMMENT &nbsp(<a class="comment-cnt"></a>)</div>
	<div class="comments"></div>
	<div class="comt-pager"></div>
	
	<div class="comment-write">
		<textarea class="comment-write-contents" id="contents" name="contents"></textarea>
		<div class="comment-write-pinfo">
			<div class="write-name"><input type="text" id="name" name="name" placeholder="name"></div>
			<div class="write-password"><input type="password" id="password" name="password" placeholder="password"></div>
			<div style="flex : 1"></div>
			<div onclick="doCommentSubmit()" class="comment-write-submit col-center">등록</div>
		</div>
		
	</div>
	
</div>
