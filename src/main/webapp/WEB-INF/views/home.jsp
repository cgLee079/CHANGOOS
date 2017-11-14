<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<html>
<head>
	<title>Home</title>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/animejs/2.0.2/anime.min.js"></script>

<style>
*{
	margin	: 0px;
	padding	: 0px;
}

.main{
	width: 80%;
	text-align: center;
	margin: 0px auto;
}

.main-introduce{
	max-width: 700px;
	margin : 0px auto;
	margin-top: 200px; 
} 

.main-items{
	margin-top: 200px;
	display: flex;
	flex-flow: row wrap;
} 

.main-items .item{
	flex : 1 45%;
	background: rgba(100, 100, 100, 0.1);
	height: 300px;
	margin: 1px;
}

.ml9 {
  position: relative;
  font-weight: 200;
  font-size: 4em;
}

.ml9 .text-wrapper {
  position: relative;
  display: inline-block;
  padding-top: 0.2em;
  padding-right: 0.05em;
  padding-bottom: 0.1em;
  overflow: hidden;
}

.ml9 .letter {
  transform-origin: 50% 100%;
  display: inline-block;
  line-height: 1em;
}
</style>

<script>
</script>
</head>
<body>
<div class="wrapper">
	<div class="main">
		<div class="main-introduce">
			<h1 class="ml9">
			  <span class="text-wrapper">
			    <span class="letters">Changoo Lee</span>
			  </span>
			</h1>
			<script>
			(function(){
				$('.ml9 .letters').each(function(){
					  $(this).html($(this).text().replace(/([^\x00-\x80]|\w)/g, "<span class='letter'>$&</span>"));
				});
				
				anime.timeline({loop: true})
				  .add({
				    targets: '.ml9 .letter',
				    scale: [0, 1],
				    duration: 1500,
				    elasticity: 600,
				    delay: function(el, i) {
				      return 45 * (i+1)
				    }
				  }).add({
				    targets: '.ml9',
				    opacity: 0,
				    duration: 1000,
				    easing: "easeOutExpo",
				    delay: 1000
				  });
			})();
			</script>
			<a>
				Hello! Thank you for visiting my photofolio site.<br>
				My name is changoo.<br>
				I specialized computer engineering in Hansung Univ.<br>
				and interested in Android development.<br>
			</a>
		</div>
		
		<div class="main-items">
			<div class="item"></div>
			<div class="item"></div>
			<div class="item"></div>
			<div class="item"></div>
			<div class="item"></div>
			<div class="item"></div>
			<div class="item"></div>
		</div>
		
	</div>
</div>
</body>
</html>
