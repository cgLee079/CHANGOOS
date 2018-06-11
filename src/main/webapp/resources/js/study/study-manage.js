$(document).ready(function(){
	fn_onInitDataGrid();
});

/* Datagrid Initialize */
function fn_onInitDataGrid(){
	$('#dg').datagrid({
		url: getContextPath() + '/mgnt/study/list.do',
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
				return "<a onclick='studyView(" + row.seq + ")' class='dg-btn'> 보기 </a>" 
			}},
			{field:'modify', title:'수정', width:'70px', halign:'center', styler : alignCenter, formatter : function(value, row){
				return "<a onclick='studyModify(" + row.seq + ")' class='dg-btn'> 수정 </a>" 
			}},
			{field:'delete', title:'삭제', width:'70px', halign:'center', styler : alignCenter, formatter : function(value, row){
				return "<a onclick='studyDelete(" + row.seq + ")' class='dg-btn'> 삭제 </a>" 
			}},
			{field:'sect', title:'영역', width:'150px', halign:'center', sortable : "true", styler : alignLeft},
			{field:'codeLang', title:'소스언어', width:'150px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'title', title:'이름', width:'300px', halign:'center', sortable : "true", styler : alignLeft},
			{field:'comtCnt', title:'댓글수', width:'70px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'hits', title:'조회수', width:'70px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'date', title:'작성일', width:'100px', halign:'center', sortable : "true", styler : alignCenter},
		]]
	});
}

/* when '보기' click */
function studyView(seq){
	window.location.href = getContextPath() + "/study/view?seq=" + seq;		
}

/* when '삭제' click */
function studyDelete(seq){
	swal({
		  title: "정말로 삭제 하시겠습니까?",
		  text: "삭제된 게시글은 복구 할 수 없습니다.",
		  icon: "warning",
		  buttons: ["취소", "삭제"],
		  dangerMode: true,
		})
		.then(function(willDelete){
		  if (willDelete) {
			  window.location.href = getContextPath() + "/mgnt/study/delete.do?seq=" + seq;	
		  } 
		});
}

/* when '수정' click */
function studyModify(seq){
	window.location.href = getContextPath() + "/mgnt/study/upload?seq=" + seq;		
}