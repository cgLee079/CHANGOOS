<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/item-view.css" />
<script src="${pageContext.request.contextPath}/resources/js/pager-1.0.0.js"></script>
<script>
function contentImgResize(){
	var imgs = $(".item-content img");
	imgs.each(function(){
		var parentWidth = parseInt($(".item-content").width());
		var width 	= parseInt($(this).css("width"));
		var height 	= parseInt($(this).css("height"));
		var ratio	= width / height;
		
		$(this).css("width", "");
		$(this).css("height", "");
		
		if(width > parentWidth){
			$(this).css("width", "100%");
		} else if (width <= parentWidth){
			$(this).css("width", "");
			width 	= parseInt($(this).css("width"));
			if(width > parentWidth){
				$(this).css("width", "100%");
			}
		} 
		
	});
}

function contentYoutubeResize(){
	var parentWidth = parseInt($(".item-content").width());
	var videos = $("iframe");
	videos.each(function(){
		if(parentWidth >= 640){
			$(this).attr("width", "640");
			$(this).attr("height", "360");
		} else{
			var width = parentWidth;
			var ratio = parseFloat($(this).attr("width") /$(this).attr("height"));
			$(this).attr("width", width);
			$(this).attr("height", width / ratio );
		} 
	});
}


function resizedw(){
	contentImgResize();
	contentYoutubeResize();
}

var doit;
$(window).resize(function(){
  clearTimeout(doit);
  doit = setTimeout(resizedw, 100);
});

window.onload = function(){
	var lineDrawing = anime({
		targets: ".item-line",
		easing: "easeInQuad",
		width : [0, "100%"],
		duration: 400,
	});
	
	if(isMobile){
		$(".btn-item-before, .btn-item-next").addClass("display-none");
	}
	
	$(".btn-item-next").on("click", function(){
		var afterItemSeq = '${afterItem.seq}';
		
		if(afterItemSeq){
			Progress.start();
			window.location.href = getContextPath() + "/item/view?seq=" + afterItemSeq;
		} 
	});
	
	$(".btn-item-before").on("click", function(){
		var beforeItemSeq= '${beforeItem.seq}';
		if(beforeItemSeq){
			Progress.start();
			window.location.href = getContextPath() + "/item/view?seq=" + beforeItemSeq;
		} 
	});
	
	/*
	$(".wrapper").touchwipe({
	     wipeLeft: function() {
	    	 $(".btn-item-next").trigger("click");
	     },		     
	     wipeRight: function() {
	    	 $(".btn-item-before").trigger("click");
	     },		     
	     min_move_x: 30,
	     min_move_y: 20,
	     preventDefaultEvents: true
	});
	*/
	
	contentImgResize();
	contentYoutubeResize();
}

/*script about comment */

var page;
var perPgLine 	= 10;
var itemSeq	= '${item.seq}';
var comtCnt		= parseInt('${comtCnt}');

function commentPageMove(pg){
	$.ajax({
		type	: "POST",
		url		: getContextPath() + "/item/comment_paging.do",
		data	: {
			'itemSeq'	: itemSeq,					
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
	comment += '<input type="hidden" class="comment-itemSeq">';
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
		item.find(".comment-itemSeq").val(comment.itemSeq);
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
	$.ajax({	
		type	: "POST",
		url		: getContextPath() + "/item/comment_update.do",
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
				url		: getContextPath() + "/item/comment_checkPwd.do",
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
			var contents = contentsDiv.text();
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
			url		: getContextPath() + "/item/comment_delete.do",
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
		url		: getContextPath() + "/item/comment_submit.do",
		data	: {
			'itemSeq'	: itemSeq,				
			'name'		: name.val(),					
			'password'	: password.val(),
			'contents' 	: contents.val()
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

/*


 */
</script>
</head>
<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
		
		<div class="item-detail">
			<div class="item-head">
				<h1 class="item-name">${item.name}</h1>
				<div class="item-subinfo">
					<c:if test="${!empty item.date}"> 
						<a class="item-date">DATE : ${item.date}</a> <br/>
					</c:if>
					<a class="item-sect">SECT : ${item.sect}</a>
					,&nbsp
					<a class="item-hits">HITS : ${item.hits}</a>
				</div>
			</div>

			<div class="item-line"></div>
				   			
			<div class="item-subinfo2">
				<c:if test="${!empty item.developer}"> 
					<a class="item-developer">by ${item.developer}</a>
				</c:if>
				<c:if test="${!empty item.sourcecode}">
					<a class="btn row-center item-source" target="_black" href="${item.sourcecode}">
						<img src="${pageContext.request.contextPath}/resources/image/btn_itemview_source.png" style="width:0.8rem; height:0.8rem; margin-right:0.1rem">
						SOURCE
					</a>
				</c:if>
			</div>
			
			<div class="item-content">
				<c:if test="${item.video eq 'N'}">
					<img class="item-snapsht" src="${pageContext.request.contextPath}${item.snapsht}" >
				</c:if>
				${item.content}
			</div>
		
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
		</div>
		
		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8">
		</c:import>
		
		<c:if test="${!empty beforeItem}">
			<div class="btn btn-item-before h-reverse" style="background-image: url(${pageContext.request.contextPath}/resources/image/btn_item_arrow.png)"></div>
		</c:if>
		
		<c:if test="${!empty afterItem}">
			<div class="btn btn-item-next" style="background-image: url(${pageContext.request.contextPath}/resources/image/btn_item_arrow.png)"></div>
		</c:if>
	</div>
</body>
</html>