<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board-view-basic.css" />
<script src="${pageContext.request.contextPath}/resources/js/pager-1.0.0.js"></script>
</head>
<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
		
		<div class="wrap-board">
			<div class="board-submenu">
				<a class="btn">목록</a>
				<a class="btn">이전글</a>
				<a class="btn">다음글</a>
			</div>
			<div class="board-detail">
				<div class="board-head">
					<div class="board-title">${board.title}</div>
					<div style="height : 1px; background: #CCC; margin: 0.5rem 0rem" ></div>
					<div class="board-info">
						<a>${board.sect}</a>
						<a>${board.date}</a>
						<a>조회수 ${board.hits}</a>
					</div>
				</div>
				
				<div class="board-contents">	
						<h2>OCP, Open-Closed Principle (개방-폐쇄 원칙)</h2>
					
						<p>소프트웨어 개체(클래스, 모듈, 함수 등등)는 확장에 대해 열려 있어야 하고,<br/>
						수정에 대해서는 닫혀 있어야 한다<br/>
						<br/>
						기존의 코드를 변경하지 않으면서, 기능을 추가 할 수 있도록 설계한다.</p>
						
						<p><br/>
						&nbsp;</p>
						
						<h3>#Before Branch</h3>
						
						<p><img alt="" src="/resources/image/item/contents/content_171205_132752_OCP before.jpg" style="height:300px; width:470px"/><br/>
						<br/>
						AreaCalculator 클래스는 shape들의 넓이의 합을 계산하고.<br/>
						ConsolePrinter 클래스는 결과를 출력한다.<br/>
						<br/>
						이 프로그램은 OCP원칙이 위배됨을 보여준다</p>
						
						<p>&nbsp;</p>
						
						<p>&nbsp;</p>
						
						<p><strong>AreaCaclualotr.java</strong></p>
				</div>
			</div>
			
			<script>
			var page;
			var perPgLine 	= 5;
			var boardSeq	= '${board.seq}';
			var comtCnt		= parseInt('${comtCnt}');
			
			function commentPageMove(pg){
				$.ajax({
					type	: "POST",
					url		: getContextPath() + "/board/comment_paging.do",
					data	: {
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
				var boardPager	= $('.bcomt-pager');
				var	pager		= drawPager(callFunc, page, comtCnt, perPgLine, pgGrpCnt);
					
				boardPager.empty();
				boardPager.append(pager);
			}
			
			function makeComment(){
				var comment = "";
				comment += '<div class="comment-item">';
				comment += '<input type="hidden" class="comment-seq">';
				comment += '<input type="hidden" class="comment-boardseq">';
				comment += '<div class="comment">';
				comment += '<a class="comment-writer"></a> <a class="comment-date"></a>'
				comment += '<div class="comment-contents"></div>';
				comment += '</div>';
				comment += '<div class="comment-menu">';
				comment += '<a onclick="commentDelete(this)" class="btn">삭제</a>';
				comment += '</div>';
				comment += '</div>';
				return comment;
			}
			
			function updateComment(data){
				var container = $(".board-comments");
				var length = data.length;
				var comment;
				var item;
				
				container.empty();
				for(var i = 0; i < length; i++){
					comment = data[i];
					item = $(makeComment());
					item.find(".comment-seq").val(comment.seq);
					item.find(".comment-boardseq").val(comment.boardSeq);
					item.find(".comment-writer").text(comment.name);
					item.find(".comment-date").text(comment.date);
					item.find(".comment-contents").text(comment.contents);
					item.appendTo(container);					
				}
			}
			
			function commentDelete(tg){
								
				/*
				var item= $(tg).parents(".comment-item");
				var seq	= item.find(".comment-seq").val();
				$.ajax({	
					type	: "POST",
					url		: getContextPath() + "/board/comment_delete.do",
					data	: {
						'seq' : seq				
					},
					dataType: 'JSON',
					success : function(data) {
						if(data){
							alert("댓글이 삭제 되었습니다.");
							comtCnt = comtCnt - 1;
							commentPageMove(parseInt((comtCnt-1) / perPgLine)+1);
						}
					}
				});
				*/
			}
			
			function commentSubmit(){
				var name = $(".comment-write-pinfo #name");
				var password = $(".comment-write-pinfo #password");
				var contents  = $(".comment-write-contents");
				
				$.ajax({
					type	: "POST",
					url		: getContextPath() + "/board/comment_submit.do",
					data	: {
						'boardSeq'	: boardSeq,				
						'name'		: name.val(),					
						'password'	: password.val(),
						'contents' 	: contents.val()
					},
					dataType: 'JSON',
					success : function(data) {
						if(data){
							alert("댓글이 등록 되었습니다.");
							name.val('');
							password.val('');
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
			<div class="warp-board-comment">
				<div class="board-comments"></div>
				<div class="bcomt-pager"></div>
				
				<div class="comment-write">
					<div class="comment-write-pinfo">
						<input type="text" id="name" name="name" placeholder="name">
						<input type="password" id="password" name="password" placeholder="password">					
					</div>
					
					<textarea class="comment-write-contents" id="contents" name="contents"></textarea>
					<div onclick="commentSubmit()" class="comment-write-submit">등록</div>				
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