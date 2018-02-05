<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
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
		margin-top: 10px;
	}
	
	.project-list{
		border-collapse: collapse;
		border-spacing: 0px;
		width: 100%;
	}
	
	.project-list tr:FIRST-CHILD{
		background : #EEE;
	}
	
	.project-list tr td{
		min-width : 50px;
		padding : 2px;
		border : 1px solid #DDD;
		word-break : break-all;
		text-align : center;
	}
</style>

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
	
	<div class="menu-manage">
		<a href="${pageContext.request.contextPath}/admin/project/upload" class="btn">Upload</a>
	</div>
	
	<div class="wrap-project-list">
		<table class="project-list">
			<tr>
			<td> SEQ </td>
			<td> SORT </td>
			<td> Name </td>
			<td> Sect </td>
			<td> Desc </td>
			<td> Snapshot </td>
			<td> Video </td>
			<td> date </td>
			<td> sourcecode </td>
			<td> developer </td>
			<td> Hits </td>
			<td> view </td>
			<td> Modify</td>
			<td> Delete</td>
			</tr>
				
			<c:forEach var="project" items="${projects}">
				<tr>
				<td> ${project.seq} </td>
				<td> ${project.sort} </td>
				<td> ${project.title} </td>
				<td> ${project.sect} </td>
				<td> ${project.desc} </td>
				<td> 
					<img height="100" src="${pageContext.request.contextPath}${project.snapsht}"/> <br/>
					${project.snapsht}
				</td>
				<td> ${project.video} </td>
				<td> ${project.date} </td>
				<td> ${project.sourcecode} </td>
				<td> ${project.developer} </td>
				<td> ${project.hits} </td>
				<td> <a href="javascript:void(0)" onclick="projectView('${project.seq}')">보기</a> </td>
				<td> <a href="javascript:void(0)" onclick="projectModify('${project.seq}')">수정</a> </td>
				<td> <a href="javascript:void(0)" onclick="projectDelete('${project.seq}')">삭제</a> </td>
				</tr>
			</c:forEach>
		
		</table>
	</div>
	
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
</div>
</body>
</html>