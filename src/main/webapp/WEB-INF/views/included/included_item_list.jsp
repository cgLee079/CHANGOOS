<%@ page pageEncoding="UTF-8"%>
<style>
.item-view{	
	display : flex;
	flex-flow: row wrap;
	background: #FFF;
	border : 1px solid #DDD;
	padding : 2rem;
	margin-bottom: 1rem;
	opacity: 0;
}

.item-snapsht{
	flex : 1 50%;
	height : 400px;
	opacity: 0.9;
	cursor : pointer;
	position : relative;
	background-position	: center;
    background-repeat	: no-repeat;
    background-size		: cover;
}

.item-snapsht .item-snapsht-overlay {
    position: absolute;
    top: 0;
    left: 30px;
    padding: 17px 30px;
    background-color: #000;
    z-index: 2;
    color: #fff;
    font-size: 13px;
    transform-origin: 0 0;
    will-change: transform, opacity;
    transform: scaleY(0) translateZ(0);
    transition: transform 0.3s cubic-bezier(0.215, 0.61, 0.355, 1);
}

.item-snapsht:HOVER .item-snapsht-overlay {
	transform:scaleY(1) translateZ(0);
	transition:transform 0.3s cubic-bezier(0.175, 0.885, 0.32, 1.275);
}

.item-snapsht .item-snapsht-fg{
	position: absolute;
	left : 0;
	top : 0;
	right : 0;
	bottom  : 0;
	z-index: 1;
	background: rgba(0, 0, 0, 0.2);
	transform: scale(0);
}

.item-snapsht:hover .item-snapsht-fg{
	transform: scale(1);
}

.item-info{
	margin-left : 1rem;
	flex : 1 35%;
	position: relative;
}

.item-title {
	font-size: 1.3rem;
}

.item-desc {
	margin-top : 1rem;
	font-size: 0.8rem;
	color : #444;
}

.item-desc p{
	margin: 0.2rem 0;
	line-height: 1.5rem;
}


.item-menu{
	position : absolute;
	bottom: 0;
	right : 0;
}


@media (max-width: 700px){
	.item-view{
		flex-flow: column wrap;
		padding : 1rem;
	}
	
	.item-snapsht{
		flex : 1 30%;
		height : auto;
		border : 1px solid #DDD;
	}
	
	.item-info{
		flex : 1 20%;
		margin: 0;
		margin-top: 2rem;
	}
	
}
</style>


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
				<div class="item-desc">${item.desc}</div>
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