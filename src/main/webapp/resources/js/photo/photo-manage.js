$(document).ready(() => {
	doMenuOn(menu.MGNT_PHOTO);
	
	$('#dg').datagrid({
		url: getContextPath() + '/mgnt/photos/records',
		method: 'GET',
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
			{field:'thumbnail', title:'스냅샷', width:'100px', halign:'center', styler : alignCenter, formatter: function(value, row){
				return "<img src='" + getContextPath() + loc.photo.thumbDir + value  + "' style='max-width: 90px; max-height: 70px; padding : 1px'/>"
			}},
			{field:'name', title:'이름', width:'200px', halign:'center', sortable : "true", styler : alignLeft},
			{field:'desc', title:'설명', width:'200px', halign:'center', styler : alignLeft},
			{field:'date', title:'날짜', width:'100px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'time', title:'시간', width:'100px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'location', title:'위치', width:'150px', halign:'center', sortable : "true", styler : alignLeft},
			{field:'tag', title:'태그', width:'200px', halign:'center', sortable : "true", styler : alignLeft},
			{field:'device', title:'촬영기기', width:'150px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'likeCnt', title:'좋아요', width:'70px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'enabled', title:'공개여부', width:'70px', halign:'center', sortable : "true", styler : alignCenter},
		]]
	});
});


/* when '삭제' click */
const photoDelete = function(seq, index){
	swal({
		  title: "정말로 삭제 하시겠습니까?",
		  text: "삭제된 사진은 복구 할 수 없습니다.",
		  icon: "warning",
		  buttons: ["취소", "삭제"],
		  dangerMode: true,
		})
		.then(function(willDelete) {
			if (willDelete) {
				$.ajax({
					type	: "DELETE",
					url		:  getContextPath() + "/photos/post/" + seq,
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
		});
	
}

/* when '수정' click' */
const photoModify = function(seq){
	window.location.href = getContextPath() + "/photos/post/" + seq;		
}

