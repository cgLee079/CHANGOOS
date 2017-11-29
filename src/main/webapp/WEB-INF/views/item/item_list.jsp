<%@ page pageEncoding="UTF-8" %>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<script>
function itemView(seq){
	var contextPath = "${pageContext.request.contextPath}";
	window.location.href = contextPath + "/item/view?seq=" + seq;		
}
</script>

<style>
	.main{
		width : 90%;
		margin: 0px auto;
		margin-top : 5rem;	
	}
	
	.items-title{
		font-size: 3.5rem;
		font-weight : bold;
		text-align: center;
		margin: 3rem 0px;
	}
	
	.wrap-items{
		display: flex;
		flex-flow: row wrap;
		align-items: flex-start;
	}
	
	.item-view{
		flex : 1 30%;
		border: 1px solid #EEE;
		background: #FFF;
		cursor: pointer;
		overflow: hidden;
		opacity: 0.8;
		position : relative;
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
		background-color: rgba(52, 73, 94, 0.75);
	}
	
	.item-view:hover .item-snapsht:before,
	.item-view:focus .item-snapsht:before {
		display: block;
	}
	
	.item-view .item-info{
		color: white; /* Good thing we set a fallback color! */
		padding: 25%;
		width : 40%;
		word-break : break-all;
		position: absolute;
	}
	
	.item-view:hover .item-info{
		display: block;
	}
	
	.item-info .item-desc{
		font-size: 0.7rem;
	}
	
	@media (max-width: 420px){
		.item-view{
			flex : 1 100%;
		}
	}
</style>
<script>
$(document).ready(function(){
	var ani = anime.timeline({loop : false})
		.add({
			targets : ".wrap-items .item-view",
			opacity : [0, 0.8],
			easing : "easeInQuad",
			duration : 500,
			delay : function (el, i){
				var length = $(".wrap-items .item-view").length;
				var gap = 500 / length;
				return gap * i;
			}
		});
})
</script>
</head>
<body>
<div class="wrapper">
	<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
	
	<div class="main">
		<div class="items-title">
			Projects
		</div>
		
		<div class="wrap-items">
			<c:forEach var="item" items="${items}">
				<div onclick="itemView(${item.seq})" class="item-view">
					<div class="item-snapsht" style="background-image: url('${pageContext.request.contextPath}${item.snapsht}')">
						<div class="item-info display-none">
						<h3>${item.name}</h3>
						<a class="item-desc">${item.desc}</a>
						</div>
					</div>
				</div>
			</c:forEach>
			
			<script>
			(function(){
				function itemResize(){
					$(".wrap-items > .item-view").each(function(){
						var width = parseInt($(this).width());
						$(this).css("height", width *(2/3));
					});
				}
				itemResize();
				
				$(window).resize(function(){
					itemResize();	
				})
				
			})();
			</script>
		</div>
	</div>
	
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
</div>
</body>
</html>


