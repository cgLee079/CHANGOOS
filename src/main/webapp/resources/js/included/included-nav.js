/* Progress bar */
var Progress = {
	start 	: function() { $(".progress-bar").removeClass("off"); },
	stop 	: function() { $(".progress-bar").addClass("off"); }
}


//Menu Enum
var menu ={
	MGNT_PROJECT: ".menu-mgnt-project",
	MGNT_STUDY 	: ".menu-mgnt-study",
	MGNT_BLOG 	: ".menu-mgnt-blog",
	MGNT_PHOTO 	: ".menu-mgnt-photo",
	PROJECT 	: ".menu-project",
	STUDY 		: ".menu-study",
	BLOG 		: ".menu-blog",
	PHOTO 		: ".menu-photo",
}

$(document).ready(function(){
	if(!isMobile){ //PC환경이라면
		var globalSearch = $(".web-menu.menu-search-field .global-search");
		globalSearch[0].comboboxByCG({
			inputFocusClass : "on",
			listClass : "facet-panel",
			listItemClass : "facet-value",
			listItemFocusClass : "on",
			dataURL : getContextPath() + "/search/facets",
			panelMinHeight: 100,
			panelMaxHeight: 200,
			onEnter : function globalSearch(value){
				window.location.href = getContextPath() + "/search?value=" + encodeURIComponent(value);
			},
			maxValues : 5
		});
	}
	
	else{ // Mobile 환경이라면
		var globalSearch = $(".mob-menu .mobnav-search-field .global-search");
		globalSearch[0].comboboxByCG({
			inputFocusClass : "on",
			listClass : "facet-panel",
			listItemClass : "facet-value",
			listItemFocusClass : "on",
			dataURL : getContextPath() + "/search/facets",
			panelMinHeight: 100,
			panelMaxHeight: 200,
			onEnter : function globalSearch(value){
				window.location.href = getContextPath() + "/search?value=" + encodeURIComponent(value);
			},
			maxValues : 5
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
	var globalSearch = $(tg).parent().siblings("*").find(".global-search")
	if(!globalSearch.hasClass("on")){
		globalSearch.focus();	
	}
}

/* MOBILE NAV ICON 애니메이션 */
function drawMobileMenu(tg) {
	var tg = $(tg);
	
	var bgMobileMenus = $(".bg-mob-menus");
	var mobileMenus = $(".mob-menus");
	
	bgMobileMenus.addClass("on");
	mobileMenus.addClass("on");
	$("body").css("overflow", "hidden");
	$("body").css("touch-action", "none");
	$("body").on("scroll mousewheel", function(event) {
		event.preventDefault();
		event.stopPropagation();
		return false;
	});
	
	
	$("body").on("click", function(e){ //문서 body를 클릭했을때
		if($(e.target).hasClass("bg-mob-menus")){
			bgMobileMenus.removeClass("on");
			mobileMenus.removeClass("on");
			$("body").off("scroll mousewheel");
			$("body").off("click");
			$("body").css("overflow", "unset");	
			$("body").css("touch-action", "unset");
		}
	});
	
  	return false;
}

