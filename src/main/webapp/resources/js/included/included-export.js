function drawExportView(){
	$(".bg-wrap-export").addClass("on");
	$(".wrap-export").addClass("on");
	$("body").css("overflow", "hidden");
	$("body").on("scroll touchmove mousewheel", function(event) {
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

function exitExportView(){
	$(".bg-wrap-export").removeClass("on");
	$(".wrap-export").removeClass("on");
	$("body").off("scroll touchmove mousewheel");
	$("body").off("click");
	$("body").css("overflow", "");
}

function urlClipCopy() { 
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

function initExportKakao(data){
	var url = window.location.href;
	var thumbnailURL = window.location.origin + data.thumbnail;
	
	Kakao.init('3684e89896f38ed809137a3e7062bf95');
	
	// 카카오링크 버튼을 생성
	Kakao.Link.createDefaultButton({
		container: '#expKakaotalk',
		objectType: 'feed',
  		content: {
    		title: data.title,
    		imageUrl: thumbnailURL,
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
	   			title: '웹으로 보기',
	   			link: {
					mobileWebUrl: url,
					webUrl: url
				}
			},
			{
	   			title: "CHANGOO'S",
	   			link: {
	   				androidExecParams : url,
				}
			}
  		]
	});
}