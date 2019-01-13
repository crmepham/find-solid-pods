$(function() {
    $('#register-service-container').hide();
    $('#register-service-container').fadeIn(700).animate({
        'bottom': '54%'
    }, {duration: 'slow', queue: false}, function() {
        // Animation complete.
    });
});