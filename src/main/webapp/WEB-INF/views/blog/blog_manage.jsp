<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/blog/blog-manage.css" />
<script src="${pageContext.request.contextPath}/resources/js/blog/blog-manage.js"></script>
<script>
$(document).ready(function(){
	fn_onInitDataGrid();
});

/* Datagrid Initialize */
function fn_onInitDataGrid(){
	$('#dg').datagrid({
		url: getContextPath() + '/mgnt/blog/list.do',
		method: 'post',
		singleSelect: true,
		remoteSort: true,
		fitColumns: false,
		checkOnSelect: false,
		selectOnCheck: false,
		emptyMsg: '데이터가 없습니다.',
		columns:[[
			{field:'seq', title:'번호', width:'50px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'sort', title:'정렬', width:'70px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'view', title:'보기', width:'70px', halign:'center', styler : alignCenter, formatter : function(value, row){
				return "<a onclick='blogView(" + row.seq + ")' class='dg-btn'> 보기 </a>" 
			}},
			{field:'modify', title:'수정', width:'70px', halign:'center', styler : alignCenter, formatter : function(value, row){
				return "<a onclick='blogModify(" + row.seq + ")' class='dg-btn'> 수정 </a>" 
			}},
			{field:'delete', title:'삭제', width:'70px', halign:'center', styler : alignCenter, formatter : function(value, row, index){
				return "<a onclick='blogDelete(" + row.seq + "," + index + ")' class='dg-btn'> 삭제 </a>" 
			}},
			{field:'snapsht', title:'스냅샷', width:'150px', halign:'center', sortable : "true", styler : alignLeft, formatter: function(value){
				return "<img src='" + getContextPath() + value + "' height='50px' style='padding : 2px'/>"
			}},
			{field:'tag', title:'태그', width:'150px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'title', title:'이름', width:'300px', halign:'center', sortable : "true", styler : alignLeft},
			{field:'comtCnt', title:'댓글수', width:'70px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'hits', title:'조회수', width:'70px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'date', title:'작성일', width:'100px', halign:'center', sortable : "true", styler : alignCenter},
		]]
	});
}

/* when '보기' click */
function blogView(seq){
	window.location.href = getContextPath() + "/blog/view?seq=" + seq;		
}

/* when '삭제' click */
function blogDelete(seq, index){
	swal({
		  title: "정말로 삭제 하시겠습니까?",
		  text: "삭제된 게시글은 복구 할 수 없습니다.",
		  icon: "warning",
		  buttons: ["취소", "삭제"],
		  dangerMode: true,
		})
		.then(function(willDelete){
		  if (willDelete) {
			  doDelete(seq, index);
		  } 
		});
	
	function doDelete(seq, index){
		$.ajax({
			type	: "POST",
			url		: getContextPath() + "/mgnt/blog/delete.do?",
			data	: { 'seq' : seq },
			dataType: 'JSON',
			async	: false,
			success : function(data) {
				if(data.result){
					$('#dg').datagrid('deleteRow', index);
					swal({ title: "삭제 완료하였습니다.", icon: "info"});
				}
			},
			error : function(e) {
				console.log(e);
			}
		});
	}
}

/* when '수정' click */
function blogModify(seq){
	window.location.href = getContextPath() + "/mgnt/blog/upload?seq=" + seq;		
}
</script>
</head>
<body>
<div class="wrapper">
	<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
	
	<div class="wrap-blog-list">
		<div class="menu-manage">
			<a href="${pageContext.request.contextPath}/blog" class="btn">List</a>
			<a href="${pageContext.request.contextPath}/mgnt/blog/upload" class="btn">Upload</a>
		</div>
		
		<div class="blog-list">
			<table id="dg" style="width: 100%; height:100%;"></table>
		</div>
	</div>
	
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
</div>
</body>
</html>