<div class="footer">
	<div class="info-contact">
		<div class="footer-head"><h4>CONTACT INFOMATION</h4></div>
		<div class="row-center">
			cglee079@gmail.com
		</div>
		<div class="row-center">
			010 - 2062 - 2979
		</div>
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