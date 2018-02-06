<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
var currentView = 0;
var tops 	= [];
var pass 	= [];

var projectViews;

function projectView(seq){
	Progress.start();
	window.location.href = getContextPath() + "/project/view?seq=" + seq;		
}

$(document).ready(function(){
	projectViews = $(".project-view");
	projectViews.each(function(){
		tops.push($(this).offset().top);
		pass.push(false);
	});
	
	if(isMobile){
		projectViews.each(function(){
			$(this).bind("click", function(){
				var seq = $(this).find("#project-seq").val();
				console.log(seq);
				projectView(seq);
			});
		});	
	}
	
	$(window).scroll(function(){
		var scrollTop = $(window).scrollTop();
		if(tops[currentView] <= (scrollTop + 550) && pass[currentView] == false){
			pass[currentView] = true;
			var target = document.querySelector(".project-view:nth-child(" + (currentView + 1)+ ")");
			anime.timeline()
				.add({
					targets: target,
					opacity : [0, 1],
					duration: 650,
					easing : "easeInQuad"
				});
			currentView += 1;
		}
		
	});
	
	$(window).trigger("scroll");
});

</script>
<div class="project-list">
	<c:forEach var="project" items="${projects}">
		<div onclick="" class="project-view">
			<div onclick="projectView(${project.seq})" class="project-snapsht" style="background-image: url('${pageContext.request.contextPath}${project.snapsht}')">
				<span class="project-snapsht-overlay">Show</span>
				<div class="project-snapsht-fg"></div>
			</div>
			<div class="project-info">
				<div class="project-title">[${project.sect}] ${project.title}</div>
				<div class="row-border"></div>
				<div class="project-desc editor-contents">${project.desc}</div>
				<div class="project-menu">
					<div class="btn-slideup btn-project-show">
						<a onclick="projectView(${project.seq})">Show</a>	
					</div>
				</div>
			</div>
			<input type="hidden" id="project-seq" class="project-seq" value="${project.seq}">
		</div>
	</c:forEach>
</div>
