function footerRelocate() {
	var footer = $(".footer");
	var top = undefined;
	footer.css("margin-top", "3rem");
	top = footer.offset().top;
	if (top < deviceHeight) {
		var marginTop = parseInt(footer.css("margin-top"));
		$(".footer").css("margin-top", marginTop + (deviceHeight - top));
	}
}

$(window).load(function() {
	footerRelocate();
})

$(window).resize(function() {
	footerRelocate();
})