
/* check, is mobile?*/
var isMobile = false;
var deviceWidth = undefined;
var deviceHeight = undefined;

function checkDevice(){
	deviceWidth 	= Math.min(window.innerWidth || Infinity, screen.width);
	deviceHeight	= Math.min(window.innerHeight || Infinity, screen.height);
	if(deviceWidth <= 800){
		isMobile = true;
	}
	
}

checkDevice();
$(window).resize(function(){
	checkDevice();
})

/* Download File */
function downloadFile(dir, pathname, filename){
	var href = getContextPath() 
		+ "/board/file?"
		+ "dir=" + dir
		+ "&pathname=" + pathname
		+ "&filename=" + filename;
	window.location.href = href;
}


function encodeURIParam(data) {
	var count = 0;
	var param = "";
	var keys = Object.keys(data);
	for(var i = 0; i < keys.length; i++){
		var key = keys[i];
		if((data[key] || data[key] === 0) && data[key] != undefined && data[key].length != 0){
			count++;
			if(count == 1){ param += "?"}
			else{ param += "&"}
			
			var value = data[key];
			if(Array.isArray(data[key])){
				value = data[key][0];
				for(var j = 1; j < data[key].length; j++){
					value += "," + data[key][j].trim();
				}
			}
			param += key + "=" + value;
		}
	}
	
	return encodeURI(param);
}  


//function disableTouchmove(){
//	var preventDefault = function(e){
//	    e.preventDefault();
//	};
//	var touchstart = function(e) {
//	    document.addEventListener('touchstart', preventDefault,false);
//	    document.addEventListener('touchmove',  preventDefault,false);
//	    /*do other stuff*/
//	};
//	var touchend = function(e) {
//	    document.removeEventListener('touchstart', preventDefault,false);
//	    document.removeEventListener('touchmove',  preventDefault,false);
//	};
//
//	document.addEventListener('touchstart',  touchstart, false);
//	document.addEventListener('touchend',    touchend,   false);
//}

