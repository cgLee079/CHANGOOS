const paging = {
	item : undefined,
	seqs : undefined,
	totalCount : undefined,
	current : 0,
	loading : false,
	next(){
		const seq = this.seqs[this.current++];
		const photoLoading = $("<div>", {"class" : "photo-loading col-center", "text" : "Loading..."});
		
		$.ajax({
			type	: "GET",
			url		: getContextPath() + "/photos/" + seq, 
			dataType: 'JSON',
			context : this,
			beforeSend : function(){
				photoLoading.appendTo($(".photo-list"));
				this.loading = true;
			},
			success : function(photo) {
				const item = this.item.clone();
				item.find(".photo-seq").val(photo.seq);
				item.find(".photo-img").attr("src", getContextPath() + loc.photo.originDir + photo.pathname);
				item.find(".photo-name").text(photo.name);
				item.find(".photo-date-loc").text(photo.date + " " + photo.location);
				item.find(".photo-desc").html(photo.desc);
				
				if(photo.likeCnt != 0){
					item.find(".photo-like").text("♥" + photo.likeCnt);
				}
				
				if(photo.like){
					item.find(".btn-photo-like").addClass("on");
					item.find(".btn-photo-like").attr("src", getContextPath() + "/resources/image/btn-photo-like-on.svg");
				}
				
				if(photo.device){
					item.find(".photo-tag").text(photo.tag + " D:" + photo.device);
				} else{
					item.find(".photo-tag").text(photo.tag);
				}
				
				photoLoading.remove();
				item.appendTo($(".photo-list"));
				
				this.loadComment(item.find(".photo-comments"), seq);
				
			},
			complete : function(){
				this.loading = false;
			},
			error : function(e) {
				console.log(e);
			}
		});
	},
	
	loadComment(parent, seq){
		parent.empty();
		
		$.ajax({
			type	: "GET",
			url		: getContextPath() + "/photos/" + seq +"/comments",
			dataType: 'JSON',
			success : function(data) {
				let datum;
				let comment;
				for(let i = 0 ; i < data.length; i++){
					datum = data[i];
					comment = $("<div>", {"class" : "comment"});
					$("<input>", {"type": "hidden", "class" : "comment-seq", "value" : datum.seq}).appendTo(comment);
					$("<div>", {"class" : "comment-userinfo", "onclick" : "commentUsernameOnClick(this)", "text" : datum.username}).appendTo(comment);
					$("<div>", {"class" : "comment-contents", "text" : datum.contents}).appendTo(comment);
					$("<div>", {"class" : "comment-date", "text" : datum.date}).appendTo(comment);
					$("<div>", {"class" : "btn-comment-delete", "onclick" : "commentDelete(this)", "text" : "삭제"}).appendTo(comment);
					comment.appendTo(parent);
				}
			},
			error : function(e) {
				console.log(e);
			}
		});
	}
}

$(document).ready(function(){
	doMenuOn(menu.PHOTO);
	
	$(window).scroll(() => {
		const scrollBottom = $(this).scrollTop()  + $(window).height();
		const photos  = $(".photo-list-item");
		const lastPhoto = photos.eq(photos.length - 1);
		if(scrollBottom >= lastPhoto.offset().top && !paging.loading && paging.current < paging.totalCount){
			paging.next();
		}
	});
	 
	paging.seqs = JSON.parse($("#seqs").val());
	paging.totalCount = paging.seqs.length;
	paging.item = $(".photo-list-item").clone();
	$(".photo-list-item").remove();
	
	//Default Photos.
	for(let i = 0; i < 2; i++){
		paging.next();
	}
});

/* Ajax, when love icon click. */
const photoDoLike= function(target){
	const tg		= $(target);
	const item 	= tg.parents(".photo-list-item");
	const seq 	= item.find(".photo-seq").val();
	const isUnlike= tg.hasClass("on");
	
	$.ajax({	
		type	: "POST",
		url		:  getContextPath() + "/photos/" + seq + "/like",
		data	: {
			'_method' 	: "PUT",
			'like'		: !isUnlike
		},
		dataType: 'JSON',
		success : function(data) {
			item.find(".photo-like").text("♥" + data.likeCnt);
			if(data.like){
				tg.addClass("on");
				tg.attr("src", getContextPath() + "/resources/image/btn-photo-like-on.svg");
			} else{
				tg.removeClass("on");
				tg.attr("src", getContextPath() + "/resources/image/btn-photo-like.svg");
			}
		},
		error : function(e){
			console.log(e);
		}
	});
}


