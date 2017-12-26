<%@ page pageEncoding="UTF-8"%>

<style>
.wrap-comment {
	border: 1px solid #DDD;
	background: #FFF;
	margin-top: 1rem;
	padding: 1rem;
}

.comment-item {
	display: flex;
	flex-flow: row nowrap;
	justify-content: space-between;
	padding: 0.5rem;
	border-bottom: 1px solid #F0F0F0;
}

.comment {
	flex: 1;
}

.comment .comment-writer {
	color: #00D;
	font-size: 0.7rem;
}

.comment .comment-date {
	color: #777;
	font-size: 0.5rem;
}

.comment .comment-contents {
	color: #444;
	font-size: 0.6rem;
	padding-top : 0.5rem;
	word-break:break-all;
	word-wrap:break-word;
}

.comment-menu {
	font-size: 0.6rem;
	width: 5rem;
	color: #444;
	text-align: center;
}

.comment-menu a {
	margin-left: 0.5rem;
}

.comt-pager {
	margin: 1rem;
	text-align: center;
}

.comment-write {
	display: flex;
	flex-flow: row nowrap;
	justify-content: space-between;
	margin: 1rem 0.5rem;
	margin-top: 2rem;
	height: 3rem;
}

.comment-write-pinfo {
	display: flex;
	flex-flow: column nowrap;
	width: 5rem;
	height: 100%;
	margin-right: 0.5rem;
}

.comment-write-pinfo input {
	width: 100%;
	height: 1rem;
	font-size: 0.5rem;
	margin-bottom: 0.2rem;
	padding-left : 0.2rem;
	border: 1px solid #CCC;
}

.comment-write-contents {
	flex: 1;
	height: 100%;
	resize: none;
	overflow: hidden;
	border: 1px solid #CCC;
}

.comment-write-submit {
	color: #FFF;
	width: 5rem;
	height: 100%;
	background: #666;
	cursor: pointer;
	font-size: 0.7rem;
}

.comment-modify{
	display : flex; 
	flex-flow : row wrap;
	height : 50px;
}
</style>


<div class="wrap-comment">
	<div class="comments"></div>
	<div class="comt-pager"></div>

	<div class="comment-write">
		<div class="comment-write-pinfo">
			<input type="text" id="name" name="name" placeholder="name">
			<input type="password" id="password" name="password" placeholder="password">
		</div>

		<textarea class="comment-write-contents" id="contents" name="contents"></textarea>
		<div onclick="commentSubmit()" class="comment-write-submit col-center">등록</div>
	</div>
</div>

<script>

/*** script about comment ****/


var page;
var perPgLine 	= 10;
var boardType	= "${param.boardType}";
var boardSeq	= "${param.boardSeq}";
var comtCnt		= parseInt("${param.comtCnt}");

function br2nl(text){
	return text.replace(/(<br\s*\/?>)+/g, "\n");
}

function nl2br(text){
	return text.replace(/\n/g, "<br />");
}
function commentPageMove(pg){
	$.ajax({
		type	: "POST",
		url		: getContextPath() + "/comment/paging.do",
		data	: {
			'boardType'	: boardType,			
			'boardSeq'	: boardSeq,					
			'page'		: pg,
			'perPgLine' : perPgLine
		},
		dataType: 'JSON',
		success : function(data) {
			page = pg;
			updateComment(data);
			updatePaging("commentPageMove", page, comtCnt, perPgLine, 3);
		},
		error : function(e) {
			console.log(e);
		}
	});
}

function updatePaging(callFunc, page, comtCnt, perPgLine, pgGrpCnt){
	var boardPager	= $('.comt-pager');
	var	pager		= drawPager(callFunc, page, comtCnt, perPgLine, pgGrpCnt);
		
	boardPager.empty();
	boardPager.append(pager);
}

function makeComment(){
	var comment = "";
	comment += '<div class="comment-item">';
	comment += '<input type="hidden" class="comment-seq">';
	comment += '<input type="hidden" class="comment-boardSeq">';
	comment += '<div class="comment">';
	comment += '<a class="comment-writer"></a> <a class="comment-date"></a>'
	comment += '<div class="comment-contents"></div>';
	comment += '</div>';
	comment += '<div class="comment-menu">';
	comment += '<a onclick="commentModify(this)" class="btn">수정</a>';
	comment += '<a onclick="commentDelete(this)" class="btn">삭제</a>';
	comment += '</div>';
	comment += '</div>';
	return $(comment);
}

