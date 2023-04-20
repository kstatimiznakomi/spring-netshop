var stomp = null;

// подключаемся к серверу по окончании загрузки страницы
window.onload = function() {
    connect();
};

function connect() {
    var socket = new SockJS('/socket');
    stomp = Stomp.over(socket);
    stomp.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stomp.subscribe('/topic/products', function (product) {
            renderItem(product);
        });
    });
}

// отправка сообщения на сервер
function sendContent() {
    stomp.send("/app/products", {}, JSON.stringify({
        'name': $("#name").val(),
        'price': $("#price").val()
    }));
}
// хук на интерфейс
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#send" ).click(function() { sendContent(); });
});
// рендер сообщения, полученного от сервера
function renderItem(productJson) {
    var product = JSON.parse(productJson.body);
    $("#products").append(
        "<div class=" + '"' + "row item" + '"' + " id=" + '"' + "products" + '"' + ">" +
        "<div class=" + '"' + "col-4" + '"' + ">" + product.name +"</div>" +
        "<div class=" + '"' + "col-1" + '"' + ">" + product.price +"</div>" +
        "<div><a href='/products/" + product.id +"/bucket'>Добавить в корзину</a></div>" +
        "<div>"
    );
}