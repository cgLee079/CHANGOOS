$(document).ready(function() {
	(function(){
		$('.ml6 .letters').each(function(){
		  $(this).html($(this).text().replace(/([^\x00-\x80]|\w)/g, "<span class='letter'>$&</span>"));
		});

		anime.timeline({loop: true})
		  .add({
		    targets: '.ml6 .letter',
		    translateY: ["1.1em", 0],
		    translateZ: 0,
		    duration: 1000,
		    delay: function(el, i) {
		      return 50 * i;
		    }
		  }).add({
		    targets: '.ml6',
		    opacity: 0,
		    duration: 1000,
		    easing: "easeOutExpo",
		    delay: 1000
		  });
	})();
	
	(function() {
		$('.ml9 .letters').each( function() {
			$(this).html( $(this).text().replace( /([^\x00-\x80]|\w)/g, "<span class='letter'>$&</span>"));
		});

		anime.timeline({
			loop : true
		}).add({
			targets : '.ml9 .letter',
			scale : [ 0, 1 ],
			duration : 1500,
			elasticity : 600,
			delay : function(el, i) {
				return 45 * (i + 1)
			}
		}).add({
			targets : '.ml9',
			opacity : 0,
			duration : 1000,
			easing : "easeOutExpo",
			delay : 1000
		});
	})();
	
	
});