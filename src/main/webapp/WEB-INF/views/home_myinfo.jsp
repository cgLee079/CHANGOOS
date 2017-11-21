<style>
.ml6 {
	color: #000;
	position: relative;
	font-weight: 900;
	font-size: 3.3em;
}

.ml6 .text-wrapper {
	position: relative;
	display: inline-block;
	padding-top: 0.2em;
	padding-right: 0.05em;
	padding-bottom: 0.1em;
	overflow: hidden;
}

.ml6 .letter {
	display: inline-block;
	line-height: 1em;
}
</style>

<style>
.wrap-myinfo {
	background: rgba(255, 255, 255, 1);
	position : fixed;
	top : 0;
	left : 0;
	bottom : 0;
	right : 0;
}

.slider {
	overflow-y: hidden;
	bottom: 0; /* approximate max height */
	transition-property: all;
	transition-duration: .5s;
	transition-timing-function: cubic-bezier(0, 1, 0.5, 1);
}

.slider.closed {
	bottom: 100%;
}

.myinfo-views{
	position : relative;
	margin-top: 5%;
	width: 100%;
	height: 500px;
}

.content-picture{
	flex : 1 50%;
	background-position	: center;
    background-repeat	: no-repeat;
    background-size		: cover;
}

.content-text{
	flex : 1 40%;
	padding : 30px;
}

.myinfo-view {
	display : flex;
	flex-flow: row wrap;
	position: absolute;
	width : 100%;
	height: 100%;
}

.myinfo-view00 {left: 0%; background: #FFF;}
.myinfo-view01 {left: 100%; background: #333;}
.myinfo-view02 {left: 100%; background: #666;}
.myinfo-view03 {left: 100%; background: #999;}

.btns-view{
	width : 100%;
	text-align: center;
	margin-top : 30px;
}

.btn-view{
	display : inline-block;
	width : 20px;
	height: 20px;
	border-radius : 10px;
	background : #888;
	margin : 20px 10px 0px 10px;
	cursor : pointer;
}

.btn-view.on{
	background : #000;
}

@media (max-width: 800px){
	.myinfo-views{
		margin-top: 20%;
		height: 1000px;
	}
	
	.myinfo-view{
		flex-flow: column wrap;
	}
	
	.btns-view{
		margin-top : 100px;
	}
}
</style>
<script type="text/javascript">
	$(document).ready(function(){
		var wrap	= $(".myinfo-views");
		var views	= $(".myinfo-views > .myinfo-view");
		var btns 	= $(".btns-view > .btn-view");
		var current = 0 ;
		var setIntervalId;
		
		btns.on("click", function(){
			var tg = $(this);
			var index = tg.index();
			
			btns.removeClass("on");
			tg.addClass("on");
			
			move(index);
		});
		
		wrap.on("mouseover", function(){clearInterval(setIntervalId);});
		wrap.on("mouseout", function(){timer();});
	
		function move(index){
			var currentEl =  views.eq(current);
			var nextEl = views.eq(index);
			
			currentEl.css({left : "0"}).stop().animate({left : "-100%"});
			nextEl.css({left : "100%"}).stop().animate({left : "0%"});
			
			current = index;
		}
		
		timer();
		function timer(){
			setIntervalId = setInterval(function(){
				var n = current + 1;
				if(n === views.length){
					n = 0;
				}
				
				btns.eq(n).trigger("click");
			}, 5000);
		}
	});
</script>
</head>
<body>
	<div class="wrap-myinfo slider closed">
		<div class="myinfo-views">
			<div class="myinfo-view myinfo-view00">
				<div class="content-picture" style="background-image: url(${pageContext.request.contextPath}/resources/image/bg_sample1.jpg)"></div>
				<div class="content-text">
					<h1 class="ml6">
						<span class="text-wrapper">
						<span class="letters">Who am I?</span>
						</span>
					</h1>
					<script>
						(function(){
							$('.ml6 .letters').each(function(){
							  $(this).html($(this).text().replace(/([^\x00-\x80]|\w)/g, "<span class='letter'>$&</span>"));
							});
		
							anime.timeline({loop: true})
							  .add({
							    targets: '.ml6 .letter',
							    translateY: ["1.1em", 0],
							    translateZ: 0,
							    duration: 1000,
							    delay: function(el, i) {
							      return 50 * i;
							    }
							  }).add({
							    targets: '.ml6',
							    opacity: 0,
							    duration: 1000,
							    easing: "easeOutExpo",
							    delay: 1000
							  });
						})();
					</script>
				</div>
			</div>
			<div class="myinfo-view myinfo-view01"></div>
			<div class="myinfo-view myinfo-view02"></div>
			<div class="myinfo-view myinfo-view03"></div>
		</div>
		
		<div class="btns-view">
			<div class="btn-view btn-view00 on"></div>
			<div class="btn-view btn-view01"></div>
			<div class="btn-view btn-view02"></div>
			<div class="btn-view btn-view03"></div>
		</div>
	</div>
</body>
</html>
