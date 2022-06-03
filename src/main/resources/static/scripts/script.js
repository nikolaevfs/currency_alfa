function getGif() {

    let code = document.getElementById("code").value;

    $.ajax({
        url: '/api/currency/gif/' + code,
        method: 'GET',
        complete: function (response, status) {
            let gifPlace = document.getElementById("for-gif");

            if (status === "error"){
                console.log("error");
                gifPlace.innerHTML = response.responseText;
            }else{
                console.log("success");
                let responseText = JSON.parse(response.responseText);
                let gif = document.createElement("img");
                gif.src = responseText.data.images.original.url;
                gifPlace.innerHTML = '';
                gifPlace.insertAdjacentElement("afterbegin", gif);
            }
        }
    })

}