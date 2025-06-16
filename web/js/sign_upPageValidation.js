$(document).ready(function () {

    const usernamePattern = /^[A-Za-z0-9_ ]{3,20}$/;
    const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d ]{6,}$/;
    const phonePattern = /^0\d{9}$/;
    const emailPattern = /^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\.[A-Za-z]+$/;


    $("#username").on("blur", function () {
        if (!usernamePattern.test($(this).val())) {
            $(this).addClass("invalid");
            $(this).next(".error").text("Username must be 3-20 letters, numbers or underscores.");
        } else {
            $(this).removeClass("invalid");
            $(this).next(".error").text("");
        }
    });

    $("#password").on("blur", function () {
        if (!passwordPattern.test($(this).val())) {
            $(this).addClass("invalid");
            $(this).next(".error").text("Must be minimum 6 with at least 1 number.");
        } else {
            $(this).removeClass("invalid");
            $(this).next(".error").text("");
        }
    });

    $("#email").on("blur", function () {
        if (!emailPattern.test($(this).val())) {
            $(this).addClass("invalid");
            $(this).next(".error").text("Invalid email.");
        } else {
            $(this).removeClass("invalid");
            $(this).next(".error").text("");
        }
    });

    $("#number").on("blur", function () {
        if (!phonePattern.test($(this).val())) {
            $(this).addClass("invalid");
            $(this).next(".error").text("Must be 10-digits starting with 0.");
        } else {
            $(this).removeClass("invalid");
            $(this).next(".error").text("");
        }
    });

    $("#fullName").on("blur", function () {
        if ($($(this).val()) === '') {
            $(this).addClass("invalid");
            $(this).next(".error").text("Full name is required.");
        } else {
            $(this).removeClass("invalid");
            $(this).next(".error").text("");
        }
    });

    $("form").on("submit", function (e) {
        let invalid = false;

        if (!usernamePattern.test($("#username").val())) invalid = true;
        if (!passwordPattern.test($("#password").val())) invalid = true;
        if ($($("#fullName").val()) === '') invalid = true;
        if (!emailPattern.test($("#email").val())) invalid = true;
        if (!phonePattern.test($("#number").val())) invalid = true;

        if (invalid) {
            e.preventDefault();

            alert("Please correct the highlighted fields.");
        }
    });

});