$(document).ready(function(){
	doMenuOn(".menu-mgnt-project");
	
	fn_onInitDataGrid();
});

/* datagrid initialize */
function fn_onInitDataGrid(){
	$('#dg').datagrid({
		url: getContextPath() + '/mgnt/projects/records',
		method: 'GET',
		singleSelect: true,
		remoteSort: true,
		fitColumns: false,
		checkOnSelect: false,
		selectOnCheck: false,
		emptyMsg: '데이터가 없습니다.',
		columns:[[
			{field:'seq', title:'번호', width:'50px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'view', title:'보기', width:'70px', halign:'center', styler : alignCenter, formatter : function(value, row){
				return "<a onclick='projectView(" + row.seq + ")' class='dg-btn'> 보기 </a>" 
			}},
			{field:'modify', title:'수정', width:'70px', halign:'center', styler : alignCenter, formatter : function(value, row){
				return "<a onclick='projectModify(" + row.seq + ")' class='dg-btn'> 수정 </a>" 
			}},
			{field:'delete', title:'삭제', width:'70px', halign:'center', styler : alignCenter, formatter : function(value, row, index){
				return "<a onclick='projectDelete(" + row.seq + "," + index + ")' class='dg-btn'> 삭제 </a>" 
			}},
			{field:'thumbnail', title:'썸네일', halign:'center', formatter: function(value){
				return "<img src='" + getContextPath() + thumbDir + value + "' width='100px' height='50px'/>"
			}},
			{field:'subtitle', title:'부제', width:'150px', halign:'center', sortable : "true", styler : alignLeft},
			{field:'title', title:'이름', width:'200px', halign:'center', sortable : "true", styler : alignLeft},
			{field:'desc', title:'설명', width:'200px', halign:'center', styler : alignLeft},
			{field:'developer', title:'개발자', width:'200px', halign:'center', sortable : "true", styler : alignLeft},
			{field:'hits', title:'조회수', width:'70px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'comtCnt', title:'댓글수', width:'70px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'sourcecode', title:'소스코드', width:'150px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'enabled', title:'공개여부', width:'150px', halign:'center', sortable : "true", styler : alignCenter},
		]]
	});
}

/* when '보기' click */
function projectView(seq){
	window.location.href = getContextPath() + "/projects/" + seq;		
}

/* Ajax, when '삭제' click */
function projectDelete(seq, index){
	swal({
		  title: "정말로 삭제 하시겠습니까?",
		  text: "삭제된 프로젝트는 복구 할 수 없습니다.",
		  icon: "warning",
		  buttons: ["취소", "삭제"],
		  dangerMode: true,
		})
		.then(function(willDelete) {
		  if (willDelete) {
			  doDelete(seq, index);
		  } 
		});
	
	function doDelete(seq, index){
		$.ajax({
			type	: "DELETE",
			url		: getContextPath() + "/mgnt/projects/post/" + seq,
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

/* Ajax, when '수정' click */
function projectModify(seq){
	window.location.href = getContextPath() + "/mgnt/projects/post/" + seq;		
}