$(function() {
	console.log('jQuery is ready'); 
	$('.hamburger').click(function() {
		$('.menu').toggleClass('open');
  		  console.log('hamburger clicked');
//		$(this).toggleClass('active');
	});
});