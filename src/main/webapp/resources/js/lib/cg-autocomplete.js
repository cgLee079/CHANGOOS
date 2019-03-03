HTMLInputElement.prototype.comboboxByCG = function(param){
	const minGetFacetsTime = 200;
	const onEnter = param.onEnter;
	const maxValues = param.maxValues;
	const inputFocusClass = param.inputFocusClass;
	const listClass = param.listClass;
	const listItemClass = param.listItemClass;
	const listItemFocusClass = param.listItemFocusClass;
	const ts = $(this);
	const width = ts.width();
	const height = ts.height();
	const dataURL = param.dataURL;
	let getFacetsTimer;
	let lastGetFacetsTime;
	
	const wrapSearch = $("<div>");
	wrapSearch.addClass("wrap-search");
	
	const searchField = $("<input>");
	searchField.appendTo(wrapSearch);
	searchField.attr("type", "text");
	searchField.attr("placeholder", ts.attr("placeholder"));
	searchField.attr("style", ts.attr("style"));
	searchField.val(ts.val());
	searchField.addClass(ts.attr("class"));
	searchField.addClass("search-field");

	const facetPanel= $("<div>");
	facetPanel.appendTo($("body"));
	facetPanel.addClass(listClass);
	facetPanel.css("min-height", param.panelMinHeight);
	facetPanel.css("max-height", param.panelMaxHeight);
	facetPanel.css("overflow-x", "hidden");
	facetPanel.css("border", "0.5px solid #DDD");
	facetPanel.css("position", "fixed");
	facetPanel.css("background", "#FFF");
	facetPanel.css("display", "none");
	
	ts.css("display", "none");
	ts.after(wrapSearch);
	
	searchField.bind("focus", function(event){
		$(this).addClass(inputFocusClass);
	});
	
	searchField.bind("focusout", function(event){
		const el = $(this);
		setTimeout(function() { 
			el.removeClass(inputFocusClass);
			facetPanel.css("display", "none"); 
		}, 100);
	});
	
	searchField.bind("keyup", function(event){
		const el = $(this);
		
		switch(event.keyCode){
			case 13 : //Enter
				const focused = facetPanel.find("." + listItemClass + "." + listItemFocusClass);
				if(focused.length){
					focused.removeClass(listItemFocusClass);
					searchField.val(focused.text());
					facetPanel.css("display", "none");
				}
				onEnter(el.val());	
				
				break;
			case 38 : //Arrow Up
				const facetValues = facetPanel.find("." + listItemClass);
				const focused = facetPanel.find(".facet-value." + listItemFocusClass);
				const index = focused.index();
				
				facetValues.removeClass(listItemFocusClass);
				
				if(index == 0) {
				} else{
					const focused = facetValues.eq(index - 1);
					focused.addClass(listItemFocusClass);
				}
				
				break;
			case 40 : //Arrow Down
				const facetValues = facetPanel.find("." + listItemClass);
				const length = facetValues.length;
				const focused = facetPanel.find("." + listItemClass + "." + listItemFocusClass);
				const index = focused.index();
				
				facetValues.removeClass(listItemFocusClass);
				
				if(index == -1) { // focused 된게 없다면.
					facetValues.eq(0).addClass(listItemFocusClass);
				} if(index == length -1){ //마지막이 focuse라면
				} else{
					const focused = facetValues.eq(index + 1);
					focused.addClass(listItemFocusClass);
				}
			 	break;
			default : // 그 외 입력값
				if (!getFacetsTimer) { //
					const now = new Date().getTime();
					
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
					if(!el.val()){
						facetPanel.empty();
						facetPanel.css("display", "none");
						return;
					}
					
					$.ajax({
						type	: "GET",
						url		: dataURL +"?value=" + encodeURIComponent(el.val()),
						dataType: 'JSON',
						success : function(result) {
							facetPanel.empty();
							facetPanel.css("width", el.width());
							facetPanel.css("left", el.offset().left);
							facetPanel.css("top", el[0].offsetTop + el.height());
							facetPanel.css("display", "initial");
							
							const length = result.length > maxValues ? maxValues : result.length;
							for(let i = 0; i < length; i++){
								const facetValue = $("<div>");
								
								facetValue.text(result[i]);
								facetValue.css("word-break", "keep-all");
								facetValue.css("white-space", "nowrap");
								facetValue.css("min-height", "1rem");
								facetValue.addClass(listItemClass);
								facetValue.bind("click", function(){
									const facetPanel = $(this).parent();
									const searchField = facetPanel.siblings(".search-field");
									
									searchField.val($(this).text());
									onEnter($(this).text());
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