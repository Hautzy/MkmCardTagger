var server_url = "http://localhost:8080"
var activeCards = []
const mkm_token = "mkm_token"
var mkm_token_val = ""
var page = 0
var expansions = []

function createCard(c) {
    var elementStr = `<div class="box" id="card-${c.id}">
                    <img src="https://www.cardmarket.com/de/Magic${c.imgUrl}">
        </div>`
    var cardHtml = $(elementStr)
    $('#cardContainer').append(cardHtml)
}

function loadExpansions() {
    var method = ""
    method = "/expansion/findAllNames"
    $.ajax({
        url: server_url + method
    }).then(function(data) {
        expansions = data
        for (const e in data) {
            console.log(data[e])
            $('#expansions').append($('<option>', {
                value: e,
                text: data[e][0]
            }));
        }
    });
}

function loadCards(page) {
    var method = ""
    var name = $('#filterInput').val()
    var expansion = $('#expansions').val()
    method = "/card/findByEnglishName?page=" + page + "&limit=30&englishName=" + name + "&expansion=" + expansion
    $.ajax({
        url: server_url + method
    }).then(function(data) {
        activeCards = data
        $('#cardContainer').empty()
        for(var i = 0; i < data.length; i++) {
            createCard(data[i])
        }
        console.log(data)
        if(data.length == 0) {
            page--
            loadCards(page)
        }
    });
}

$(document).ready(function() {
    loadExpansions()
    $( "#expansions" ).change(function() {
        loadCards(page)
    });
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
        page = 0
        loadCards(page)
    })
    $("#backPagingBtn").click(function() {
        page--
        if(page < 0)
            page = 0
        loadCards(page)
    })
    $("#nextPagingBtn").click(function() {
        page++
        loadCards(page)
    })
    page = 0
    loadCards(page)
})