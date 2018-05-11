$(document).ready(function(){
	fn_onInitDataGrid();
});

function fn_onInitDataGrid(){
	$('#dg').datagrid({
		url: getContextPath() + '/admin/project/manageList.do',
		method: 'post',
		singleSelect: true,
		remoteSort: true,
		fitColumns: false,
		checkOnSelect: false,
		selectOnCheck: false,
		emptyMsg: '데이터가 없습니다.',
		columns:[[
			{field:'seq', title:'번호', width:'50px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'sort', title:'정렬', width:'50px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'view', title:'보기', width:'70px', halign:'center', styler : alignCenter, formatter : function(value, row){
				return "<a onclick='projectView(" + row.seq + ")' class='dg-btn'> 보기 </a>" 
			}},
			{field:'modify', title:'수정', width:'70px', halign:'center', styler : alignCenter, formatter : function(value, row){
				return "<a onclick='projectModify(" + row.seq + ")' class='dg-btn'> 수정 </a>" 
			}},
			{field:'delete', title:'삭제', width:'70px', halign:'center', styler : alignCenter, formatter : function(value, row){
				return "<a onclick='projectDelete(" + row.seq + ")' class='dg-btn'> 삭제 </a>" 
			}},
			{field:'snapsht', title:'스냅샷', width:'100px', halign:'center', styler : alignCenter, formatter: function(value){
				return "<img src='" + getContextPath() + value + "' height='50px' style='padding : 2px'/>"
			}},
			{field:'title', title:'이름', width:'200px', halign:'center', sortable : "true", styler : alignLeft},
			{field:'desc', title:'설명', width:'200px', halign:'center', styler : alignLeft},
			{field:'developer', title:'개발자', width:'200px', halign:'center', sortable : "true", styler : alignLeft},
			{field:'hits', title:'조회수', width:'70px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'date', title:'날짜', width:'100px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'sourcecode', title:'소스코드', width:'150px', halign:'center', sortable : "true", styler : alignCenter},
		]]
	});
}

function projectView(seq){
	window.location.href = getContextPath() + "/project/view?seq=" + seq;		
}

function projectDelete(seq){
	swal({
		  title: "정말로 삭제 하시겠습니까?",
		  text: "삭제된 프로젝트는 복구 할 수 없습니다.",
		  icon: "warning",
		  buttons: ["취소", "삭제"],
		  dangerMode: true,
		})
		.then(willDelete => {
		  if (willDelete) {
			  window.location.href = getContextPath() + "/admin/project/delete.do?seq=" + seq;	
		  } 
		});
}

function projectModify(seq){
	window.location.href = getContextPath() + "/admin/project/upload?seq=" + seq;		
}