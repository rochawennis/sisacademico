$(function () {
    var senhaNova = document.getElementById("senha");
    var senhaNova_confirm = document.getElementById("senha_confirm");
 
    function validatePassword() {
        if (senhaNova.value != senhaNova_confirm.value) {
            senhaNova_confirm.setCustomValidity("As senhas não batem!");
        } else {
            senhaNova_confirm.setCustomValidity('');
        }
    }
 
    senhaNova.onchange = validatePassword;
    senhaNova_confirm.onkeyup = validatePassword;
});