/*******************
 ** About Comment **
 *******************/

/* 댓글 작성 폼 그리기 */
const drawCommentForm = function(target){
	const tg = $(target);
	const writeComment = tg.parents(".photo-sub").find(".photo-write-comment");
	writeComment.toggleClass("none");
	writeComment.find(".write-comment .contents").trigger("keyup"); //Resize
}

const commentAreaResize = function(tg) {
	tg.style.height = "1px";
	tg.style.height = (8 + tg.scrollHeight)+"px";
}

/* 댓글 이름 클릭시 @Username 작성 */
const commentUsernameOnClick = function(target){
	const tg = $(target);
	const username = tg.text();
	const writeComment = tg.parents(".photo-comments").siblings(".photo-write-comment ");
	const writeCommentContents = writeComment.find(".contents");
	
	writeComment.removeClass("none");
	writeCommentContents.val(writeCommentContents.val() + "@" + username + " ");
	writeCommentContents.focus();
	writeCommentContents.trigger("keyup") // Resize TextArea;
}

const commentDelete = function(target){
	const tg = $(target);
	const photoSeq = tg.parents(".photo-list-item").find(".photo-seq").val();
	const seq = tg.parents(".comment").find(".comment-seq").val();
	
	commentDoCheck(photoSeq, seq, commentDoDelete.bind(tg));
}

/* 댓글 비밀번호 확인*/
const commentDoCheck = function(photoSeq, seq, callback){
	swal({
	  	text: '비밀번호를 입력해주세요',
		content: {
			element : "input",
			attributes: {
				type: "password",
			}
		},
		buttons : ["취소", "확인"]
	}).then(function(password) {
		if(password){
			$.ajax({	
				type	: "POST",
				url		:  getContextPath() + "/photos/" + photoSeq + "/comments/" + seq + "/check",
				data	: {
					'password' 	: password
				},
				dataType: 'JSON',
				async 	: false,
				success : function(data) {
					if(data){
						callback();
					} else{
						swal({
							text : "비밀번호가 틀렸습니다.", 
							icon : "error"
						});
					}
				}
			});		  
	  	}
	});

}

/* 댓글 삭제하기 */
const commentDoDelete = function(){
	const item 		= this.parents(".photo-list-item");
	const photoSeq	= item.find(".photo-seq").val();
	const parent	= item.find(".photo-comments");
	const seq 		= this.parent(".comment").find(".comment-seq").val();

	$.ajax({	
		type	: "DELETE",
		url		:  getContextPath() + "/photos/" + photoSeq + "/comments/" + seq,
		dataType: 'JSON',
		success : function(data) {
			if(data){
				swal({ text : "댓글이 삭제 되었습니다.", icon : "success" });
				paging.loadComment(parent, photoSeq);
			} else{
				swal({
					text : "댓글 삭제 실패!", 
					icon : "error"
				});
			}
		}
	});
}

/* 댓글 작성 하기 */
const commentDoWrite = function(target){
	const tg		= $(target);
	const item 		= tg.parents(".photo-list-item");
	const photoSeq	= item.find(".photo-seq").val();
	const parent	= item.find(".photo-write-comment");
	const username	= item.find(".photo-write-comment .username");
	const pwd 		= item.find(".photo-write-comment .password");
	const contents	= item.find(".photo-write-comment .contents");

	if(!username.val()){ swal({text : "이름을 입력해주세요.", icon : "warning"}); return ;}
	if(!pwd.val()){ swal({text : "비밀번호를 입력해주세요.", icon : "warning"}); return ;}
	if(!contents.val()){ swal({text : "내용을 입력해주세요.", icon : "warning"}); return ;}
	
	$.ajax({
		type	: "POST",
		url		: getContextPath() + "/photos/" + photoSeq + "/comments",
		data	: {
			'username'	: username.val(),
			'password'	: pwd.val(),
			'contents'	: contents.val(),
		},
		dataType: 'JSON',
		beforeSend : function(){
			Progress.start();
		},
		success : function(data) {
			swal({ text : "댓글이 등록 되었습니다.", icon : "success" });
			contents.val('');
			paging.loadComment(item.find(".photo-comments"), photoSeq);
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