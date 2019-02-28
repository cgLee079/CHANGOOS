HTMLInputElement.prototype.comboboxByCG = function(param){
	var getFacetsTimer;
	var lastGetFacetsTime;
	var minGetFacetsTime = 200;
	var onEnter = param.onEnter;
	var maxValues = param.maxValues;
	var inputFocusClass = param.inputFocusClass;
	var listClass = param.listClass;
	var listItemClass = param.listItemClass;
	var listItemFocusClass = param.listItemFocusClass;
	var ts = $(this);
	var width = ts.width();
	var height = ts.height();
	var dataURL = param.dataURL;
	
	var wrapSearch = $("<div>");
	wrapSearch.addClass("wrap-search");
	
	var searchField = $("<input>");
	searchField.appendTo(wrapSearch);
	searchField.attr("type", "text");
	searchField.attr("placeholder", ts.attr("placeholder"));
	searchField.attr("style", ts.attr("style"));
	searchField.val(ts.val());
	searchField.addClass(ts.attr("class"));
	searchField.addClass("search-field");

	var facetPanel= $("<div>");
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
		var el = $(this);
		setTimeout(function() { 
			el.removeClass(inputFocusClass);
			facetPanel.css("display", "none"); 
		}, 100);
	});
	
	searchField.bind("keyup", function(event){
		var el = $(this);
		
		switch(event.keyCode){
			case 13 : //Enter
				var focused = facetPanel.find("." + listItemClass + "." + listItemFocusClass);
				if(focused.length){
					focused.removeClass(listItemFocusClass);
					searchField.val(focused.text());
					facetPanel.css("display", "none");
				}
				onEnter(el.val());	
				
				break;
			case 38 : //Arrow Up
				var facetValues = facetPanel.find("." + listItemClass);
				var focused = facetPanel.find(".facet-value." + listItemFocusClass);
				var index = focused.index();
				
				facetValues.removeClass(listItemFocusClass);
				
				if(index == 0) {
				} else{
					var focused = facetValues.eq(index - 1);
					focused.addClass(listItemFocusClass);
				}
				
				break;
			case 40 : //Arrow Down
				var facetValues = facetPanel.find("." + listItemClass);
				var length = facetValues.length;
				var focused = facetPanel.find("." + listItemClass + "." + listItemFocusClass);
				var index = focused.index();
				
				facetValues.removeClass(listItemFocusClass);
				
				if(index == -1) { // focused 된게 없다면.
					facetValues.eq(0).addClass(listItemFocusClass);
				} if(index == length -1){ //마지막이 focuse라면
				} else{
					var focused = facetValues.eq(index + 1);
					focused.addClass(listItemFocusClass);
				}
			 	break;
			default : 
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
							
							var length = result.length > maxValues ? maxValues : result.length;
							for(var i = 0; i < length; i++){
								var facetValue = $("<div>");
								
								facetValue.text(result[i]);
								facetValue.css("word-break", "keep-all");
								facetValue.css("white-space", "nowrap");
								facetValue.css("min-height", "1rem");
								facetValue.addClass(listItemClass);
								facetValue.bind("click", function(){
									var facetPanel = $(this).parent();
									var searchField = facetPanel.siblings(".search-field");
									
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