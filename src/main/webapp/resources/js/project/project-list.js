$(document).ready(() => {
	doMenuOn(menu.PROJECT);
});

/* when project click */
const projectView = function(seq){
	Progress.start();
	window.location.href = getContextPath() + "/projects/" + seq;
}
