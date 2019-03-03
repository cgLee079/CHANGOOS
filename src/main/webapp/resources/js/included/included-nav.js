/* Progress bar */
const Progress = {
	start() { $(".progress-bar").removeClass("off"); },
	stop() { $(".progress-bar").addClass("off"); }
}


//Menu Enum
const menu = Object.freeze({
	MGNT_PROJECT: ".menu-mgnt-project",
	MGNT_STUDY 	: ".menu-mgnt-study",
	MGNT_BLOG 	: ".menu-mgnt-blog",
	MGNT_PHOTO 	: ".menu-mgnt-photo",
	PROJECT 	: ".menu-project",
	STUDY 		: ".menu-study",
	BLOG 		: ".menu-blog",
	PHOTO 		: ".menu-photo",
});

$(document).ready(() => {
	if(!isMobile){ //PC환경이라면
		const globalSearch = $(".web-menu.menu-search-field .global-search");
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
		const globalSearch = $(".mob-menu .mobnav-search-field .global-search");
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
const doMenuOn = function(menu){
	const menus = $(".web-menus");
	menus.find(".web-menu").removeClass("on");
	menus.find(menu).addClass("on");
}

/* 스크롤 시 TOP 버튼 보여주기 */
$(window).scroll((event) => {
	const scroll = $(window).scrollTop();
	if (scroll > 100) { $(".btn-scroll-top").removeClass("off"); } 
	else { $(".btn-scroll-top").addClass("off"); }
});

/* TOP 버튼 클릭 시 이벤트 */
const scrollToTop = function() {
	$("html, body").animate({ scrollTop : 0 });
}

/* 검색 버튼 누를 시 이벤트 */
const focuseSearch = function(tg){
	var globalSearch = $(tg).parent().siblings("*").find(".global-search")
	if(!globalSearch.hasClass("on")){
		globalSearch.focus();	
	}
}

/* MOBILE NAV ICON 애니메이션 */
const drawMobileMenu = function(target) {
	const tg = $(target);
	const bgMobileMenus = $(".bg-mob-menus");
	const mobileMenus = $(".mob-menus");
	
	bgMobileMenus.addClass("on");
	mobileMenus.addClass("on");
	$("body").css("overflow", "hidden");
	$("body").css("touch-action", "none");
	$("body").on("scroll mousewheel", function(event) {
		event.preventDefault();
		event.stopPropagation();
		return false;
	});
	
	
	$("body").on("click", (e) => { //문서 body를 클릭했을때
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

