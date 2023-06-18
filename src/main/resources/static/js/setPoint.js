var orderId;
function getOption(select){
    var query = 'http://localhost:8080/orders/26/go/' + select.options[select.selectedIndex].value;
    window.location.href = query;
}