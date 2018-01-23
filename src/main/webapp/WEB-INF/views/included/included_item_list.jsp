<%@ page pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/included/included-item-list.css" />
<script>
var currentView = 0;
var tops 	= [];
var pass 	= [];

var itemViews;

function itemView(seq){
	Progress.start();
	window.location.href = getContextPath() + "/item/view?seq=" + seq;		
}

$(document).ready(function(){
	itemViews = $(".item-view");
	itemViews.each(function(){
		tops.push($(this).offset().top);
		pass.push(false);
	});
	
	if(isMobile){
		itemViews.each(function(){
			$(this).bind("click", function(){
				var seq = $(this).find("#item-seq").val();
				console.log(seq);
				itemView(seq);
			});
		});	
	}
	
	
	$(window).scroll(function(){
		var scrollTop = $(window).scrollTop();
		if(tops[currentView] <= (scrollTop + 550) && pass[currentView] == false){
			pass[currentView] = true;
			var target = document.querySelector(".item-view:nth-child(" + (currentView + 1)+ ")");
			anime.timeline()
				.add({
					targets: target,
					opacity : [0, 1],
					duration: 650,
					easing : "easeInQuad"
				});
			currentView += 1;
		}
		
	});
	
	$(window).trigger("scroll");
});

</script>
<div class="main-items">
	<c:forEach var="item" items="${items}">
		<div onclick="" class="item-view">
			<div onclick="itemView(${item.seq})" class="item-snapsht" style="background-image: url('${pageContext.request.contextPath}${item.snapsht}')">
				<span class="item-snapsht-overlay">Show</span>
				<div class="item-snapsht-fg"></div>
			</div>
			<div class="item-info">
				<div class="item-title">[${item.sect}] ${item.name}</div>
				<div class="item-desc editor-contents">${item.desc}</div>
				<div class="item-menu">
					<div class="btn-slideup btn-item-show">
						<a onclick="itemView(${item.seq})">Show</a>	
					</div>
				</div>
			</div>
			<input type="hidden" id="item-seq" class="item-seq" value="${item.seq}">
		</div>
	</c:forEach>
</div>