
/* check, is mobile?*/
let isMobile = false;
let deviceWidth = undefined;
let deviceHeight = undefined;

const checkDevice = function(){
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


/* Check Browser, ES5 */
window.onload = function(){
	var broswer = navigator.userAgent.toLowerCase();
	if((navigator.appName = 'Netscape' && broswer.indexOf('trident') != -1) || (broswer.indexOf('msie') != -1)){
		alert("본 사이트는 크롬 브라우저에 최적화 되어있습니다.");
	}
}

/* Download File */
const downloadFile = function(dir, pathname, filename){
	const href = getContextPath() 
		+ "/board/file?"
		+ "dir=" + dir
		+ "&pathname=" + pathname
		+ "&filename=" + filename;
	window.location.href = href;
}


const encodeURIParam = function(data) {
	const keys = Object.keys(data);
	let count = 0;
	let param = "";
	for(let i = 0; i < keys.length; i++){
		const key = keys[i];
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


const br2nl = function(text){
	return text.replace(/(<br\s*\/?>)+/g, "\n");
}

const nl2br = function(text){
	return text.replace(/\n/g, "<br />");
}

