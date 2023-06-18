function setValue() {
    let input = document.getElementById("order-in-details");
    let span = document.getElementById("order-id");
    input.value = span.innerText;
    input.setAttribute("value", span.innerText);
}