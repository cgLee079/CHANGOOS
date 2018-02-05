<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<script type="text/javascript">
	function projectView(seq){
		window.location.href = getContextPath() + "/project/view?seq=" + seq;		
	}

	function projectDelete(seq){
		var question = "정말로 삭제 하시겠습니까?";
		if(confirm(question)){
			window.location.href = getContextPath() + "/admin/project/delete.do?seq=" + seq;	
		}
	}
	
	function projectModify(seq){
		window.location.href = getContextPath() + "/admin/project/upload?seq=" + seq;		
	}
</script>
</head>
<body>
<div class="wrapper">
	<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
	
	<style>
	.wrapper{
		width : 100%;
		margin: 0px auto;
	}
	
	.menu-manage{
		margin : 1rem;
		margin-top: 30px;
		text-align: right;
	}
	
	.wrap-project-list{
		width : 80%;
		margin : 0px auto;
		margin-top : 1rem;
		padding : 1rem;
		background: #FFF;
		border: 1px solid #DDD;
	}
	
	.project-list{
		width : 100%;
		font-size: 0.7rem;
		
	}
	
	.list-item > div{
		padding: 0.5rem;
		border: 1px solid #DDD;
	}
	
	.list-item{
		display: flex;
		flex-flow : row nowrap;
		justify-content : center;
	}
	
	.list-item-header{font-weight: bold; text-align: center;}
	
	.list-item .item-seq 	{width : 2rem; text-align: center;}
	.list-item .item-sort	{width : 2rem; text-align: center;}
	.list-item .item-title 	{flex : 1; min-width : 5rem; text-align: center; font-weight: bold;}
	.list-item .item-snapsht{width : 10rem; height: 1rem}
	.list-item .item-snapsht img{max-width:10rem; height: 1rem}
	.list-item .item-detail-view {width : 2rem; text-align: center;}
	.list-item .item-modify {width : 2rem; text-align: center;}
	.list-item .item-delete {width : 2rem; text-align: center;}
	
	</style>
	<div class="wrap-project-list">
		<div class="menu-manage">
			<a href="${pageContext.request.contextPath}/admin/project/upload" class="btn">Upload</a>
		</div>
		
		<div class="project-list">
			<div class="list-item list-item-header">
				<div class="item-seq">SEQ</div>
				<div class="item-sort">SORT</div>
				<div class="item-title">TITLE</div>
				<div class="item-snapsht">SNAPSHT</div>
				<div class="item-detail-view">보기</div>
				<div class="item-modify">수정</div>
				<div class="item-delete">삭제</div>
			</div>
		
			<c:forEach var="project" items="${projects}">
				<div class="list-item">
					<div class="item-seq">${project.seq}</div>
					<div class="item-sort">${project.sort}</div>
					<div class="item-title">${project.title}</div>
					<div class="item-snapsht"><img src="${pageContext.request.contextPath}${project.snapsht}"/></div>
					<div class="btn item-detail-view"><a href="javascript:void(0)" onclick="projectView('${project.seq}')">보기</a></div>
					<div class="btn item-modify"><a href="javascript:void(0)" onclick="projectModify('${project.seq}')">수정</a></div>
					<div class="btn item-delete"><a href="javascript:void(0)" onclick="projectDelete('${project.seq}')">삭제</a></div>
				</div>
			</c:forEach>
		</div>
	</div>
	
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
</div>
</body>
</html>