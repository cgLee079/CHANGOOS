var page;
var limit; 
var comtFormTemp;
var boardType;
var boardSeq;
var comtCnt;
var isAdmin;
var path;

$(document).ready(function(){
	limit 	= 1000000;
	boardType	= $("#boardType").val();
	boardSeq	= $("#boardSeq").val();
	isAdmin 	= $("#isAdmin").val();
	comtCnt		= parseInt($("#comtCnt").val());
	comtFormTemp= $(".comment-write").clone();
	path = getContextPath() + "/" + boardType + "/" + boardSeq;
	
	drawCommentCnt();
	commentPageMove(1); // Paging
	
	//Resize Area
	$(".comment-write-contents").trigger("keyup");
})

function br2nl(text){
	return text.replace(/(<br\s*\/?>)+/g, "\n");
}

function nl2br(text){
	return text.replace(/\n/g, "<br />");
}

function drawCommentCnt(){
	$(".comment-cnt").text(comtCnt);
}

function commentAreaResize(obj) {
	var height = $(obj).height();
	obj.style.height = "1px";
	obj.style.height = (50 + obj.scrollHeight)+"px";
}


// 댓글 페이징
function commentPageMove(pg){
	$.ajax({
		type	: "GET",
		url		: path + "/comments",
		data	: {
			'offset'	: (pg - 1) * limit,
			'limit' 	: limit
		},
		beforeSend : function(){
			Progress.start();
		},
		dataType: 'JSON',
		success : function(data) {
			page = pg;
			drawComment(data);
			updatePaging("commentPageMove", page, comtCnt, limit, 3);
		},
		complete : function(){
			Progress.stop();
		},
		error : function(e) {
			console.log(e);
			Progress.stop();
		}
	});
}

/* draw Page number */
function updatePaging(callFunc, page, comtCnt, limit, pgGrpCnt){
	var boardPager	= $('.comt-pager');
	boardPager.empty();
	
	if(comtCnt > 0){
		var	pager	= drawPager(callFunc, page, comtCnt, limit, pgGrpCnt);
		boardPager.append(pager);
	}
}

/* draw comment */
function drawComment(data){
	var container = $(".comments");
	var length = data.length;
	var comment;
	var item;
	
	container.empty();
	for(var i = 0; i < length; i++){
		comment = data[i];
		if(!comment.parentComt){
			item = makeComment();
			item.find(".comment-seq").val(comment.seq);
			item.find(".comment-boardSeq").val(comment.boardSeq);
			item.find(".comment-writer").text(comment.username);
			item.find(".comment-date").text(comment.date);
			item.find(".comment-contents").html(comment.contents);
			item.appendTo(container);					
		} else{
			item = makeReplyComment();
			item.find(".comment-seq").val(comment.seq);
			item.find(".comment-boardSeq").val(comment.boardSeq);
			item.find(".comment-writer").text(comment.username);
			item.find(".comment-date").text(comment.date);
			item.find(".comment-contents").html(comment.contents);
			item.appendTo(container);	
		}
	}
	
	/* 일반 댓글 작성 */
	function makeComment(){
		var comment = "";
		comment += '<div class="comment-item">';
		comment += '<input type="hidden" class="comment-seq">';
		comment += '<input type="hidden" class="comment-boardSeq">';
		comment += '<div class="comment">';
		comment += '<div class="comment-head">';
		comment += '<div class="comment-head-info"><a class="comment-writer"></a> <a class="comment-date"></a></div>'
		comment += '<div class="comment-menu">';
		if(isAdmin === 'true'){
			comment += '<a onclick="drawReplyForm(this)" class="btn btn-reply">답변</a>';	
		}
		if(!(isAdmin === 'true')){
			comment += '<a onclick="commentModify(this)" class="btn btn-modify">수정</a>';
		}
		comment += '<a onclick="commentDelete(this)" class="btn">삭제</a>';
		comment += '</div>';
		comment += '</div>';
		comment += '<div class="comment-contents editor-contents"></div>';
		comment += '</div>';
		
		comment += '</div>';
		return $(comment);
	}
	
	/* 답변 댓글 작성  */
	function makeReplyComment(){
		var comment = "";
		comment += '<div class="comment-item reply">';
		comment += '<input type="hidden" class="comment-seq">';
		comment += '<input type="hidden" class="comment-boardSeq">';
		comment += '<div class="comment">';
		comment += '<div class="comment-head">';
		comment += '<div class="comment-head-info"><a class="comment-writer"></a> <a class="comment-date"></a></div>'
		comment += '<div class="comment-menu">';
		if(isAdmin === 'true'){
			comment += '<a onclick="commentModify(this)" class="btn btn-modify">수정</a>';
			comment += '<a onclick="commentDelete(this)" class="btn">삭제</a>';
		}
		comment += '</div>';
		comment += '</div>';
		comment += '<div class="comment-contents editor-contents"></div>';
		comment += '</div>';
		comment += '</div>';
		return $(comment);
	}
}

/* 답변하기 클릭 시, 답변 폼 */
function drawReplyForm(tg){
	var tg 			= $(tg);
	var item		= $(tg).parents(".comment-item");
	var parentComt	= item.find(".comment-seq").val();
	var form 		= makeReplyForm();

	if(tg.hasClass("open")){
		var replyForm = item.next();
		if(replyForm.hasClass("comment-reply")){
			replyForm.remove();
			tg.removeClass("open");
		}
	} else{
		$("<div>", {"class" : "parentComt display-none", text : parentComt}).appendTo(form);
		form.insertAfter(item);	
		tg.addClass("open");
	}
	
	/* Make reply comment form */
	function makeReplyForm(){
		var form = "";
		form += '<div class="comment-reply row-center">';
		form += '<img src="' + getContextPath() + '/resources/image/icon_comment_reply.svg" style="width:1rem; height:1rem; margin-right: 0.3rem">';
		form += '<textarea class="comment-reply-content" id="contents" name="contents"/></textarea>';
		form += '<div onclick="commentDoReply(this)" class="comment-reply-submit col-center">답변</div>';
		form += '</div>';
		return $(form);
	}
}

