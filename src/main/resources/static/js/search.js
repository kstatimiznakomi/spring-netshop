
// отправка сообщения на сервер
function sendContent() {
    $.ajax({
        type: "POST",
        contentType: false,
        dataType: 'json',
        url: '/products/add',
        data:  {
            'name': $("#name").val(),
            'price': $("#price").val(),
            'img': $("#img").val()
        },
    });
}
// хук на интерфейс
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#search-button" ).click(function() { sendContent(); });
});