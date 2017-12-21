<style>
.footer {
	width : 100%;
	margin-top : 3rem;
	padding : 1rem 0 1rem 0;
	background : #333;
	color : #FFF;
}

.footer-head{
	letter-spacing: 0.1rem;
}

.info-contact {
	text-align: center;
	font-size: 0.5rem;
}

.info-contact div {
	margin : 0.2rem 0;
}
</style>

<div class="footer">
	<div class="info-contact">
		<div class="footer-head"><h4>CONTACT INFOMATION</h4></div>
		<div class="row-center">
			<img src="${pageContext.request.contextPath}/resources/image/icon_footer_email.png" style="width:0.6rem; height:0.6rem; margin-right: 0.3rem">
			cglee079@gmail.com
		</div>
		<div class="row-center">
			<img src="${pageContext.request.contextPath}/resources/image/icon_footer_phone.png" style="width:0.6rem; height:0.6rem; margin-right: 0.3rem">
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