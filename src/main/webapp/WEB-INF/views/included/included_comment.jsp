<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin"></sec:authorize>
<script>
var page;
var perPgLine 	= 10;
var boardType	= "${param.boardType}";
var boardSeq	= "${param.boardSeq}";
var comtCnt		= parseInt("${param.comtCnt}");
var path 		= getContextPath() + "/" + boardType;
var comtFormTemp= $(".comment-write").clone();
var isAdmin		= '${isAdmin}';

$(document).ready(function(){
	commentPageMove(1);
})

</script>
<div class="wrap-comment">
	<div class="comments"></div>
	<div class="comt-pager"></div>
	
	<div class="comment-write">
		<div class="comment-write-pinfo">
			<input type="text" id="name" name="name" placeholder="name">
			<input type="password" id="password" name="password" placeholder="password">
		</div>

		<textarea class="comment-write-contents" id="contents" name="contents"></textarea>
		<div onclick="commentSubmit()" class="comment-write-submit col-center">등록</div>
	</div>
	
</div>
