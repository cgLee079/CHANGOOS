$(document).ready(function(){
	doMenuOn(menu.MGNT_BLOG);
	
	init();
});

/* Datagrid Initialize */
let init = function datagridInit(){
	const limit = 8;
	
	$('#dg').datagrid({
		toolbar : '#tb',
		url: getContextPath() + '/mgnt/blogs/records',
		method: 'get',
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
				return "<a onclick='blogView(" + row.seq + ")' class='dg-btn'> 보기 </a>" 
			}},
			{field:'modify', title:'수정', width:'70px', halign:'center', styler : alignCenter, formatter : function(value, row){
				return "<a onclick='blogModify(" + row.seq + ")' class='dg-btn'> 수정 </a>" 
			}},
			{field:'delete', title:'삭제', width:'70px', halign:'center', styler : alignCenter, formatter : function(value, row, index){
				return "<a onclick='blogDelete(" + row.seq + "," + index + ")' class='dg-btn'> 삭제 </a>" 
			}},
			{field:'thumbnail', title:'스냅샷', width:'100px', halign:'center', styler : alignCenter,  formatter: function(value, row){
				let src = "";
				
				if(value){
					src = getContextPath() + loc.blog.thumbDir + value;
				} else if (row.images.length > 0){
					src = getContextPath() + loc.blog.imageDir + row.images[0].pathname;
				} 
				
				return "<img src='" + src + "' style='max-width: 90px; max-height: 70px; padding : 1px'/>"
				
				style='max-width: 100px; max-height: 50px;'
			}},
			{field:'tag', title:'태그', width:'150px', halign:'center',  styler : alignCenter},
			{field:'title', title:'이름', width:'300px', halign:'center', sortable : "true", styler : alignLeft},
			{field:'comtCnt', title:'댓글수', width:'70px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'hits', title:'조회수', width:'70px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'date', title:'작성일', width:'100px', halign:'center', sortable : "true", styler : alignCenter},
			{field:'enabled', title:'공개여부', width:'100px', halign:'center', sortable : "true", styler : alignCenter},
		]],
		pageList: [limit, limit*2, limit*3, limit*4],
	  	pageSize: limit,
	});
	
	const pager = $('#dg').datagrid('getPager');    // get the pager of datagrid
	pager.pagination({ 
		displayMsg : '{total} 중 {from}-{to} 스터디',
		beforePageText : '',
		afterPageText : '페이지 ',
	});
}


let search = function serachByTitle(){
	 $('#dg').datagrid('load',{
		 title: $('#title').val(),
	 });
}

/* when '보기' click */
let blogView = function (seq){
	window.location.href = getContextPath() + "/blogs/" + seq;		
}

/* when '삭제' click */
let blogDelete = function(seq, index){
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
				url		: getContextPath() + "/blogs/post/" + seq,
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
let blogModify = function(seq){
	window.location.href = getContextPath() + "/blogs/post/" + seq;		
}