<style>
.footer {
	width : 100%;
	margin-top : 3rem;
	padding : 1rem 0 1rem 0;
	background : #333;
	color : #FFF;
}

.info-contact {
	text-align: center;
	font-size: 0.5rem;
}
</style>

<div class="footer">
	<div class="info-contact">
		<h4>CONTACT INFOMATION</h4>
		<p>Email : cglee079@gmail.com</p>
		<p>Tel	 : 010 - 2062 - 2979</p>
	</div>
</div>
<script>
function footerRelocate(){
	var footer = $(".footer");
	var offset = footer.offset();
	var top = offset.top;
	
	if(top < deviceHeight){
		var marginTop = parseInt(footer.css("margin-top"));
		$(".footer").css("margin-top", marginTop + (deviceHeight - top));
	}
	
}

footerRelocate();
$(window).resize(function(){
	footerRelocate();
})
</script>