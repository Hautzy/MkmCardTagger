var server_url = "http://localhost:8080/api"

$(document).ready(function() {
    $('#clicker').click(function() {
        $.ajax({
            url: server_url + "/findAll"
        }).then(function(data) {
            console.log(data)
        });
    })
})