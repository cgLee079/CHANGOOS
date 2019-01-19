$(document).ready(function(){
	if(!isMobile){ //PC환경이라면
		var globalSearch = $(".web-menu.menu-search-field .global-search");
		globalSearch[0].comboboxByCG({
			inputFocusClass : "on",
			listItemClass : "facet-value",
			listItemFocusClass : "on",
			dataURL : getContextPath() + "/search/facets",
			panelMinHeight: 100,
			panelMaxHeight: 200,
			onEnter : function globalSearch(value){
				window.location.href = getContextPath() + "/search?value=" + value;
			},
			maxValues : 5
		});
	}
	
	else{ // Mobile 환경이라면
		var globalSearch = $(".mob-nav .menu-search-field .global-search");
		globalSearch[0].comboboxByCG({
			inputFocusClass : "on",
			listItemClass : "facet-value",
			listItemFocusClass : "on",
			dataURL : getContextPath() + "/search/facets",
			panelMinHeight: 100,
			panelMaxHeight: 200,
			onEnter : function globalSearch(value){
				window.location.href = getContextPath() + "/search?value=" + value;
			},
			maxValues : 5
		});
			
		/* MOBILE NAV ICON 애니메이션 */
		$(".mobnav-icon").click(function() {
			$(this).toggleClass("open");

			if ($(this).hasClass("open")) {
				$(".mob-menus").removeClass("unvalid");
				$("html, body").css("overflow", "hidden");
				$("html, body").on("scroll touchmove mousewheel", function(event) {
					event.preventDefault();
					event.stopPropagation();
					return false;
				});

				var ani = anime.timeline({
					loop : false
				}).add({
					targets : ".bg-mobmenu",
					easing: "easeInCubic",
					duration : 400,
					scale: [0.2, 3],
					opacity: [0.2,1],
				}).add({
					targets : ".mob-menus",
					opacity : [ 0, 1 ],
					duration : 1,
					easing : "easeOutExpo"
				}).add({
					targets : ".ml1 .letter",
					scale : [ 0.3, 1 ],
					opacity : [ 0, 1 ],
					translateZ : 0,
					easing : "easeOutExpo",
					duration : 500,
					delay : function(el, i) {
						return 50 * (i)
					}
				}).add({
					targets : ".mob-menus .mob-menu",
					scale: [ 0, 1 ],
					easing : "easeOutExpo",
					duration : 500,
					delay : function(el, i) {
						return 50 * (i);
					}
				})
			} else {
				var ani = anime.timeline({
					loop : false
				}).add({
					targets : ".mob-menus",
					opacity : [ 1, 0 ],
					duration : 100,
					easing : "easeOutExpo"
				}).add({
					targets : ".bg-mobmenu",
					easing: "easeInCubic",
					duration : 500,
					scale: [3,0],
					opacity: [1,0],
				})

				$(".mob-menus").addClass("unvalid");
				$("html, body").off("scroll touchmove mousewheel");
				$("html, body").css("overflow", "");
			}
		});
	}

});

/* PC 환경에서 메뉴 버튼 클릭시 이벤트 */
function doMenuOn(menu){
	var menus = $(".web-menus");
	menus.find(".web-menu").removeClass("on");
	menus.find(menu).addClass("on");
}

/* 스크롤 시 TOP 버튼 보여주기 */
$(window).scroll(function(event) {
	var scroll = $(window).scrollTop();
	if (scroll > 100) { $(".btn-scroll-top").removeClass("off"); } 
	else { $(".btn-scroll-top").addClass("off"); }
});

/* TOP 버튼 클릭 시 이벤트 */
function scrollToTop() {
	$("html, body").animate({ scrollTop : 0 });
}

/* 검색 버튼 누를 시 이벤트 */
function focuseSearch(tg){
	var globalSearch = $(tg).parent().siblings(".menu-search-field").find(".global-search")
	if(!globalSearch.hasClass("on")){
		globalSearch.focus();	
	}
}

/* Progress bar */
var Progress = {
	start 	: function() { $(".progress-bar").removeClass("off"); },
	stop 	: function() { $(".progress-bar").addClass("off"); }
}