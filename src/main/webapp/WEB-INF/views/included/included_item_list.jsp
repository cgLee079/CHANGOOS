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
	border : 1px solid #DDD;
	opacity: 0.9;
	background-position	: center;
    background-repeat	: no-repeat;
    background-size		: cover;
}

.item-info{
	margin-left : 1rem;
	flex : 1 35%;
	position: relative;
}

.item-title {
	font-size: 1.3rem;
	margin-bottom: 1rem;
}

.item-desc {
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

.item-menu .btn-item-show{
	font-weight : bold;
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
			<div class="item-snapsht" style="background-image: url('${pageContext.request.contextPath}${item.snapsht}')"></div>
			<div class="item-info">
				<div class="item-title">[${item.sect}] ${item.name}</div>
				<div class="item-desc">${item.desc}</div>
				<div class="item-menu">
					<div class="btn-slideup btn-item-show">
						<a onclick="itemView(${item.seq})">SHOW</a>					
					</div>
				</div>
			</div>
			<input type="hidden" id="item-seq" class="item-seq" value="${item.seq}">
		</div>
	</c:forEach>
</div>