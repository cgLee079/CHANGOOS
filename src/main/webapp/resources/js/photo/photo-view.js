var currentView 	= 0;
var loading 		= false;
var photoWrapper 	= undefined;
var seqs 			= undefined;
var templeate		= undefined;

$(document).ready(function(){
	if(isMobile){
		$(".wrap-photo-list").css("display", "none");
		
		photoWrapper = $(".wrapper");
		$(window).scroll(function(){
			var scrollPosition = $(this).scrollTop() + $(this).outerHeight();
			var docHeight = $(this).height();
			if(scrollPosition >= docHeight - 10 && !loading && currentView < seqs.length){
				loadPhoto(currentView);
				currentView++;
			}
		});
	} else{
		photoWrapper = $(".photo-list-scroll");
		photoWrapper.scroll(function(){
			var scrollPosition = $(this).scrollTop() + $(this).outerHeight();
			var docHeight = $(this)[0].scrollHeight ;
			if(scrollPosition >= docHeight - 10 && !loading && currentView < seqs.length){
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

function loadPhoto(currentView){
	var seq = seqs[currentView];
	
	$.ajax({
		type	: "POST",
		url		: getContextPath() + "/photo/view.do",
		data	: {
			'seq'	: seq,					
		},
		dataType: 'JSON',
		async	: false,
		beforeSend : function(){
			loading  = true;
			Progress.start();
		},
		success : function(data) {
			var item = templeate.clone();
			item.find("#photo-seq").val(data.seq);
			item.find(".photo-img").css("background-image", "url('" + getContextPath() + data.image +"')");
			item.find(".photo-name").text(data.name);
			item.find(".photo-date-loc").text(data.date + " " + data.location);
			item.find(".photo-desc").html(data.desc);
			if(data.like != 0){
				item.find(".photo-like").text("♡" + data.like);
			}
			
			if(data.device){
				item.find(".photo-tag").text(data.tag + " D:" + data.device);
			} else{
				item.find(".photo-tag").text(data.tag);
			}
			
			item.appendTo(photoWrapper);
			
			loadComment(item.find(".photo-comments"), seq);
		},
		complete : function(){
			loading  = false;
			Progress.stop();
		},
		error : function(e) {
			console.log(e);
			Progress.stop();
		}
	});
}


function loadComment(parent, seq){
	parent.empty();
	
	$.ajax({
		type	: "POST",
		url		: getContextPath() + "/photo/comment/list.do",
		data	: {
			'photoSeq'	: seq,					
		},
		dataType: 'JSON',
		async	: false,
		success : function(data) {
			var datum;
			var comment;
			for(var i = 0 ; i < data.length; i++){
				datum = data[i];
				comment = $("<div>", {"class" : "comment"});
				$("<input>", {"type": "hidden", "id" : "comment-seq", "value" : datum.seq}).appendTo(comment);
				$("<div>", {"class" : "comment-userinfo", "text" : datum.name}).appendTo(comment);
				$("<div>", {"class" : "comment-contents", "text" : datum.contents}).appendTo(comment);
				$("<div>", {"class" : "btn btn-comment-delete", "onclick" : "deleteComment(this)", "text" : "삭제"}).appendTo(comment);
				comment.appendTo(parent);
			}
		},
		error : function(e) {
			console.log(e);
		}
	});
}

function showPhoto(index){
	var scroll = $(".photo-list .photo-list-scroll");
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

function photoSnapshtResize(){
	var photoItems = $(".photo-item");
	var length = photoItems.length;
	
	photoItems.each(function(){
		var tg = $(this);
		var height 	= parseInt(tg.css("height"));
		var width	= height * (4/3);
		tg.css("width", width);
	});
	
	for(var i = 0; i < length; i++){
		var tg = photoItems.eq(i);
		var width = parseInt(tg.css("width"));
		tg.stop().css("left", (width * i) + (5 * i));
	}
}

function increaseLike(tg){
	var tg		= $(tg);
	var item 	= tg.parents(".photo-list-item");
	var seq 	= item.find("#photo-seq").val();
	
	$.ajax({	
		type	: "POST",
		url		:  getContextPath() + "/photo/increaseLike.do",
		data	: {
			'seq' : seq
		},
		dataType: 'JSON',
		asyncl 	: false,
		success : function(data) {
			item.find(".photo-like").text("♡" + data.like);
		}
	});
}

/* About Comment */
function showWriteComment(tg){
	var tg = $(tg);
	var writeComment = tg.parents(".photo-sub").find(".photo-write-comment");
	writeComment.toggleClass("none");
}

function deleteComment(tg){
	swal({
		  	text: '비밀번호를 입력해주세요',
		  	content: "input",
			buttons : ["취소", "확인"]
		})
		.then((pw) => {
			if(pw){
				doDelete(pw);				  
		  	}
		});
	
	function doDelete(password){
		var item= $(tg).parents(".comment");
		var seq	= item.find("#comment-seq").val();
		
		$.ajax({	
			type	: "POST",
			url		:  getContextPath() + "/photo/comment/delete.do",
			data	: {
				'seq' : seq,
				'password' 	: password,
			},
			dataType: 'JSON',
			asyncl 	: false,
			success : function(data) {
				if(data){
					swal({ text : "댓글이 삭제 되었습니다.", icon : "success" });
					
					var item 	= $(tg).parents(".photo-list-item");
					var photoSeq= item.find("#photo-seq").val();
					var parent	= item.find(".photo-comments");
				
					loadComment(parent, photoSeq);
					
				} else{
					swal({
						text : "비밀번호가 틀렸습니다.", 
						icon : "error"
					});
				}
			}
		});
	}
}

function doWriteComment(tg){
	var tg		= $(tg);
	var item 	= tg.parents(".photo-list-item");
	var seq 	= item.find("#photo-seq").val();
	var parent	= item.find(".photo-write-comment");
	var name	= item.find(".photo-write-comment .name");
	var pwd 	= item.find(".photo-write-comment .password");
	var contents= item.find(".photo-write-comment .contents");


	if(!name.val()){ swal({text : "이름을 입력해주세요.", icon : "warning"}); return ;}
	if(!pwd.val()){ swal({text : "비밀번호를 입력해주세요.", icon : "warning"}); return ;}
	if(!contents.val()){ swal({text : "내용을 입력해주세요.", icon : "warning"}); return ;}
	
	$.ajax({
		type	: "POST",
		url		: getContextPath() + "/photo/comment/submit.do",
		data	: {
			"photoSeq"	: seq,
			"name"		: name.val(),
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
			
			loadComment(item.find(".photo-comments"), seq);
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