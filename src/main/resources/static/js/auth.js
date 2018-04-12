var server_url = "http://localhost:8080"
var signUpActive = false
var logInActive = false
const mkm_token = "mkm_token"
var mkm_token_val = ""

function createLoginPage() {
    $('#logInContainer').show()
    $('#btnLogIn').addClass('toggled')
}

function clearLoginPage() {
    $('#logInContainer').hide()
    $('#btnLogIn').removeClass('toggled')
}

function createSignUpPage() {
    $('#signUpContainer').show()
    $('#btnSignUp').addClass('toggled')
}

function clearSignUpPage() {
    $('#signUpContainer').hide()
    $('#btnSignUp').removeClass('toggled')
}

function toggleAuthButtons(signUpPress, logInPress) {
    $('#alertContainer').hide()

    if (signUpPress) {
        signUpActive = !signUpActive
        logInActive = false
    }
    if (logInPress) {
        logInActive = !logInActive
        signUpActive = false
    }

    clearSignUpPage()
    clearLoginPage()

    if (signUpActive) {
        createSignUpPage()
    }
    if (logInActive) {
        createLoginPage()
    }
}

function signUp() {
    var username = $('#usernameInput').val()
    var email = $('#emailInput').val()
    var password = $('#passwordInput').val()

    var xhr = new XMLHttpRequest();
    var url = server_url + "/sign-up";
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $('#alertText').html("Registered!")
            $('#alertContainer').show()
            clearSignUpPage()
        }
    };
    var data = JSON.stringify({
        username: username,
        email: email,
        password: password
    });
    xhr.send(data);
}

function login() {
    var username = $('#usernameLogInInput').val()
    var password = $('#passwordLogInInput').val()

    var xhr = new XMLHttpRequest();
    var url = server_url + "/login";
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var token = xhr.response.substring(1, xhr.response.length - 1)
            localStorage.setItem(mkm_token, token)
            $('#alertText').html("Logged in!")
            $('#alertContainer').show()
            clearLoginPage()
            $('#btnContainer').hide()
        }
    };
    var data = JSON.stringify({
        username: username,
        password: password
    });
    xhr.send(data);
}

$(document).ready(function () {
    $('#btnSignUpOk').click(function () {
        signUp()
    })
    $('#btnLoginOk').click(function () {
        login()
    })
    $('#btnSignUp').click(function () {
        toggleAuthButtons(true, false)
    })
    $('#btnLogIn').click(function () {
        toggleAuthButtons(false, true)
    })
    $('#logInContainer').hide()
    $('#signUpContainer').hide()
    $('#alertContainer').hide()
    $('#alreadyLoggedIn').hide()

    if(localStorage.getItem(mkm_token) != null) {
        var mkm_token_val = localStorage.getItem(mkm_token)
        $.ajax({
            headers: { 
                "Authorization" : mkm_token_val
            },
            url: server_url + "/user/checkToken"
        }).then(function(data) {
            $('#btnContainer').hide()
            $('#alreadyLoggedIn').show()
        });
    }
})