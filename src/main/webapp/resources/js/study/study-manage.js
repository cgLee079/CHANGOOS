$(document).ready(() =>{
	doMenuOn(menu.MGNT_STUDY);
	
	const limit = 20;
	
	$('#dg').datagrid({
		toolbar : '#tb',
		url: getContextPath() + '/mgnt/studies/records',
		method: 'GET',
		singleSelect: true,
		remoteSort: true,
		pagination: true,
		fitColumns: false,
		checkOnSelect: false,
		selectOnCheck: false,
		emptyMsg: '데이터가 없습니다.',
		columns:[[
			{field:'seq', title:'번호', width:'50px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'view', title:'보기', width:'70px', halign:'center', styler : alignCenter, formatter : function(value, row){
				return "<a onclick='studyView(" + row.seq + ")' class='dg-btn'> 보기 </a>" 
			}},
			{field:'modify', title:'수정', width:'70px', halign:'center', styler : alignCenter, formatter : function(value, row){
				return "<a onclick='studyModify(" + row.seq + ")' class='dg-btn'> 수정 </a>" 
			}},
			{field:'delete', title:'삭제', width:'70px', halign:'center', styler : alignCenter, formatter : function(value, row, index){
				return "<a onclick='studyDelete(" + row.seq + "," + index + ")' class='dg-btn'> 삭제 </a>" 
			}},
			{field:'category', title:'카테고리', width:'150px', halign:'center', sortable : "true", styler : alignLeft},
			{field:'codeLang', title:'소스언어', width:'150px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'title', title:'이름', width:'300px', halign:'center', sortable : "true", styler : alignLeft},
			{field:'comtCnt', title:'댓글수', width:'70px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'hits', title:'조회수', width:'70px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'date', title:'작성일', width:'100px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'enabled', title:'공개여부', width:'100px', halign:'center', sortable : "true", styler : alignCenter},
		]],
		//Paging
		pageList: [limit, limit*2, limit*3, limit*4],
	  	pageSize: limit,
	});
	
	const pager = $('#dg').datagrid('getPager');    // get the pager of datagrid
	pager.pagination({ 
		displayMsg : '{total} 중 {from}-{to} 스터디',
		beforePageText : '',
		afterPageText : '페이지 ',
	});
});

const search = function searchByTitle(){
	 $('#dg').datagrid('load',{
		 title: $('#title').val(),
	 });
}

/* Datagrid Initialize */

/* when '보기' click */
const studyView = function(seq){
	window.location.href = getContextPath() + "/studies/" + seq;		
}

/* when '삭제' click */
const studyDelete = function(seq, index){
	swal({
		  title: "정말로 삭제 하시겠습니까?",
		  text: "삭제된 게시글은 복구 할 수 없습니다.",
		  icon: "warning",
		  buttons: ["취소", "삭제"],
		  dangerMode: true,
		})
		.then(function(willDelete){
		  if (willDelete) {
			$.ajax({
				type	: "DELETE",
				url		: getContextPath() + "/studies/post/" + seq,
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

/* when '수정' click */
const studyModify = function(seq){
	window.location.href = getContextPath() + "/studies/post/" + seq;		
}