$('.answer').click(function() {
	$(this).closest('li').find('span').each(function() {
		$(this).removeClass('red_text green');
	});

	if($(this).hasClass('correct'))
		$(this).parent().addClass('green');
	else
		$(this).parent().addClass('red_text');
		
	var correctans = $('#quiz li span.green').length;
	var wrongans = $('#quiz li span.red_text').length;
	var totque = $('#quiz li').length
	var completed = parseInt(correctans * 100 / totque);
	$('.progress-bar-info').css('width',completed+'%');
	$('.progress-bar-info span').text(completed+'%');

	if(((correctans+wrongans) == totque) && completed < 70) {
		alert('It\'s good idea to re-read the tutorials');
		location.href='css.html';		
	} else if(correctans == totque) {
		alert('Congratulations, you have completed quiz successfully');
		location.href='js.html';
	}
});

$('#example').mouseover(function() {
	$('#example').popover('show');
});