/* 수정하기 클릭 시*/
function commentModify(tg){
	var tg = $(tg);
	var item= $(tg).parents(".comment-item");
	var seq = item.find(".comment-seq").val();
	
	if(tg.hasClass("open")){
		commentPageMove(page);
	} else {
		commentDoCheck(seq, commentToModifyForm, tg);
	}
}

/* 수정 하기 클릭 시, 수정 폼 변환*/
function commentToModifyForm(tg){
	var item= tg.parents(".comment-item");
	var contentsDiv = item.find(".comment-contents");
	var contents = br2nl(contentsDiv.html());
	tg.toggleClass("open");
	
	var commentModify = $("<div>" , {"class" : "comment-modify"});
	$("<textarea>", {text : contents, id : "contents", "class" : "comment-write-contents"}).appendTo(commentModify);
	$("<div>", {text : "수정", onclick:  "commentDoModify(this)", "class" : "col-center comment-write-submit"}).appendTo(commentModify);
	contentsDiv.empty();
	contentsDiv.append(commentModify);
}

/* 삭제하기 클릭 시 */
function commentDelete(tg){
	var item= $(tg).parents(".comment-item");
	var seq	= item.find(".comment-seq").val();
	
	commentDoCheck(seq, commentDoDelete, seq);		  
}

/* 답변 작성하기 */
function commentDoReply(tg){
	var reply		= $(tg).parents(".comment-reply");
	var parentComt	= reply.find(".parentComt").text();
	var username	= "CHANGOO";
	var password	= "I_AM_ADMIN";
	var contents	= reply.find(".comment-reply-content").val();
	var param = { };
	param['username'] 	= username;
	param['password'] 	= password;
	param['contents'] 	= nl2br(contents);
	param['parentComt']	= parentComt;
	
	$.ajax({	
		type	: "POST",
		url		: path +  "/comments",
		data	: param,
		dataType : "JSON",
		success : function(result) {
			commentPageMove(page);
		}
	});
}

/* 댓글 수정하기 */
function commentDoModify(tg){
	var item	= $(tg).parents(".comment-item");
	var seq 	= item.find(".comment-seq").val();
	var contents = item.find(".comment-modify #contents").val();
	contents = nl2br(contents);
	
	$.ajax({	
		type	: "POST",
		url		: path + "/comments/" + seq,
		data	: {
			'_method'	: "PUT",
			'contents' 	: contents
		},
		dataType : "JSON",
		success : function(result) {
			if(result) {
				commentPageMove(page);
			} else{
				swal({ text : "수정 실패하였습니다.", icon : "error" });
			}
		}
	});
}


/* 비밀번호 검증 */
function commentDoCheck(seq, callback, callBackValue){
	swal({
		text: '비밀번호를 입력해주세요',
		content: {
			element : "input",
			attributes: {
				type: "password",
			}
		},
		buttons : ["취소", "확인"]
	})
	.then(function(password) {
		return $.ajax({	
			type	: "POST",
			url		: path +  "/comments/" + seq + "/check",
			data	: {
				'password'	: password
			},
			async 	: false,
			dataType : "JSON",
			success : function(result) {
				if(result){
					callback(callBackValue);
				} else{
					swal({
						text : '비밀번호가 틀렸습니다',
						icon : "error"
					});
				}
			}
		});
	});
}

function commentDoDelete(seq){
	$.ajax({	
		type	: "DELETE",
		url		: path + "/comments/" + seq,
		dataType: 'JSON',
		success : function(data) {
			if(data){
				swal({
					text : "댓글이 삭제 되었습니다.", 
					icon : "success"
				});
				
				comtCnt = comtCnt - 1;
				drawCommentCnt();
				commentPageMove(parseInt((comtCnt-1) / limit)+1);
			} else{
				swal({
					text : "댓글 삭제 실패!", 
					icon : "error"
				});
			}
		}
	});
}	

/* comment Submit*/
function commentDoSubmit(){
	var username = $(".comment-write #username");
	var password = $(".comment-write #password");
	var contents  = $(".comment-write #contents");
	var param = { };
	param['username'] 	= username.val();
	param['password'] 	= password.val();
	param['contents'] 	= nl2br(contents.val());
	
	if(!username.val()) { swal({text : "이름을 입력해주세요.", icon : "warning"}); return ;}
	if(!password.val()) { swal({text : "비밀번호를 입력해주세요.", icon : "warning"}); return ;}
	if(!contents.val()) { swal({text : "내용을 입력해주세요.", icon : "warning"}); return ;}
	
	$.ajax({
		type	: "POST",
		url		: path + "/comments",
		data	: param,
		dataType: 'JSON',
		beforeSend : function(){
			Progress.start();
		},
		success : function(data) {
			if(data){
				swal({
					text : "댓글이 등록 되었습니다.", 
					icon : "success"
				});
				contents.val('');
				comtCnt = comtCnt + 1;
				drawCommentCnt();
				commentPageMove(parseInt((comtCnt-1) / limit )+1);
			}
		},
		complete : function(){
			Progress.stop();
		},
		error : function(e) {
			console.log(e);
			swal({
				text : "댓글 등록에 실패하였습니다.", 
				icon : "error"
			});
		}
	});
}
