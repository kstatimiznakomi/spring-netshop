
let ids =  [];
function change(id){
    let select = document.getElementById(id).value;
    if (ids.find(e => e.id === id)){
        let index = ids.findIndex((obj => obj.id === id));
        ids[index].role = select;
    }
    else {
        ids.push({id: id, role: select});
    }
    console.clear();
    ids.forEach(function (item, i, ids){
        console.log(item)
    });
}

function commit() {
    console.clear();
    ids.forEach(function (item, i, ids) {
        let objStr = JSON.stringify(item);
        let validJson1 = objStr.replaceAll(`[`, ``);
        let validJson2 = validJson1.replaceAll(`]`, ``);
        let parsed = JSON.parse(validJson2);
        console.log(parsed)
        $.ajax({
            type: "GET",
            contentType: false,
            dataType: 'json',
            url: '/users/edit/complete',
            data: parsed,
        });
    });
}