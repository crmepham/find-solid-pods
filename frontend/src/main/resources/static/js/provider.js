$(function() {
    $('#provider-list-container').hide();
    $('#provider-list-container').fadeIn(700).animate({
        'bottom': '54%'
    }, {duration: 'slow', queue: false}, function() {
        // Animation complete.
    });
});