<style>
.main-items{
	display: flex;
	flex-flow: row wrap;
	align-items: flex-start;
}   

.item-view{
	width : 33%;
	border: 1px solid #EEE;
	background: #FFF;
	cursor: pointer;
	overflow: hidden;
	position: relative;
	opacity: 0.8;
}

.item-view:hover > .item-snapsht{
	transform: scale(1.1);
	transition: all .5s;
}

.item-snapsht{
	height : 100%;
	width  : 100%;
	background-position	: center;
    background-repeat	: no-repeat;
    background-size		: cover;
    overflow: hidden;
}

.item-snapsht::before {
	content: "";
	display: none;
	height: 100%;
	width: 100%;
	position: absolute;
	top: 0;
	left: 0;
	background-color: rgba(0, 0, 0, 0.7);
}

.item-view:hover .item-snapsht:before,
.item-view:focus .item-snapsht:before {
	display: block;
}

.item-view .item-info{
	color: white; /* Good thing we set a fallback color! */
	padding: 25% 15%;
	width : 70%;
	height: 10%;
	overflow : hidden;
	word-break : break-all;
	position: absolute;
}

.item-view:hover .item-info{
	display: block;
}

.item-info .item-title{
	margin-bottom: 0.4rem;
}

.item-info .item-desc{
	font-size: 0.6rem;
}

.item-info .item-desc p{
	margin : 0.1rem 0;
}


@media (max-width: 420px){
	.item-view{
		flex : 1 100%;
	}
	
	.item-snapsht:before {
		display: block;
		background-color: rgba(0, 0, 0, 0.4);
	}
	
	.item-info{
		display: block;
		font-size: 1.5rem;
	}

	.item-info .item-desc{
		font-size: 0.8rem;
	}


}
</style>
<script>
function itemResize(){
	$(".main-items > .item-view").each(function(){
		var width = parseInt($(this).width());
		$(this).css("height", width *(2/3));
	});
}

$(document).ready(function(){
	var ani = anime.timeline({loop : false})
		.add({
			targets : ".main-items .item-view",
			opacity : [0, 0.8],
			easing : "easeInQuad",
			duration : 500,
			delay : function (el, i){
				var length = $(".main-items .item-view").length;
				var gap = 500 / length;
				return (gap * i) + 100;
			}
		});
	
	itemResize();
	$(window).resize(function(){
		itemResize();	
	})
})
</script>
<div class="main-items">
	<c:forEach var="item" items="${items}">
		<div onclick="itemView(${item.seq})" class="item-view">
			<div class="item-snapsht" style="background-image: url('${pageContext.request.contextPath}${item.snapsht}')">
				<div class="item-info display-none">
				<h5 class="item-title">[${item.sect}] ${item.name}</h5>
				<a class="item-desc">${item.desc}</a>
				</div>
			</div>
		</div>
	</c:forEach>
</div>