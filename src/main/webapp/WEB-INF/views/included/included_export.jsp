<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
$(document).ready(function(){
	var data = {
		thumbnail 	: '${param.thumbnail}',
		title 		: '${param.title}',
		hits 		: '${param.hits}',
		comtCnt 	: '${param.comtCnt}'
	}
	
	initExportKakao(data);
})
</script>

<div class="bg-wrap-export"></div>
<div class="wrap-export">
	<div class="export">
		<div class="btn-export-exit" onclick="exitExportView()">
			<span></span> <span></span>
		</div>
	
		<div class="wp-export-img">
			<div class="bg-img"></div>
			<c:if test="${!empty param.thumbnail}">
				<div class="img">
					<img src="${pageContext.request.contextPath}${param.thumbnail}" />
				</div>
			</c:if>
		</div>
	
		<div class="exps">
			<div class="exp exp-url" onclick="urlClipCopy()">
				<img class="exp-icon"
					src="${pageContext.request.contextPath}/resources/image/btn-export-url.svg">
				<span>URL 복사</span>
			</div>
			<div id="expKakaotalk" class="exp exp-kakaotalk"
				onclick="exitExportView();">
				<img class="exp-icon"
					src="${pageContext.request.contextPath}/resources/image/btn-export-kakaotalk.svg">
				<span>카카오톡 공유하기</span>
			</div>
	
		</div>
	</div>

</div>
