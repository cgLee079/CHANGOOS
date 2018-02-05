<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<script type="text/javascript">
	function photoDelete(seq){
		var question = "정말로 삭제 하시겠습니까?";
		if(confirm(question)){
			window.location.href = getContextPath() + "/admin/photo/delete.do?seq=" + seq;
		}
	}
	
	function photoModify(seq){
		window.location.href = getContextPath() + "/admin/photo/upload?seq=" + seq;		
	}
</script>
</head>
<body>
<div class="wrapper">
	<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
	
	<style>
.menu-manage{
	margin : 1rem;
	margin-top: 30px;
	text-align: right;
}

.wrap-photo-list{
	width : 80%;
	margin : 0px auto;
	margin-top : 1rem;
	padding : 1rem;
	background: #FFF;
	border: 1px solid #DDD;
}

.project-list{
	width : 100%;
	font-size: 0.5rem;
}

.list-photo > div{
	padding: 0.5rem;
	border: 1px solid #DDD;
}

.list-photo{
	display: flex;
	flex-flow : row nowrap;
	justify-content : center;
}

.list-photo-header{font-weight: bold; text-align: center;}

.list-photo .photo-seq 	{width : 2rem; text-align: center;}
.list-photo .photo-sort	{width : 2rem; text-align: center;}
.list-photo .photo-title {flex : 1; min-width : 5rem; text-align: center; font-weight: bold;}
.list-photo .photo-img{width : 3rem; max-width: 5rem;}
.list-photo .photo-img img{max-width:3rem; max-height: 1rem;}
.list-photo .photo-device {width:4rem;}
.list-photo .photo-loc {width:3rem;}
.list-photo .photo-date {width:3rem;}
.list-photo .photo-time {width:2rem;}
.list-photo .photo-modify {width : 2rem; text-align: center;}
.list-photo .photo-delete {width : 2rem; text-align: center;}

</style>
	
	
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