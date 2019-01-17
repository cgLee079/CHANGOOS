<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- Header -->
<div class="header-occupy"></div>
<div class="wrap-header">
	<div class="header">
		<div class="wrap-home-logo col-center">
			<img src="${pageContext.request.contextPath}/resources/image/icon-logo.svg"  class="logo" onclick="window.location.href = '${pageContext.request.contextPath}/'" ></img>
		</div>
		<div class="web-menus">
			<sec:authorize access="hasRole('ROLE_ADMIN')">
		  		<div class="btn-web-menu menu-logout"><a class="btn" href="${pageContext.request.contextPath}/j_spring_security_logout">로그아웃 </a></div>
		  		<div class="btn-web-menu menu-mgnt-project"><a class="btn" href="${pageContext.request.contextPath}/mgnt/projects">프로젝트관리 </a></div>
		  		<div class="btn-web-menu menu-mgnt-study"><a class="btn" href="${pageContext.request.contextPath}/mgnt/studies">스터디관리 </a></div>
		  		<div class="btn-web-menu menu-mgnt-blog"><a class="btn" href="${pageContext.request.contextPath}/mgnt/blogs">블로그관리 </a></div>
		  		<div class="btn-web-menu menu-mgnt-photo"><a class="btn" href="${pageContext.request.contextPath}/mgnt/photos">사진관리 </a></div>
			</sec:authorize>
		
			<div class="btn-web-menu menu-about"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/introduce">이력</a></div>
			<div class="btn-web-menu menu-project"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/projects">프로젝트</a></div>
			<div class="btn-web-menu menu-study"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/studies">스터디</a></div>
			<div class="btn-web-menu menu-blog"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/blogs">블로그</a></div>
			<div class="btn-web-menu menu-photo"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/photos">사진</a></div>
			
			<div class="btn-web-menu menu-search">
				<a onclick="a(this)" href="javascript:void(0);">검색</a>
				<input type="text" id="globalSearch" class="global-search">
			</div>
		</div>
		
		<style>
			.facet-value{
				padding: 0.3rem 0.2rem;
				cursor: pointer;
			}
			
			.facet-value.on, .facet-value:hover{
				background: #EEE;
			}
			
		</style>
		<script>
			function a(tg){
				$(".global-search").focus();
			}
			
			HTMLInputElement.prototype.comboboxByCG = function(param){
				var getFacetsTimer;
				var lastGetFacetsTime;
				var minGetFacetsTime = 200;
				var onEnter = param.onEnter;
				var maxValues = param.maxValues;
				var ts = $(this);
				var width = ts.width();
				var height = ts.height();
				
				var wrapSearch = $("<div>");
				wrapSearch.attr("id","wrapSearch");
				wrapSearch.addClass("wrap-search");
				wrapSearch.css("position", "relative");
				
				var searchField = $("<input>");
				searchField.appendTo(wrapSearch);
				searchField.attr("id", "searchField");
				searchField.attr("type", "text");
				searchField.attr("placeholder", ts.attr("placeholder"));
				searchField.attr("style", ts.attr("style"));
				searchField.val(ts.val());
				searchField.addClass(ts.attr("class"));
				searchField.addClass("search-field");
			
				var facetPanel= $("<div>");
				facetPanel.appendTo(wrapSearch);
				facetPanel.attr("id", "facetPanel");
				facetPanel.addClass("facet-panel");
				facetPanel.css("min-height", param.panelMinHeight);
				facetPanel.css("max-height", param.panelMaxHeight);
				facetPanel.css("overflow-y", "scroll");
				facetPanel.css("overflow-x", "hidden");
				facetPanel.css("border", "0.5px solid #DDD");
				facetPanel.css("position", "absolute");
				facetPanel.css("background", "#FFF");
				facetPanel.css("display", "none");
				
				ts.css("display", "none");
				ts.after(wrapSearch);
				
				searchField.bind("focusout", function(event){
					var el = $(this);
					setTimeout(function() { 
						var facetPanel = el.siblings("#facetPanel");
						facetPanel.css("display", "none"); 
					}, 100);
				});
				
				searchField.bind("keyup", function(event){
					var el = $(this);
					
					switch(event.keyCode){
						case 13 : //Enter
							var focused = facetPanel.find(".facet-value.on");
							if(focused.length){
								focused.removeClass("on");
								searchField.val(focused.text());
								facetPanel.css("display", "none");
							}
							onEnter(el.val());	
							
							break;
						case 38 : //Arrow Up
							var facetValues = facetPanel.find(".facet-value");
							var focused = facetPanel.find(".facet-value.on");
							var index = focused.index();
							
							facetValues.removeClass("on");
							
							if(index == 0) {
							} else{
								var focused = facetValues.eq(index - 1);
								focused.addClass("on");
							}
							
							break;
						case 40 : //Arrow Down
							var facetValues = facetPanel.find(".facet-value");
							var length = facetValues.length;
							var focused = facetPanel.find(".facet-value.on");
							var index = focused.index();
							
							facetValues.removeClass("on");
							
							if(index == -1) { // focused 된게 없다면.
								facetValues.eq(0).addClass("on");
							} if(index == length -1){ //마지막이 focuse라면
							} else{
								var focused = facetValues.eq(index + 1);
								focused.addClass("on");
							}
						 	break;
						default : 
							if(!el.val()){
								facetPanel.empty();
								facetPanel.css("display", "none");
								return;
							}
						
							if (!getFacetsTimer) {
								var now = new Date().getTime();
								
								if(now - lastGetFacetsTime > minGetFacetsTime){
									getFacets();
									lastGetFacetsTime = now;
								}
								
								getFacetsTimer = setTimeout(function(){
									getFacetsTimer = null;
									lastGetFacetsTime = new Date().getTime();
									getFacets();
								}, minGetFacetsTime);
							}
							
							function getFacets(){
								$.ajax({
									type	: "GET",
									url		: getContextPath() + "/search/facets?value=" + el.val(),
									dataType: 'JSON',
									success : function(result) {
										var facetPanel = el.siblings("#facetPanel");
										
										facetPanel.empty();
										facetPanel.css("width", el.width());
										facetPanel.css("left", el.position().left);
										facetPanel.css("top", el.position().top + el.height());
										facetPanel.css("display", "initial");
										
										var length = result.length > maxValues ? maxValues : result.length;
										for(var i = 0; i < length; i++){
											var facetValue = $("<div>");
											
											facetValue.text(result[i]);
											facetValue.addClass("facet-value");
											facetValue.bind("click", function(){
												var facetPanel = $(this).parent();
												var searchField = facetPanel.siblings("#searchField");
												
												searchField.val($(this).text());
												onEnter($(this).text());
												//searchField.trigger(jQuery.Event( 'keyup', { which: 13 } ));
												facetPanel.css("display", "none");
											})
											
											facetValue.appendTo(facetPanel);
										}
									},
									complete: function(){
									},
									error : function(e) {
										console.log(e);
									}
								});
							}
						break;
					}
				});
				
			}
			
			$(document).ready(function(){
				var globalSearch = $("#globalSearch");
				globalSearch[0].comboboxByCG({
					panelMinHeight: 100,
					panelMaxHeight: 200,
					onEnter : function globalSearch(value){
						window.location.href = getContextPath() + "/search?value=" + value;
					},
					maxValues : 5
				});
			});
		
		</script>
		
	</div>
