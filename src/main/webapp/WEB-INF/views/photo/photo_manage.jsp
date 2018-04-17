<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/photo/photo-manage.css" />
<script type="text/javascript">
	function photoDelete(seq){
		swal({
			  title: "정말로 삭제 하시겠습니까?",
			  text: "삭제된 사진은 복구 할 수 없습니다.",
			  icon: "warning",
			  buttons: ["취소", "삭제"],
			  dangerMode: true,
			})
			.then(willDelete => {
			  if (willDelete) {
				  window.location.href = getContextPath() + "/admin/photo/delete.do?seq=" + seq;
			  } 
			});
	}
	
	function photoModify(seq){
		window.location.href = getContextPath() + "/admin/photo/upload?seq=" + seq;		
	}
</script>
</head>
<body>
<div class="wrapper">
	<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
	
	<div class="wrap-photo-list">
		<div class="menu-manage">
			<a href="${pageContext.request.contextPath}/photo" class="btn">List</a>
			<a href="${pageContext.request.contextPath}/admin/photo/upload" class="btn">Upload</a>
		</div>
		
		<div class="project-list">
			<div class="list-photo list-photo-header">
				<div class="photo-seq">SEQ</div>
				<div class="photo-title">TITLE</div>
				<div class="photo-img">IMG</div>
				<div class="photo-device">DEVICE</div>
				<div class="photo-loc">LOC</div>
				<div class="photo-date">DATE</div>
				<div class="photo-time">TIME</div>
				<div class="photo-modify">수정</div>
				<div class="photo-delete">삭제</div>
			</div>
		
			<c:forEach var="photo" items="${photos}">
				<div class="list-photo">
					<div class="photo-seq">${photo.seq}</div>
					<div class="photo-title">${photo.name}</div>
					<div class="photo-img"><img src="${pageContext.request.contextPath}${photo.snapsht}"/></div>
					<div class="photo-device">${photo.device}</div>
					<div class="photo-loc">${photo.location }</div>
					<div class="photo-date">${photo.date}</div>
					<div class="photo-time">${photo.time}</div>
					<div class="btn photo-modify"><a href="javascript:void(0)" onclick="photoModify('${photo.seq}')">수정</a></div>
					<div class="btn photo-delete"><a href="javascript:void(0)" onclick="photoDelete('${photo.seq}')">삭제</a></div>
				</div>
			</c:forEach>
		</div>
	</div>
	
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
</div>
</body>
</html>