function updateComment(data){
	var container = $(".comments");
	var length = data.length;
	var comment;
	var item;
	
	container.empty();
	for(var i = 0; i < length; i++){
		comment = data[i];
		item = makeComment();
		item.find(".comment-seq").val(comment.seq);
		item.find(".comment-boardSeq").val(comment.boardSeq);
		item.find(".comment-writer").text(comment.name);
		item.find(".comment-date").text(comment.date);
		item.find(".comment-contents").html(comment.contents);
		item.appendTo(container);					
	}
}

function doModify(tg){
	var item	= $(tg).parents(".comment-item");
	var seq 	= item.find(".comment-seq").val();
	var contents = item.find(".comment-modify #contents").val();
	contents = nl2br(contents);
	
	$.ajax({	
		type	: "POST",
		url		: getContextPath() + "/comment/update.do",
		data	: {
			'seq' : seq,
			'contents' : contents
		},
		dataType : "JSON",
		success : function(result) {
			if(result) {
				commentPageMove(page);
			} else{
				alert("수정 실패하였습니다.");
			}
		}
	});
}

function commentModify(tg){
	var tg = $(tg);
	if(tg.hasClass("open")){
		commentPageMove(page);
	} else {
		var person = prompt("비밀번호를 입력해주세요", "");
		if(person){
			var item= tg.parents(".comment-item");
			var seq	= item.find(".comment-seq").val();
			$.ajax({	
				type	: "POST",
				url		: getContextPath() + "/comment/checkPwd.do",
				data	: {
					'seq' : seq,
					'password' : person
				},
				dataType : "JSON",
				success : function(result) {
					console.log(result);
					if (result){
						changeToForm();
					} else{
						alert("비밀번호가 틀렸습니다.");
					}
				}
			});
		}
		
		function changeToForm(){
			var contentsDiv = item.find(".comment-contents");
			var contents = br2nl(contentsDiv.html());
			tg.toggleClass("open");
			
			var commentModify = $("<div>" , {"class" : "comment-modify"});
			$("<textarea>", {text : contents, id : "contents", "class" : "comment-write-contents"}).appendTo(commentModify);
			$("<div>", {text : "등록", onclick:  "doModify(this)", "class" : "col-center comment-write-submit"}).appendTo(commentModify);
			contentsDiv.empty();
			contentsDiv.append(commentModify);
		}
	} 
}

function commentDelete(tg){
	var person = prompt("비밀번호를 입력해주세요", "");
	
	if (person){
		var item= $(tg).parents(".comment-item");
		var seq	= item.find(".comment-seq").val();
		$.ajax({	
			type	: "POST",
			url		: getContextPath() + "/comment/delete.do",
			data	: {
				'seq' : seq,
				'password' : person
			},
			dataType: 'JSON',
			success : function(data) {
				if(data){
					alert("댓글이 삭제 되었습니다.");
					comtCnt = comtCnt - 1;
					commentPageMove(parseInt((comtCnt-1) / perPgLine)+1);
				} else{
					alert("비밀번호가 틀렸습니다.");
				}
			}
		});
	}
}

function commentSubmit(){
	var name = $(".comment-write #name");
	var password = $(".comment-write #password");
	var contents  = $(".comment-write #contents");
	
	if(!name.val()) { alert("이름을 입력해주세요."); return ;}
	if(!password.val()) { alert("비밀번호를 입력해주세요."); return ;}
	if(!contents.val()) { alert("내용을 입력해주세요."); return ;}
	
	$.ajax({
		type	: "POST",
		url		: getContextPath() + "/comment/submit.do",
		data	: {
			'boardType' : boardType,			
			'boardSeq'	: boardSeq,				
			'name'		: name.val(),					
			'password'	: password.val(),
			'contents' 	: nl2br(contents.val())
		},
		dataType: 'JSON',
		success : function(data) {
			if(data){
				alert("댓글이 등록 되었습니다.");
				contents.val('');
				comtCnt = comtCnt + 1;
				commentPageMove(parseInt((comtCnt-1) / perPgLine)+1);
			}
		},
		error : function(e) {
			console.log(e);
		}
	});
}

$(document).ready(function(){
	commentPageMove(1);
})

</script>