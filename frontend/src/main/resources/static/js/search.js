$(function() {

    filter();

    $('#global-search').keyup(function(e) {
        var length = $(this).val().length;
        if (length > 2 || length == 0) {
            filter();
        }
        return false;
    });

    $('.filter-select').change(function(e) {
        filter();
        return false;
    });

});

function filter() {

    var type            = $('#type').val();
    var country         = $('#country').val();
    var term            = $('#global-search').val();
    var searchContainer = $('#provider-list-container');

    var posting = $.post('/search', {type:type, country:country, term:term});

    searchContainer.empty();
    searchContainer.html('Loading...');

    posting.done(function(data) {

        searchContainer.empty();
        searchContainer.html(data);
    });

    return false;
}