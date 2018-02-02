<%@ page pageEncoding="UTF-8"%>
<div class="footer col-center">
	<div class="info-contact font-lora">
		<h4>CHANGOO'S</h4>
		<a target="_black" href="https://github.com/cglee079">Git</a>  
		/ 
		<a target="_blank" href="https://www.instagram.com/cglee079">Instagram</a>
		<br/>
		Tel : 010-2062-2979 / Email : cglee079@gmail.com<br/>
		Copyrightⓒ2018 By Changoo Lee, All rights Reserved.
	</div>
</div>
<script>
function footerRelocate(){
	var footer = $(".footer");
	var top = undefined;
	footer.css("margin-top", "3rem");
	top = footer.offset().top;
	if(top < deviceHeight){
		var marginTop = parseInt(footer.css("margin-top"));
		$(".footer").css("margin-top", marginTop + (deviceHeight - top));
	}
}

$(document).ready(function(){
	footerRelocate();
	$(window).resize(function(){
		footerRelocate();
	})
})


</script>