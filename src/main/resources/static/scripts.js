$(document).ready(function() {
    $("#showSignup").click(function() {
        $("#loginForm").slideUp("slow", function() {
            $("#signupForm").slideDown("slow");
        });
    });

    $("#showLogin").click(function() {
        $("#signupForm").slideUp("slow", function() {
            $("#loginForm").slideDown("slow");
        });
    });
});
