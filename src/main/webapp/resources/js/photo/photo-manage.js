$(document).ready(function(){
	fn_onInitDataGrid();
});

/* datagrid initialize */
function fn_onInitDataGrid(){
	$('#dg').datagrid({
		url: getContextPath() + '/mgnt/photo/list.do',
		method: 'post',
		singleSelect: true,
		remoteSort: true,
		fitColumns: false,
		checkOnSelect: false,
		selectOnCheck: false,
		emptyMsg: '데이터가 없습니다.',
		columns:[[
			{field:'seq', title:'번호', width:'50px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'modify', title:'수정', width:'70px', halign:'center', styler : alignCenter, formatter : function(value, row){
				return "<a onclick='photoModify(" + row.seq + ")' class='dg-btn'> 수정 </a>" 
			}},
			{field:'delete', title:'삭제', width:'70px', halign:'center', styler : alignCenter, formatter : function(value, row, index){
				return "<a onclick='photoDelete(" + row.seq + "," + index + ")' class='dg-btn'> 삭제 </a>" 
			}},
			{field:'snapsht', title:'스냅샷', width:'100px', halign:'center', styler : alignCenter, formatter: function(value){
				return "<img src='" + getContextPath() + value + "' height='50px' style='padding : 2px'/>"
			}},
			{field:'name', title:'이름', width:'200px', halign:'center', sortable : "true", styler : alignLeft},
			{field:'desc', title:'설명', width:'200px', halign:'center', styler : alignLeft},
			{field:'date', title:'날짜', width:'100px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'time', title:'시간', width:'100px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'sort', title:'정렬', width:'100px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'location', title:'위치', width:'150px', halign:'center', sortable : "true", styler : alignLeft},
			{field:'tag', title:'태그', width:'200px', halign:'center', sortable : "true", styler : alignLeft},
			{field:'device', title:'촬영기기', width:'150px', halign:'center', sortable : "true", styler : alignCenter},
		]]
	});
}

/* when '삭제' click */
function photoDelete(seq, index){
	swal({
		  title: "정말로 삭제 하시겠습니까?",
		  text: "삭제된 사진은 복구 할 수 없습니다.",
		  icon: "warning",
		  buttons: ["취소", "삭제"],
		  dangerMode: true,
		})
		.then(function(willDelete) {
		  if (willDelete) {
			  doDelete(seq, index);
		  } 
		});
	
	/* Ajax */
	function doDelete(seq, index){
		$.ajax({
			type	: "POST",
			url		:  getContextPath() + "/mgnt/photo/delete.do",
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

/* when '수정' click' */
function photoModify(seq){
	window.location.href = getContextPath() + "/mgnt/photo/upload?seq=" + seq;		
}

