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
	
	.photo-list{
		border-collapse: collapse;
		border-spacing: 0px;
		width: 100%;
	}
	
	.photo-list tr:FIRST-CHILD{
		background : #EEE;
	}
	
	.photo-list tr td{
		min-width : 50px;
		padding : 2px;
		border : 1px solid #DDD;
		word-break : break-all;
		text-align : center;
	}
</style>

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
	
	<div class="menu-manage">
		<a href="${pageContext.request.contextPath}/admin/photo/upload" class="btn">Upload</a>
	</div>
	
	<div class="wrap-item-list">
		<table class="photo-list">
			<tr>
			<td> Delete</td>
			<td> Modify</td>
			<td> SEQ </td>
			<td> SORT </td>
			<td> image </td>
			<td> Name </td>
			<td> Desc </td>
			<td> date </td>
			<td> time </td>
			<td> location </td>
			<td> tag </td>
			<td> device </td>
			<td> like </td>
			</tr>
			
			<c:forEach var="photo" items="${photos}">
				<tr>
				<td> <a href="javascript:void(0)" onclick="photoDelete('${photo.seq}')">삭제</a> </td>
				<td> <a href="javascript:void(0)" onclick="photoModify('${photo.seq}')">수정</a> </td>
				<td> ${photo.seq} </td>
				<td> ${photo.sort} </td>
				<td> <img height="100" src="${pageContext.request.contextPath}${photo.image}"/> </td>
				<td> ${photo.name} </td>
				<td> ${photo.desc} </td>
				<td> ${photo.date} </td>
				<td> ${photo.time} </td>
				<td> ${photo.location} </td>
				<td> ${photo.tag} </td>
				<td> ${photo.device} </td>
				<td> ${photo.like} </td>
			</c:forEach>
		</table>
	</div>
	
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
</div>
</body>
</html>