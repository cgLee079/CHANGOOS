<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
	.wrapper{
		width : 90%;
		margin: 0px auto;
	}
	
	.header{
		text-align: center;
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
		var contextPath = "${pageContext.request.contextPath}";
		window.location.href = contextPath + "/admin/photo/delete.do?seq=" + seq;		
	}
	
	function photoModify(seq){
		var contextPath = "${pageContext.request.contextPath}";
		window.location.href = contextPath + "/admin/photo/upload?seq=" + seq;		
	}
</script>
</head>
<body>
<div class="wrapper">
	<div class="header">
		<h1>Photo-list</h1>
	</div>
	
	<div class="nav">
		<a href="${pageContext.request.contextPath}/admin/photo/upload">Upload</a>
	</div>
	
	<div class="wrap-item-list">
		<table class="photo-list">
			<tr>
			<td> SEQ </td>
			<td> image </td>
			<td> Name </td>
			<td> Desc </td>
			<td> date </td>
			<td> location </td>
			<td> people </td>
			<td> tag </td>
			<td> like </td>
			<td> Modify</td>
			<td> Delete</td>
			</tr>
			
			<c:forEach var="photo" items="${photos}">
				<tr>
				<td> ${photo.seq} </td>
				<td> <img height="100" src="${pageContext.request.contextPath}${photo.image}"/> </td>
				<td> ${photo.name} </td>
				<td> ${photo.desc} </td>
				<td> ${photo.date} </td>
				<td> ${photo.location} </td>
				<td> ${photo.people} </td>
				<td> ${photo.tag} </td>
				<td> ${photo.like} </td>
				<td> <a href="javascript:void(0)" onclick="photoModify('${photo.seq}')">수정</a> </td>
				<td> <a href="javascript:void(0)" onclick="photoDelete('${photo.seq}')">삭제</a> </td>
			</c:forEach>
		</table>
	</div>
</div>
</body>
</html>