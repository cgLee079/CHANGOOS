const drawExportView = function(){
	$(".bg-wrap-export").addClass("on");
	$(".wrap-export").addClass("on");
	$("body").css("overflow", "hidden");
	$("body").css("touch-action", "none");
	$("body").on("scroll mousewheel", function(event) {
		event.preventDefault();
		event.stopPropagation();
		return false;
	});
	
	$("body").on("click", function(e){ //문서 body를 클릭했을때
		if($(e.target).hasClass("wrap-export")){
			exitExportView();			
		}
	});
	
  	return false;
}

const exitExportView = function(){
	$(".bg-wrap-export").removeClass("on");
	$(".wrap-export").removeClass("on");
	$("body").off("scroll mousewheel");
	$("body").off("click");
	$("body").css("overflow", "unset");
	$("body").css("touch-action", "unset");
}

const urlClipCopy = function() { 
	var dummy = $('<input>');
	dummy.appendTo($("body"));
    dummy.val(window.location.href);
    
	dummy.select();
	document.execCommand('copy');
	
	dummy.remove();

	swal({
		text : "URL이 복사 되었습니다", 
		icon : "success",
	}).then(function() {
		exitExportView();
	});
}

const initExportKakao = function(data){
	const url = window.location.href;
	const thumbnailURL = window.location.origin + data.thumbnail;
	const path  = window.location.pathname;
	
	Kakao.init('3684e89896f38ed809137a3e7062bf95');
	
	// 카카오링크 버튼을 생성
	Kakao.Link.createDefaultButton({
		container: '#expKakaotalk',
		objectType: 'feed',
  		content: {
    		title: data.title,
    		imageUrl: thumbnailURL,
    		imageWidth : 300,
    		imageHeight : 200,
    		link: {
    			mobileWebUrl: url,
    			webUrl: url
    		}
  		},
  		social: {
 	        commentCount: parseInt(data.comtCnt),
 	        viewCount : parseInt(data.hits),
 	    },
  		buttons: [
			{
	   			title: '웹에서 보기',
	   			link: {
					mobileWebUrl: url,
					webUrl: url
				}
			},
			{
	   			title: "앱에서 보기",
	   			link: {
	   				androidExecParams : "path=" + path,
				}
			}
  		]
	});
}