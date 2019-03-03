let boardType;
let boardSeq;
let isAdmin;
let path;

const paging = {
	page : 0,
	limit : 100000,
	item : undefined,
	totalCount : undefined,
	drawCount(){
		$(".comment-cnt").text(this.totalCount);
	},
	move(){
		$.ajax({
			type	: "GET",
			url		: path + "/comments",
			context : this,
			data	: {
				'offset'	: this.page * this.limit,
				'limit' 	: this.limit
			},
			beforeSend : function(){
				Progress.start();
			},
			dataType: 'JSON',
			success : function(data) {
				this.page++;
				this.draw(data);
			},
			complete : function(){
				Progress.stop();
			},
			error : function(e) {
				console.log(e);
				Progress.stop();
			}
		});
	},
	draw(data){
		const container = $(".comments");
		const length = data.length;
		let comment;
		let item;
		
		container.empty();
		for(let i = 0; i < length; i++){
			comment = data[i];
			if(!comment.parentComt){ 
				item = makeComment();
				item.find(".comment-seq").val(comment.seq);
				item.find(".comment-boardSeq").val(comment.boardSeq);
				item.find(".comment-writer").text(comment.username);
				item.find(".comment-date").text(comment.date);
				item.find(".comment-contents").html(comment.contents);
				item.appendTo(container);					
			} else{ // Reply comment
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
			let comment = "";
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
			let comment = "";
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
	
}

$(document).ready(() => {
	boardType	= $("#boardType").val();
	boardSeq	= $("#boardSeq").val();
	isAdmin 	= $("#isAdmin").val();
	path = getContextPath() + "/" + boardType + "/" + boardSeq;
	
	paging.totalCount	= parseInt($("#comtCnt").val());
	paging.item	= $(".comment-write").clone();
	paging.drawCount();
	paging.move(); // Paging
	
	//Resize Area
	$(".comment-write-contents").trigger("keyup");
})

const commentAreaResize = function(obj) {
	const height = $(obj).height();
	obj.style.height = "1px";
	obj.style.height = (50 + obj.scrollHeight)+"px";
}

/* 답변하기 클릭 시, 답변 폼 */
const drawReplyForm = function(target){
	const tg 		= $(target);
	const item		= tg.parents(".comment-item");
	const parentComt= item.find(".comment-seq").val();
	const form 		= makeReplyForm();

	if(tg.hasClass("open")){
		const replyForm = item.next();
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
		let form = "";
		form += '<div class="comment-reply row-center">';
		form += '<img src="' + getContextPath() + '/resources/image/icon_comment_reply.svg" style="width:1rem; height:1rem; margin-right: 0.3rem">';
		form += '<textarea class="comment-reply-content" id="contents" name="contents"/></textarea>';
		form += '<div onclick="commentDoReply(this)" class="comment-reply-submit col-center">답변</div>';
		form += '</div>';
		return $(form);
	}
}

/* 수정하기 클릭 시*/
const commentModify = function(target){
	const tg = $(target);
	const item= tg.parents(".comment-item");
	const seq = item.find(".comment-seq").val();
	
	if(tg.hasClass("open")){
		commentPageMove(page);
	} else {
		commentDoCheck(seq, commentToModifyForm.bind(tg));
	}
}

/* 수정 하기 클릭 시, 수정 폼 변환*/
const commentToModifyForm = function(){
	const item= this.parents(".comment-item");
	const contentsDiv = item.find(".comment-contents");
	const contents = br2nl(contentsDiv.html());
	this.toggleClass("open");
	
	const commentModify = $("<div>" , {"class" : "comment-modify"});
	$("<textarea>", {text : contents, id : "contents", "class" : "comment-write-contents"}).appendTo(commentModify);
	$("<div>", {text : "수정", onclick:  "commentDoModify(this)", "class" : "col-center comment-write-submit"}).appendTo(commentModify);
	contentsDiv.empty();
	contentsDiv.append(commentModify);
}

/* 삭제하기 클릭 시 */
const commentDelete = function(tg){
	const item= $(tg).parents(".comment-item");
	const seq	= item.find(".comment-seq").val();
	commentDoCheck(seq, commentDoDelete.bind({seq}));		  
}

/* 답변 작성하기 */
const commentDoReply = function(tg){
	const reply		= $(tg).parents(".comment-reply");
	const parentComt	= reply.find(".parentComt").text();
	const username	= "CHANGOO";
	const password	= "I_AM_ADMIN";
	const contents	= reply.find(".comment-reply-content").val();
	const param = { };
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
			paging.page = 0;
			paging.move();
		}
	});
}

/* 댓글 수정하기 */
const commentDoModify = function (tg){
	const item	= $(tg).parents(".comment-item");
	const seq 	= item.find(".comment-seq").val();
	const contents = nl2br(item.find(".comment-modify #contents").val());
	
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
				paging.page = 0;
				paging.move();
			} else{
				swal({ text : "수정 실패하였습니다.", icon : "error" });
			}
		}
	});
}


/* 비밀번호 검증 */
const commentDoCheck = function(seq, callback){
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
					callback();
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

const commentDoDelete = function(){
	$.ajax({	
		type	: "DELETE",
		url		: path + "/comments/" + this.seq,
		dataType: 'JSON',
		success : function(data) {
			if(data){
				swal({
					text : "댓글이 삭제 되었습니다.", 
					icon : "success"
				});
				
				paging.totalCount--;
				paging.drawCount();
				paging.page = 0;
				paging.move();
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
const commentDoSubmit = function(){
	const username = $(".comment-write #username");
	const password = $(".comment-write #password");
	const contents  = $(".comment-write #contents");
	const param = { };
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
				paging.totalCount++;
				paging.drawCount();
				paging.page = 0;
				paging.move();
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
