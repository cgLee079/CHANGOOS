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
	
	.header{
		text-align: center;
	}
	
	.item-list{
		border-collapse: collapse;
		border-spacing: 0px;
		width: 100%;
	}
	
	.item-list tr:FIRST-CHILD{
		background : #EEE;
	}
	
	.item-list tr td{
		min-width : 50px;
		padding : 2px;
		border : 1px solid #DDD;
		word-break : break-all;
		text-align : center;
	}
</style>

<script type="text/javascript">
	function itemView(seq){
		var contextPath = "${pageContext.request.contextPath}";
		window.location.href = contextPath + "/item/view?seq=" + seq;		
	}

	function itemDelete(seq){
		var contextPath = "${pageContext.request.contextPath}";
		window.location.href = contextPath + "/admin/item/delete.do?seq=" + seq;		
	}
	
	function itemModify(seq){
		var contextPath = "${pageContext.request.contextPath}";
		window.location.href = contextPath + "/admin/item/upload?seq=" + seq;		
	}
</script>
</head>
<body>
<div class="wrapper">
	<div class="header">
		<h1>item-list</h1>
	</div>
	
	<div class="nav">
		<a href="${pageContext.request.contextPath}/admin/item/upload">Upload</a>
	</div>
	
	<div class="wrap-item-list">
		<table class="item-list">
			<tr>
			<td> SEQ </td>
			<td> Name </td>
			<td> Sect </td>
			<td> Desc </td>
			<td> Snapshot </td>
			<td> date </td>
			<td> sourcecode </td>
			<td> developer </td>
			<td> Hits </td>
			<td> view </td>
			<td> Modify</td>
			<td> Delete</td>
			</tr>
				
			<c:forEach var="item" items="${items}">
				<tr>
				<td> ${item.seq} </td>
				<td> ${item.name} </td>
				<td> ${item.sect} </td>
				<td> ${item.desc} </td>
				<td> 
					<img height="100" src="${pageContext.request.contextPath}${item.snapsht}"/> <br/>
					${item.snapsht}
				</td>
				<td> ${item.date} </td>
				<td> ${item.sourcecode} </td>
				<td> ${item.developer} </td>
				<td> ${item.hits} </td>
				<td> <a href="javascript:void(0)" onclick="itemView('${item.seq}')">보기</a> </td>
				<td> <a href="javascript:void(0)" onclick="itemModify('${item.seq}')">수정</a> </td>
				<td> <a href="javascript:void(0)" onclick="itemDelete('${item.seq}')">삭제</a> </td>
				</tr>
			</c:forEach>
		
		</table>
	</div>
</div>
</body>
</html>