</div>

<!-- Navigation -->
<div class="wrap-nav-icon">
	<div class="nav-icon">
		<span></span>
		<span></span>
		<span></span>
		<span></span>
	</div>
</div>
	
<div class="nav-background-wrapper">
	<div class="nav-background"></div>
</div>
<div class="nav-menu unvalid col-center">
	<div class="btn-slideup"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/introduce">이력</a></div>
	<div class="btn-slideup"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/projects">프로젝트</a></div>
	<div class="btn-slideup"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/studies">스터디</a></div>
	<div class="btn-slideup"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/blogs">블로그</a></div>
	<div class="btn-slideup"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/photos">사진</a></div>
	
	<sec:authorize access="hasRole('ROLE_ADMIN')">
 		<div class="btn-slideup"><a href="${pageContext.request.contextPath}/mgnt/projects">프로젝트관리 </a></div>
 		<div class="btn-slideup"><a href="${pageContext.request.contextPath}/mgnt/studies">스터디관리 </a></div>
 		<div class="btn-slideup"><a href="${pageContext.request.contextPath}/mgnt/blogs">블로그관리 </a></div>
 		<div class="btn-slideup"><a href="${pageContext.request.contextPath}/mgnt/photos">사진관리 </a></div>
 		<div class="btn-slideup"><a href="${pageContext.request.contextPath}/j_spring_security_logout">로그아웃 </a></div>
	</sec:authorize>
</div>

<!--  Top btn. -->
<div class="btn-scroll-top col-center off">
	<a>TOP</a>
</div>

<!-- Progress-bar. -->
<div class="progress-bar off">
	<div class="spinner">
		<div class="double-bounce1"></div>
		<div class="double-bounce2"></div>
	</div>
</div>


<!--  main 	-->
