var server_url = "http://localhost:8080"
var activeCards = []
const mkm_token = "mkm_token"
var mkm_token_val = ""

function createCard(c) {
    var elementStr = `<div class="box" id="card-${c.id}">
                    <img src="https://www.cardmarket.com/de/Magic${c.imgUrl}">
        </div>`
    var cardHtml = $(elementStr)
    $('#cardContainer').append(cardHtml)
}

function loadCards() {
    var method = ""
    var name = $('#filterInput').val()
    method = "/card/findByEnglishName?page=0&limit=30&englishName=" + name
    $.ajax({
        url: server_url + method
    }).then(function(data) {
        activeCards = data
        $('#cardContainer').empty()
        for(var i = 0; i < data.length; i++) {
            createCard(data[i])
        }
        console.log(data)
    });
}

$(document).ready(function() {
    if(localStorage.getItem(mkm_token) != null) {
        var mkm_token_val = localStorage.getItem(mkm_token)
        $.ajax({
            headers: { 
                "Authorization" : mkm_token_val
            },
            url: server_url + "/user/checkToken"
        }).then(function(data) {
            $('#logInLink').hide()
        });
    }
    $('#filterInput').on('input', function() {
        loadCards()
    })
    loadCards()
})