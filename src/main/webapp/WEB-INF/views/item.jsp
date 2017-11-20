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
	
	.menu{
		display: flex;
		justify-content : flex-end;
	}
	
	.menu li {
		list-style-type: none;
	}
	
	.item-list{
		border-collapse: collapse;
		border-spacing: 0px;
		width: 100%;
	}
	
	.item-list tr:FIRST-CHILD{
		background : #EEE;
		text-align : center;
	}
	
	.item-list tr td{
		min-width : 50px;
		padding-left : 10px;
		border : 1px solid #DDD;
		word-break : break-all;
	}
</style>

<script type="text/javascript">
	function itemDelete(seq){
		var contextPath = "${pageContext.request.contextPath}";
		window.location.href = contextPath + "/item/delete?seq=" + seq;		
	}
</script>
</head>
<body>
<div class="wrapper">
	<div class="header">
		<h1>item-list</h1>
	</div>
	
	<div class="nav">
		<ul class="menu">
			<li><a href="${pageContext.request.contextPath}/item/upload">Upload</a></li>
		</ul>
	</div>
	
	<div class="wrap-item-list">
		<table class="item-list">
			<tr>
			<td> SEQ </td>
			<td> Name </td>
			<td> Desc </td>
			<td> Snapshot-Path </td>
			<td> Write-Date </td>
			<td> dirt </td>
			<td> dirtURL </td>
			<td> Hits </td>
			<td> Delete</td>
			</tr>
				
			<c:forEach var="item" items="${items}">
				<tr>
				<td> ${item.seq} </td>
				<td> ${item.name} </td>
				<td> ${item.desc} </td>
				<td> ${item.snapsht} </td>
				<td> ${item.wrDate} </td>
				<td> ${item.dirtURL} </td>
				<td> ${item.dirt} </td>
				<td> ${item.hits} </td>
				<td> <a href="javascript:void(0)" onclick="itemDelete('${item.seq}')">삭제</a> </td>
				</tr>
			</c:forEach>
		
		</table>
	</div>
</div>
</body>
</html>