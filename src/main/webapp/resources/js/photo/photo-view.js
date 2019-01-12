var currentView 	= 0;
var loading 		= false;
var photoWrapper 	= undefined;
var seqs 			= undefined;
var templeate		= undefined;

$(document).ready(function(){
	doMenuOn(".menu-photo");
	
	if(isMobile){
		$(".wrap-photo-list").css("display", "none");
		photoWrapper = $(".wrapper");
		$(window).scroll(function(){
			var scrollPosition = $(this).scrollTop() + $(this).outerHeight();
			var docHeight = $(this).height();
			if(scrollPosition >= (docHeight/2) - 10 && !loading && currentView < seqs.length){
				loadPhoto(currentView);
				currentView++;
			}
		});
	} else{
		photoWrapper = $(".photo-list");
		photoWrapper.scroll(function(){
			var scrollPosition = $(this).scrollTop() + $(this).outerHeight();
			var docHeight = $(this)[0].scrollHeight ;
			if(scrollPosition >= (docHeight/2) - 10 && !loading && currentView < seqs.length){
				loadPhoto(currentView);
				currentView++;
			}
		});
	}
	
	templeate = $(".photo-list-item").clone();
	$(".photo-list-item").remove();
	seqs = JSON.parse($("#seqs").val());
	
	loadPhoto(currentView);
	currentView++;
	loadPhoto(currentView);
	currentView++;
});

/* Ajax, when scroll close bottom */
function loadPhoto(currentView){
	var seq = seqs[currentView];
	var photoLoading = $("<div>", {"class" : "photo-loading col-center", "text" : "Loading..."});
	
	$.ajax({
		type	: "GET",
		url		: getContextPath() + "/photos/" + seq, 
		dataType: 'JSON',
		async	: false,
		beforeSend : function(){
			photoLoading.appendTo(photoWrapper);
			loading  = true;
		},
		success : function(photo) {
			var item = templeate.clone();
			item.find(".photo-seq").val(photo.seq);
			item.find(".photo-img").css("background-image", "url('" + getContextPath() + loc.photo.originDir + photo.pathname +"')");
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
			item.appendTo(photoWrapper);
			
			loadComment(item.find(".photo-comments"), seq);
		},
		complete : function(){
			loading  = false;
		},
		error : function(e) {
			console.log(e);
		}
	});
}

/* 댓글 읽어오기 */
function loadComment(parent, seq){
	parent.empty();
	
	$.ajax({
		type	: "GET",
		url		: getContextPath() + "/photos/" + seq +"/comments",
		dataType: 'JSON',
		async	: false,
		success : function(data) {
			var datum;
			var comment;
			for(var i = 0 ; i < data.length; i++){
				datum = data[i];
				comment = $("<div>", {"class" : "comment"});
				$("<input>", {"type": "hidden", "class" : "comment-seq", "value" : datum.seq}).appendTo(comment);
				$("<div>", {"class" : "comment-userinfo", "text" : datum.username}).appendTo(comment);
				$("<div>", {"class" : "comment-contents", "text" : datum.contents}).appendTo(comment);
				$("<div>", {"class" : "comment-date", "text" : datum.date}).appendTo(comment);
				$("<div>", {"class" : "btn btn-comment-delete", "onclick" : "commentDelete(this)", "text" : "삭제"}).appendTo(comment);
				comment.appendTo(parent);
			}
		},
		error : function(e) {
			console.log(e);
		}
	});
}

/* when snapshot click */
function showPhoto(index){
	var scroll = $(".photo-list");
	scroll.scrollTop(0);
	
	if(index >= currentView){
		while(currentView <= index){
			loadPhoto(currentView);
			currentView++;
		}
	}
	
	var photos = $(".photo-list .photo-list-item");
	var tg = photos[index];
	var tgTop = $(tg).offset().top;
	
	scroll.scrollTop(tgTop - 80);
}

/* Ajax, when love icon click. */
function photoDoLike(tg){
	var tg		= $(tg);
	var item 	= tg.parents(".photo-list-item");
	var seq 	= item.find(".photo-seq").val();
	var isUnlike= tg.hasClass("on");
	
	$.ajax({	
		type	: "POST",
		url		:  getContextPath() + "/photos/" + seq + "/like",
		data	: {
			'_method' 	: "PUT",
			'like'		: !isUnlike
		},
		dataType: 'JSON',
		asyncl 	: false,
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
function drawCommentForm(tg){
	var tg = $(tg);
	var writeComment = tg.parents(".photo-sub").find(".photo-write-comment");
	writeComment.toggleClass("none");
}

function commentDelete(tg){
	var tg = $(tg);
	var photoSeq = tg.parents(".photo-list-item").find(".photo-seq").val();
	var seq = tg.parents(".comment").find(".comment-seq").val();
	
	commentDoCheck(photoSeq, seq, commentDoDelete, tg);
}

/* 댓글 검증하기 */
function commentDoCheck(photoSeq, seq, callback, callbackValue){
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
				asyncl 	: false,
				success : function(data) {
					if(data){
						callback(callbackValue);
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
function commentDoDelete(tg){
	var item 	= tg.parents(".photo-list-item");
	var photoSeq= item.find(".photo-seq").val();
	var parent	= item.find(".photo-comments");
	var seq 	= tg.parent(".comment").find(".comment-seq").val();

	$.ajax({	
		type	: "DELETE",
		url		:  getContextPath() + "/photos/" + photoSeq + "/comments/" + seq,
		dataType: 'JSON',
		asyncl 	: false,
		success : function(data) {
			if(data){
				swal({ text : "댓글이 삭제 되었습니다.", icon : "success" });
				loadComment(parent, photoSeq);
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
function commentDoWrite(tg){
	var tg		= $(tg);
	var item 	= tg.parents(".photo-list-item");
	var photoSeq= item.find(".photo-seq").val();
	var parent	= item.find(".photo-write-comment");
	var username= item.find(".photo-write-comment .username");
	var pwd 	= item.find(".photo-write-comment .password");
	var contents= item.find(".photo-write-comment .contents");

	if(!username.val()){ swal({text : "이름을 입력해주세요.", icon : "warning"}); return ;}
	if(!pwd.val()){ swal({text : "비밀번호를 입력해주세요.", icon : "warning"}); return ;}
	if(!contents.val()){ swal({text : "내용을 입력해주세요.", icon : "warning"}); return ;}
	
	$.ajax({
		type	: "POST",
		url		: getContextPath() + "/photos/" + photoSeq + "/comments",
		data	: {
			"username"	: username.val(),
			"password"	: pwd.val(),
			"contents"	: contents.val(),
		},
		dataType: 'JSON',
		async	: false,
		beforeSend : function(){
			Progress.start();
		},
		success : function(data) {
			swal({ text : "댓글이 등록 되었습니다.", icon : "success" });
			contents.val('');
			
			loadComment(item.find(".photo-comments"), photoSeq);
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