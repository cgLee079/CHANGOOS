$(document).ready(function(){
	doMenuOn(".menu-mgnt-blog");
	fn_onInitDataGrid();
});

/* Datagrid Initialize */
function fn_onInitDataGrid(){
	$('#dg').datagrid({
		url: getContextPath() + '/mgnt/blog/paging',
		method: 'get',
		singleSelect: true,
		remoteSort: true,
		fitColumns: false,
		checkOnSelect: false,
		selectOnCheck: false,
		emptyMsg: '데이터가 없습니다.',
		columns:[[
			{field:'seq', title:'번호', width:'50px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'view', title:'보기', width:'70px', halign:'center', styler : alignCenter, formatter : function(value, row){
				return "<a onclick='blogView(" + row.seq + ")' class='dg-btn'> 보기 </a>" 
			}},
			{field:'modify', title:'수정', width:'70px', halign:'center', styler : alignCenter, formatter : function(value, row){
				return "<a onclick='blogModify(" + row.seq + ")' class='dg-btn'> 수정 </a>" 
			}},
			{field:'delete', title:'삭제', width:'70px', halign:'center', styler : alignCenter, formatter : function(value, row, index){
				return "<a onclick='blogDelete(" + row.seq + "," + index + ")' class='dg-btn'> 삭제 </a>" 
			}},
			{field:'thumbnail', title:'스냅샷', halign:'center',  formatter: function(value, row){
				if(value){
					return "<img src='" + getContextPath() + thumbDir + value + "' width='150px'/>"
				} else if (row.images.length > 0){
					return "<img src='" + getContextPath() + imageDir + row.images[0].pathname + "' width='150px'/>"
				} else{
					return "<img src='' width='100px'/>"
				}
			}},
			{field:'tag', title:'태그', width:'150px', halign:'center',  styler : alignCenter},
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